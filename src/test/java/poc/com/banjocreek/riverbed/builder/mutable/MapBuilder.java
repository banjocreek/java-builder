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
package poc.com.banjocreek.riverbed.builder.mutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import com.banjocreek.riverbed.builder.AbstractMutableBuilder;

public final class MapBuilder<P> extends
        AbstractMutableBuilder<Map<String, String>, Map<String, String>, P> {

    private static final BiFunction<Map<String, String>, Map<String, String>, Map<String, String>> MUTATOR = (
            m, e) -> {
        m.putAll(e);
        return m;
    };

    public static MapBuilder<Map<String, String>> create() {

        final Function<Map<String, String>, Map<String, String>> copy = m -> new HashMap<>(
                m);
        return new MapBuilder<>(copy);
    }

    public static <PP> MapBuilder<PP> create(
            final Function<Map<String, String>, PP> constructor) {
        return new MapBuilder<>(constructor);
    }

    private MapBuilder(final Function<Map<String, String>, P> constructor) {

        super(HashMap::new, MUTATOR, constructor);

    }

    public ThingBuilder<MapBuilder<P>> withEntry(final String key) {

        final Function<StringBuilder, MapBuilder<P>> constructor = sb -> {
            withEntry(key, String.valueOf(sb));
            return MapBuilder.this;
        };

        return ThingBuilder.create(constructor);

    }

    public MapBuilder<P> withEntry(final String key, final String value) {

        apply(Collections.singletonMap(key, value));

        return this;

    }

}
