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
package org.openrewrite.hcl;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.openrewrite.ExecutionContext;
import org.openrewrite.Parser;
import org.openrewrite.hcl.internal.HclParserVisitor;
import org.openrewrite.hcl.internal.grammar.HCLLexer;
import org.openrewrite.hcl.internal.grammar.HCLParser;
import org.openrewrite.hcl.tree.Hcl;
import org.openrewrite.internal.MetricsHelper;
import org.openrewrite.internal.StringUtils;
import org.openrewrite.internal.lang.Nullable;

import java.nio.file.Path;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class HclParser implements Parser<Hcl.ConfigFile> {
    @Override
    public List<Hcl.ConfigFile> parseInputs(Iterable<Input> sourceFiles, @Nullable Path relativeTo, ExecutionContext ctx) {
        return acceptedInputs(sourceFiles).stream()
                .map(sourceFile -> {
                    Timer.Builder timer = Timer.builder("rewrite.parse")
                            .description("The time spent parsing an XML file")
                            .tag("file.type", "XML");
                    Timer.Sample sample = Timer.start();
                    try {
                        HCLLexer lexer = new HCLLexer(CharStreams.fromStream(sourceFile.getSource()));
                        lexer.removeErrorListeners();
                        lexer.addErrorListener(new ForwardingErrorListener(sourceFile.getPath(), ctx));

                        HCLParser parser = new HCLParser(new CommonTokenStream(lexer));
                        parser.removeErrorListeners();
                        parser.addErrorListener(new ForwardingErrorListener(sourceFile.getPath(), ctx));

                        Hcl.ConfigFile configFile = (Hcl.ConfigFile) new HclParserVisitor(
                                sourceFile.getRelativePath(relativeTo),
                                StringUtils.readFully(sourceFile.getSource())
                        ).visitConfigFile(parser.configFile());

                        sample.stop(MetricsHelper.successTags(timer).register(Metrics.globalRegistry));
                        return configFile;
                    } catch (Throwable t) {
                        sample.stop(MetricsHelper.errorTags(timer, t).register(Metrics.globalRegistry));
                        ctx.getOnError().accept(t);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(toList());
    }

    @Override
    public boolean accept(Path path) {
        return path.toString().endsWith(".tf");
    }

    private static class ForwardingErrorListener extends BaseErrorListener {
        private final Path sourcePath;
        private final ExecutionContext ctx;

        private ForwardingErrorListener(Path sourcePath, ExecutionContext ctx) {
            this.sourcePath = sourcePath;
            this.ctx = ctx;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg, RecognitionException e) {
            ctx.getOnError().accept(new HclParsingException(sourcePath,
                    String.format("Syntax error at line %d:%d %s.", line, charPositionInLine, msg), e));
        }
    }
}