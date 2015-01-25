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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractMutableBuilder<T, D, P> implements
        MutableBuilder<P> {

    private final Function<T, P> constructor;

    private final BiFunction<T, D, T> mutator;

    private T state = null;

    protected AbstractMutableBuilder(final Supplier<T> initializer,
            final BiFunction<T, D, T> mutator, final Function<T, P> constructor) {
        super();
        this.mutator = mutator;
        this.constructor = constructor;
        this.state = initializer.get();
    }

    @Override
    public final P merge() {
        synchronized (this.mutator) {
            return this.constructor.apply(this.state);
        }
    }

    protected final void apply(final D delta) {
        synchronized (this.mutator) {
            this.state = this.mutator.apply(this.state, delta);
        }
    }

}
