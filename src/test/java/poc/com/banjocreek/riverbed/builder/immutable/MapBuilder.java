/**
 * Copyright (C) Greg Wiley
 *
 * Licensed under the Apache License, Version 2.0 (the "License") under
 * one or more contributor license agreements. See the NOTICE file
 * distributed with this work for information regarding copyright
 * ownership. You may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package poc.com.banjocreek.riverbed.builder.immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.banjocreek.riverbed.builder.AbstractImmutableBuilder;

public final class MapBuilder<R, P>
        extends
        AbstractImmutableBuilder<Map<String, String>, Map<String, String>, R, P> {

    private static final Supplier<Map<String, String>> INITIALIZER = HashMap::new;

    private static final BiFunction<Map<String, String>, Map<String, String>, Map<String, String>> MUTATOR;

    static {
        MUTATOR = (m, e) -> {
            m.putAll(e);
            return m;
        };
    }

    public static MapBuilder<Map<String, String>, Map<String, String>> create() {
        return new MapBuilder<>(Function.identity(), Function.identity());
    }

    public static <RR, PP> MapBuilder<RR, PP> create(
            final Function<Map<String, String>, RR> builder,
            final Function<Map<String, String>, PP> constructor) {
        return new MapBuilder<>(builder, constructor);
    }

    private MapBuilder(final Function<Map<String, String>, R> builder,
            final Function<Map<String, String>, P> constructor) {
        super(INITIALIZER, MUTATOR, constructor, builder);
    }

    private MapBuilder(final MapBuilder<R, P> previous,
            final Map<String, String> delta) {
        super(previous, delta);
    }

    public ThingBuilder<R, MapBuilder<R, P>> withEntry(final String key) {

        return ThingBuilder.create(sb -> {
            return withEntry(key, sb.toString()).build();
        },

        sb -> {
            return withEntry(key, sb.toString());
        });
    }

    public MapBuilder<R, P> withEntry(final String key, final String value) {

        return new MapBuilder<>(this, Collections.singletonMap(key, value));

    }

}
