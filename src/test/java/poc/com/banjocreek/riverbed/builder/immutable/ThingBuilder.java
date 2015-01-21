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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.banjocreek.riverbed.builder.AbstractImmutableBuilder;

public final class ThingBuilder<R, P> extends
        AbstractImmutableBuilder<StringBuilder, String, R, P> {

    private static final Supplier<StringBuilder> INITIALIZER = StringBuilder::new;

    private static final BiFunction<StringBuilder, String, StringBuilder> MUTATOR = StringBuilder::append;

    public static ThingBuilder<String, String> create() {
        return new ThingBuilder<String, String>(String::valueOf,
                String::valueOf);

    }

    public static <RR, PP> ThingBuilder<RR, PP> create(
            final Function<StringBuilder, RR> builder,
            final Function<StringBuilder, PP> constructor) {

        return new ThingBuilder<>(builder, constructor);

    }

    private ThingBuilder(final Function<StringBuilder, R> builder,
            final Function<StringBuilder, P> constructor) {
        super(INITIALIZER, MUTATOR, constructor, builder);
    }

    private ThingBuilder(final ThingBuilder<R, P> previous, final String delta) {
        super(previous, delta);
    }

    public ThingBuilder<R, P> append(final Object obj) {
        return new ThingBuilder<>(this, String.valueOf(obj));
    }
}
