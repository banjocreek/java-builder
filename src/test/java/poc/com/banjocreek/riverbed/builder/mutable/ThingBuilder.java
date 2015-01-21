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

import java.util.function.BiFunction;
import java.util.function.Function;

import com.banjocreek.riverbed.builder.AbstractMutableBuilder;

public final class ThingBuilder<P> extends
        AbstractMutableBuilder<StringBuilder, String, P> {

    private static final BiFunction<StringBuilder, String, StringBuilder> MUTATOR = StringBuilder::append;

    public static ThingBuilder<String> create() {
        return new ThingBuilder<>(String::valueOf);
    }

    public static <PP> ThingBuilder<PP> create(
            final Function<StringBuilder, PP> constructor) {
        return new ThingBuilder<>(constructor);
    }

    private ThingBuilder(final Function<StringBuilder, P> constructor) {
        super(StringBuilder::new, MUTATOR, constructor);
    }

    public ThingBuilder<P> append(final String v) {
        apply(v);
        return this;
    }
}
