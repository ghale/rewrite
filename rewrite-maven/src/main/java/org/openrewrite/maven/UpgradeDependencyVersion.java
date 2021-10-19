/*
 * Copyright 2020 the original author or authors.
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
package org.openrewrite.maven;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.*;
import org.openrewrite.internal.ListUtils;
import org.openrewrite.internal.StringUtils;
import org.openrewrite.internal.lang.Nullable;
import org.openrewrite.maven.cache.MavenPomCache;
import org.openrewrite.maven.internal.MavenMetadata;
import org.openrewrite.maven.internal.MavenPomDownloader;
import org.openrewrite.maven.tree.DependencyManagementDependency;
import org.openrewrite.maven.tree.Maven;
import org.openrewrite.maven.tree.Pom;
import org.openrewrite.semver.DependencyMatcher;
import org.openrewrite.semver.Semver;
import org.openrewrite.semver.VersionComparator;
import org.openrewrite.xml.AddToTagVisitor;
import org.openrewrite.xml.ChangeTagValueVisitor;
import org.openrewrite.xml.tree.Xml;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;

/**
 * Upgrade the version of a dependency by specifying a group or group and artifact using Node Semver
 * <a href="https://github.com/npm/node-semver#advanced-range-syntax">advanced range selectors</a>, allowing
 * more precise control over version updates to patch or minor releases.
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class UpgradeDependencyVersion extends Recipe {

    @Option(displayName = "Dependency pattern",
            description = "A dependency pattern specifying the groupId and artifactId of the dependency to upgrade, " +
                    "and the version to be upgraded to. The groupId and artifactId may include glob patterns. " +
                    "The version number may be an exact version number, or a node-style semver selector.",
            example = "com.fasterxml.jackson*:jackson-module*:29.X"
    )
    String dependencyPattern;

    @Option(displayName = "Trust parent POM",
            description = "Even if the parent suggests a version that is older than what we are trying to upgrade to, trust it anyway. " +
                    "Useful when you want to wait for the parent to catch up before upgrading. The parent is not trusted by default.",
            example = "false",
            required = false)
    @Nullable
    Boolean trustParent;

    @SuppressWarnings("ConstantConditions")
    @Override
    public Validated validate() {
        Validated validated = super.validate();
        if(dependencyPattern != null) {
            validated = validated.and(DependencyMatcher.build(dependencyPattern));
        }
        return validated;
    }

    @Override
    public String getDisplayName() {
        return "Upgrade Maven dependency version";
    }

    @Override
    public String getDescription() {
        return "Upgrade the version of a dependency by specifying a group or group and artifact using Node Semver " +
                "advanced range selectors, allowing more precise control over version updates to patch or minor releases.";
    }

    @Override
    protected MavenVisitor getVisitor() {
        return new UpgradeDependencyVersionVisitor();
    }

    private class UpgradeDependencyVersionVisitor extends MavenVisitor {
        @Nullable
        private Collection<String> availableVersions;

        private final DependencyMatcher dependencyMatcher;

        public UpgradeDependencyVersionVisitor() {
            //noinspection ConstantConditions
            dependencyMatcher = DependencyMatcher.build(dependencyPattern).getValue();
        }

        @Override
        public Maven visitMaven(Maven maven, ExecutionContext ctx) {
            return maven
                    .withMavenModel(maven.getMavenModel().withPom(maybeChangeDependencyVersion(maven.getModel(), ctx)))
                    .withModules(ListUtils.map(maven.getModules(), module -> maybeChangeDependencyVersion(module, ctx)));
        }

        private Pom maybeChangeDependencyVersion(Pom model, ExecutionContext ctx) {
            return model
                    .withDependencies(ListUtils.map(model.getDependencies(), dependency -> {
                        if (dependencyMatcher.matches(dependency.getGroupId(), dependency.getArtifactId())) {
                            if (model.getParent() != null) {
                                String managedVersion = model.getParent().getManagedVersion(dependency.getGroupId(), dependency.getArtifactId());
                                if (managedVersion != null) {
                                    return dependency;
                                }
                            }
                            return findNewerDependencyVersion(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), ctx)
                                    .map(newer -> {
                                        ChangeDependencyVersionVisitor changeDependencyVersion = new ChangeDependencyVersionVisitor(newer, dependency.getGroupId(), dependency.getArtifactId());
                                        doAfterVisit(changeDependencyVersion);
                                        return dependency.withVersion(newer);
                                    })
                                    .orElse(dependency);
                        }
                        return dependency;
                    }))
                    .withDependencyManagement(model.getDependencyManagement().withDependencies(ListUtils.map(model.getDependencyManagement().getDependencies(), dependency -> {
                        if (dependencyMatcher.matches(dependency.getGroupId(), dependency.getArtifactId())) {
                            return findNewerDependencyVersion(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion(), ctx)
                                    .map(newer -> {
                                        ChangeDependencyVersionVisitor changeDependencyVersion = new ChangeDependencyVersionVisitor(newer, dependency.getGroupId(), dependency.getArtifactId());
                                        doAfterVisit(changeDependencyVersion);
                                        return (DependencyManagementDependency) dependency.withVersion(newer);
                                    })
                                    .orElse(dependency);
                        }
                        return dependency;
                    })));
        }

        private Optional<String> findNewerDependencyVersion(String groupId, String artifactId, String currentVersion,
                                                            ExecutionContext ctx) {
            if (availableVersions == null) {
                MavenMetadata mavenMetadata = new MavenPomDownloader(MavenPomCache.NOOP,
                        emptyMap(), ctx).downloadMetadata(groupId, artifactId, getCursor().firstEnclosingOrThrow(Maven.class).getModel().getEffectiveRepositories());
                availableVersions = mavenMetadata.getVersioning().getVersions().stream()
                        .filter(v -> dependencyMatcher.isValidVersion(currentVersion, v))
                        .collect(Collectors.toList());
            }
            return dependencyMatcher.upgrade(currentVersion, availableVersions);
        }
    }

    private static class ChangeDependencyVersionVisitor extends MavenVisitor {
        private final String newVersion;
        private final String groupId;
        private final String artifactId;

        private ChangeDependencyVersionVisitor(String newVersion, String groupId, String artifactId) {
            this.newVersion = newVersion;
            this.groupId = groupId;
            this.artifactId = artifactId;
        }

        @Override
        public Xml visitTag(Xml.Tag tag, ExecutionContext ctx) {
            if (isDependencyTag(groupId, artifactId) || isManagedDependencyTag(groupId, artifactId)) {
                Optional<Xml.Tag> versionTag = tag.getChild("version");
                if (versionTag.isPresent()) {
                    String version = versionTag.get().getValue().orElse(null);
                    if (version != null) {
                        if (version.trim().startsWith("${") && !newVersion.equals(model.getValue(version.trim()))) {
                            doAfterVisit(new ChangePropertyValue(version, newVersion));
                        } else if (!newVersion.equals(version)) {
                            doAfterVisit(new ChangeTagValueVisitor<>(versionTag.get(), newVersion));
                        }
                    }
                }
                // In this case a transitive dependency has been removed and the dependency now requires a version
                else if (!isManagedDependencyTag(groupId, artifactId)) {
                    Xml.Tag newVersionTag = Xml.Tag.build("<version>" + newVersion + "</version>");
                    doAfterVisit(new AddToTagVisitor<>(getCursor().getValue(), newVersionTag));
                }
            } else if (!modules.isEmpty() && isPropertyTag()) {
                String propertyKeyRef = "${" + tag.getName() + "}";

                OUTER:
                for (Pom module : modules) {
                    for (Pom.Dependency dependency : module.getDependencies()) {
                        if (artifactId.equals(dependency.getArtifactId()) && propertyKeyRef.equals(dependency.getRequestedVersion())) {
                            doAfterVisit(new ChangeTagValueVisitor<>(tag, newVersion));
                            doAfterVisit(new RemoveRedundantDependencyVersions());
                            break OUTER;
                        }
                    }

                    for (DependencyManagementDependency dependency : module.getDependencyManagement().getDependencies()) {
                        if (artifactId.equals(dependency.getArtifactId()) && propertyKeyRef.equals(dependency.getRequestedVersion())) {
                            doAfterVisit(new ChangeTagValueVisitor<>(tag, newVersion));
                            doAfterVisit(new RemoveRedundantDependencyVersions());
                            break OUTER;
                        }
                    }

                }
            }

            return super.visitTag(tag, ctx);
        }
    }
}
