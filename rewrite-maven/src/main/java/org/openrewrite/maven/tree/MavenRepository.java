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
package org.openrewrite.maven.tree;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.openrewrite.internal.lang.Nullable;

import java.net.URI;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@RequiredArgsConstructor
public class MavenRepository {
    @EqualsAndHashCode.Include
    @With
    String id;

    @With
    URI uri;

    @With
    boolean releases;

    @With
    boolean snapshots;

    @NonFinal
    boolean knownToExist = false;

    // Prevent user credentials from being inadvertently serialized
    @With
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Nullable
    String username;

    @With
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Nullable
    String password;

    @JsonIgnore
    public MavenRepository(String id, URI uri, boolean releases, boolean snapshots, boolean knownToExist, @Nullable String username, @Nullable String password) {
        this.id = id;
        this.uri = uri;
        this.releases = releases;
        this.snapshots = snapshots;
        this.knownToExist = knownToExist;
        this.username = username;
        this.password = password;
    }

    public boolean acceptsVersion(String version) {
        if (version.endsWith("-SNAPSHOT")) {
            return snapshots;
        } else if (uri.toString().equalsIgnoreCase("https://repo.spring.io/milestone")) {
            // special case this repository since it will be so commonly used
            return version.matches(".*(M|RC)\\d+$");
        }
        return releases;
    }
}
