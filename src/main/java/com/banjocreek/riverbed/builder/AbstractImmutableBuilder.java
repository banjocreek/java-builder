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
package com.banjocreek.riverbed.builder;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractImmutableBuilder<T, D, R, P> implements
        ImmutableBuilder<R, P> {

    private final Optional<Link> head;

    private final Function<Optional<Link>, T> merge;

    private final Function<T, P> parentConstructor;

    private final Function<T, R> rootConstructor;

    protected AbstractImmutableBuilder(
            final AbstractImmutableBuilder<T, D, R, P> previous, final D delta) {
        this.merge = previous.merge;
        this.parentConstructor = previous.parentConstructor;
        this.rootConstructor = previous.rootConstructor;
        this.head = Optional.of(new Link(delta, previous.head));

    }

    protected AbstractImmutableBuilder(final Supplier<T> initializer,
            final BiFunction<T, D, T> mutator,
            final Function<T, P> parentConstructor,
            final Function<T, R> rootConstructor) {

        this.merge = ol -> {
            return fold(initializer.get(), ol, mutator);
        };
        this.parentConstructor = parentConstructor;
        this.rootConstructor = rootConstructor;
        this.head = Optional.empty();

    }

    @Override
    public final R build() {
        return this.rootConstructor.apply(this.merge.apply(this.head));
    }

    @Override
    public final P done() {
        return this.parentConstructor.apply(this.merge.apply(this.head));
    }

    private T fold(final T initial, final Optional<Link> mlink,
            final BiFunction<T, D, T> mutator) {

        return mlink.map(l -> {
            final T result = fold(initial, l.previous, mutator);
            return mutator.apply(result, l.value);
        }).orElse(initial);

    }

    private class Link {
        final Optional<Link> previous;
        final D value;

        Link(final D value, final Optional<Link> prev) {
            this.value = value;
            this.previous = prev;
        }

    }

}
