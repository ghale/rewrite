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
package org.openrewrite.java.marker;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;
import org.openrewrite.ExecutionContext;
import org.openrewrite.java.internal.ClassgraphTypeMapping;
import org.openrewrite.java.tree.JavaType;
import org.openrewrite.marker.Marker;

import java.nio.file.Path;
import java.util.*;

import static java.util.Collections.emptyMap;
import static org.openrewrite.Tree.randomId;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@With
public class JavaSourceSet implements Marker {
    private static Map<String, JavaType.FullyQualified> JAVA8_CLASSPATH;
    private static Map<String, JavaType.FullyQualified> JAVA11_CLASSPATH;

    @EqualsAndHashCode.Include
    UUID id;

    String name;
    List<JavaType.FullyQualified> classpath;

    public static JavaSourceSet build(String sourceSetName, Iterable<Path> classpath,
                                      Map<String, Object> typeBySignature, ExecutionContext ctx) {

        Map<String, JavaType.FullyQualified> jvmClasses = jvmClasses(typeBySignature, ctx);
        List<JavaType.FullyQualified> fqns = new ArrayList<>(jvmClasses.values());

        ClassgraphTypeMapping typeMapping = new ClassgraphTypeMapping(typeBySignature, jvmClasses);

        if (classpath.iterator().hasNext()) {
            for (ClassInfo classInfo : new ClassGraph()
                    .overrideClasspath(classpath)
                    .enableAnnotationInfo()
                    .enableMemoryMapping()
                    .enableClassInfo()
                    .enableMethodInfo()
                    .enableFieldInfo()
                    .ignoreClassVisibility()
                    .ignoreFieldVisibility()
                    .ignoreMethodVisibility()
                    .scan()
                    .getAllClasses()) {
                try {
                    fqns.add(typeMapping.type(classInfo));
                } catch (Exception e) {
                    ctx.getOnError().accept(e);
                }
            }
        }

        return new JavaSourceSet(randomId(), sourceSetName, fqns);
    }

    private static Map<String, JavaType.FullyQualified> jvmClasses(Map<String, Object> typeBySignature, ExecutionContext ctx) {
        boolean java8 = System.getProperty("java.version").startsWith("1.8");

        if (java8 && JAVA8_CLASSPATH != null) {
            return JAVA8_CLASSPATH;
        } else if (!java8 && JAVA11_CLASSPATH != null) {
            return JAVA11_CLASSPATH;
        }

        ClassInfoList classInfos = new ClassGraph()
                .enableMemoryMapping()
                .enableAnnotationInfo()
                .enableClassInfo()
                .enableMethodInfo()
                .enableFieldInfo()
                .enableSystemJarsAndModules()
                .acceptPackages("java")
//                .rejectPackages("java.awt")
//                .rejectPackages("java.applet")
                .ignoreClassVisibility()
                .ignoreFieldVisibility()
                .ignoreMethodVisibility()
                .scan()
                .getAllClasses();

        ClassgraphTypeMapping builder = new ClassgraphTypeMapping(typeBySignature, emptyMap());
        Map<String, JavaType.FullyQualified> fqns = new HashMap<>(classInfos.size());
        for (ClassInfo classInfo : classInfos) {
            try {
                fqns.put(classInfo.getName(), builder.type(classInfo));
            } catch (Exception e) {
                ctx.getOnError().accept(e);
            }
        }

        if (java8) {
            JAVA8_CLASSPATH = fqns;
        } else {
            JAVA11_CLASSPATH = fqns;
        }

        return fqns;
    }
}
