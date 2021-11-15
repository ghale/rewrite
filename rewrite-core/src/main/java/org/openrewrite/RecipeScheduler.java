/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.openrewrite.internal.ListUtils;
import org.openrewrite.internal.MetricsHelper;
import org.openrewrite.internal.lang.Nullable;
import org.openrewrite.marker.Generated;
import org.openrewrite.marker.Markers;
import org.openrewrite.marker.RecipesThatMadeChanges;
import org.openrewrite.scheduling.WatchableExecutionContext;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.*;
import static org.openrewrite.Recipe.PANIC;
import static org.openrewrite.Tree.randomId;

public interface RecipeScheduler {
    default <T> List<T> mapAsync(List<T> input, UnaryOperator<T> mapFn) {
        @SuppressWarnings("unchecked") CompletableFuture<T>[] futures =
                new CompletableFuture[input.size()];

        int i = 0;
        for (T before : input) {
            Callable<T> updateTreeFn = () -> mapFn.apply(before);
            futures[i++] = schedule(updateTreeFn);
        }

        CompletableFuture.allOf(futures).join();
        return ListUtils.map(input, (j, in) -> futures[j].join());
    }

    default List<Result> scheduleRun(Recipe recipe,
                                     List<? extends SourceFile> before,
                                     ExecutionContext ctx,
                                     int maxCycles,
                                     int minCycles) {
        DistributionSummary.builder("rewrite.recipe.run")
                .tag("recipe", recipe.getDisplayName())
                .description("The distribution of recipe runs and the size of source file batches given to them to process.")
                .baseUnit("source files")
                .register(Metrics.globalRegistry)
                .record(before.size());

        Map<UUID, Stack<Recipe>> recipeThatDeletedSourceFile = new HashMap<>();
        List<? extends SourceFile> acc = before;
        List<? extends SourceFile> after = acc;

        WatchableExecutionContext ctxWithWatch = new WatchableExecutionContext(ctx);
        for (int i = 0; i < maxCycles; i++) {
            Stack<Recipe> recipeStack = new Stack<>();
            recipeStack.push(recipe);

            after = scheduleVisit(recipeStack, acc, ctxWithWatch, recipeThatDeletedSourceFile);
            if (i + 1 >= minCycles && ((after == acc && !ctxWithWatch.hasNewMessages()) || !recipe.causesAnotherCycle())) {
                break;
            }
            acc = after;
            ctxWithWatch.resetHasNewMessages();
        }

        if (after == before) {
            return emptyList();
        }

        Map<UUID, SourceFile> sourceFileIdentities = before.stream()
                .collect(toMap(SourceFile::getId, Function.identity()));

        List<Result> results = new ArrayList<>();

        // added or changed files
        for (SourceFile s : after) {
            SourceFile original = sourceFileIdentities.get(s.getId());
            if (original != s) {
                if (original == null) {
                    results.add(new Result(null, s, singleton(recipeThatDeletedSourceFile.get(s.getId()))));
                } else {
                    if (original.getMarkers().findFirst(Generated.class).isPresent()) {
                        continue;
                    }

                    boolean isChanged = !original.getSourcePath().equals(s.getSourcePath());
                    if (!isChanged) {
                        TreeVisitor<Tree, PrintOutputCapture<ExecutionContext>> markerIdPrinter = new TreeVisitor<Tree, PrintOutputCapture<ExecutionContext>>() {
                            @Override
                            public Tree visit(@Nullable Tree tree, PrintOutputCapture<ExecutionContext> p) {
                                if (tree instanceof Markers) {
                                    String markerIds = ((Markers) tree).entries().stream()
                                            .filter(marker -> !(marker instanceof RecipesThatMadeChanges))
                                            .map(marker -> String.valueOf(marker.hashCode()))
                                            .collect(joining(","));
                                    if (!markerIds.isEmpty()) {
                                        p.out
                                                .append("markers[")
                                                .append(markerIds)
                                                .append("]->");
                                    }
                                }
                                return super.visit(tree, p);
                            }
                        };

                        PrintOutputCapture<ExecutionContext> originalOutput = new PrintOutputCapture<>(ctx);
                        PrintOutputCapture<ExecutionContext> sOutput = new PrintOutputCapture<>(ctx);
                        markerIdPrinter.visit(original, originalOutput);
                        markerIdPrinter.visit(s, sOutput);
                        isChanged = !originalOutput.toString().equals(sOutput.toString());
                    }

                    if (isChanged) {
                        results.add(new Result(original, s, s.getMarkers()
                                .findFirst(RecipesThatMadeChanges.class)
                                .orElseThrow(() -> new IllegalStateException("SourceFile changed but no recipe reported making a change?"))
                                .getRecipes()));
                    }
                }
            }
        }

        Set<UUID> afterIds = after.stream()
                .map(SourceFile::getId)
                .collect(toSet());

        // removed files
        for (SourceFile s : before) {
            if (!afterIds.contains(s.getId()) && !s.getMarkers().findFirst(Generated.class).isPresent()) {
                results.add(new Result(s, null, singleton(recipeThatDeletedSourceFile.get(s.getId()))));
            }
        }

        return results;
    }

    default <S extends SourceFile> List<S> scheduleVisit(Stack<Recipe> recipeStack,
                                                         List<S> before,
                                                         ExecutionContext ctx,
                                                         Map<UUID, Stack<Recipe>> recipeThatDeletedSourceFile) {
        long startTime = System.nanoTime();
        AtomicBoolean thrownErrorOnTimeout = new AtomicBoolean(false);
        Recipe recipe = recipeStack.peek();
        if (recipe.getApplicableTest() != null) {
            boolean applicable = false;
            for (S s : before) {
                if (recipe.getApplicableTest().visit(s, ctx) != s) {
                    applicable = true;
                    break;
                }
            }

            if (!applicable) {
                return before;
            }
        }

        List<S> after = !recipe.validate(ctx).isValid() ?
                before :
                mapAsync(before, s -> {
                    Timer.Builder timer = Timer.builder("rewrite.recipe.visit").tag("recipe", recipe.getDisplayName());
                    Timer.Sample sample = Timer.start();

                    if (recipe.getSingleSourceApplicableTest() != null) {
                        if (recipe.getSingleSourceApplicableTest().visit(s, ctx) == s) {
                            sample.stop(MetricsHelper.successTags(timer, s, "skipped").register(Metrics.globalRegistry));
                            return s;
                        }
                    }

                    Duration duration = Duration.ofNanos(System.nanoTime() - startTime);
                    if (duration.compareTo(ctx.getRunTimeout(before.size())) > 0) {
                        if (thrownErrorOnTimeout.compareAndSet(false, true)) {
                            RecipeTimeoutException t = new RecipeTimeoutException(recipe);
                            ctx.getOnError().accept(t);
                            ctx.getOnTimeout().accept(t, ctx);
                        }
                        sample.stop(MetricsHelper.successTags(timer, s, "timeout").register(Metrics.globalRegistry));
                        return s;
                    }

                    if (ctx.getMessage(PANIC) != null) {
                        return s;
                    }

                    try {
                        @SuppressWarnings("unchecked") S afterFile = (S) recipe.getVisitor().visit(s, ctx);
                        if (afterFile != null && afterFile != s) {
                            List<Stack<Recipe>> recipeStackList = new ArrayList<>(1);
                            recipeStackList.add(recipeStack);
                            afterFile = afterFile.withMarkers(afterFile.getMarkers().computeByType(
                                    new RecipesThatMadeChanges(randomId(), recipeStackList),
                                    (r1, r2) -> {
                                        r1.getRecipes().addAll(r2.getRecipes());
                                        return r1;
                                    }));
                            sample.stop(MetricsHelper.successTags(timer, s, "changed").register(Metrics.globalRegistry));
                        } else if (afterFile == null) {
                            recipeThatDeletedSourceFile.put(s.getId(), recipeStack);
                            sample.stop(MetricsHelper.successTags(timer, s, "deleted").register(Metrics.globalRegistry));
                        } else {
                            sample.stop(MetricsHelper.successTags(timer, s, "unchanged").register(Metrics.globalRegistry));
                        }
                        return afterFile;
                    } catch (Throwable t) {
                        sample.stop(MetricsHelper.errorTags(timer, s, t).register(Metrics.globalRegistry));
                        ctx.getOnError().accept(t);
                        return s;
                    }
                });

        // The type of the list is widened at this point, since a source file type may be generated that isn't
        // of a type that is in the original set of source files (e.g. only XML files are given, and the
        // recipe generates Java code).

        //noinspection unchecked
        List<SourceFile> afterWidened = recipe.visit((List<SourceFile>) after, ctx);

        if (afterWidened != after) {
            Map<UUID, SourceFile> originalMap = new HashMap<>(after.size());
            for (SourceFile file : after) {
                originalMap.put(file.getId(), file);
            }
            afterWidened = ListUtils.map(afterWidened, s -> {
                SourceFile original = originalMap.get(s.getId());
                if (original == null) {
                    // a new source file generated
                    recipeThatDeletedSourceFile.put(s.getId(), recipeStack);
                } else if (s != original) {
                    List<Stack<Recipe>> recipeStackList = new ArrayList<>(1);
                    recipeStackList.add(recipeStack);
                    return s.withMarkers(s.getMarkers().computeByType(
                            new RecipesThatMadeChanges(randomId(), recipeStackList),
                            (r1, r2) -> {
                                r1.getRecipes().addAll(r2.getRecipes());
                                return r1;
                            }));
                }
                return s;
            });

            for (SourceFile maybeDeleted : after) {
                if (!afterWidened.contains(maybeDeleted)) {
                    // a source file deleted
                    recipeThatDeletedSourceFile.put(maybeDeleted.getId(), recipeStack);
                }
            }
        }

        List<Recipe> recipeList = recipe.getRecipeList();
        for (Recipe r : Collections.unmodifiableList(recipeList)) {
            if (ctx.getMessage(PANIC) != null) {
                //noinspection unchecked
                return (List<S>) afterWidened;
            }

            Stack<Recipe> nextStack = new Stack<>();
            nextStack.addAll(recipeStack);
            nextStack.push(r);

            afterWidened = scheduleVisit(nextStack, afterWidened, ctx, recipeThatDeletedSourceFile);
        }

        //noinspection unchecked
        return (List<S>) afterWidened;
    }

    <T> CompletableFuture<T> schedule(Callable<T> fn);
}
