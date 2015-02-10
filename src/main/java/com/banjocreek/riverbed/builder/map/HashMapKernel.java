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
package com.banjocreek.riverbed.builder.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

final class HashMapKernel<K, V> implements MapKernel<K, V> {

    /**
     * Defaults accumulator. Default entries are added or updated with the
     * {@link #defaults(Map)} method.
     */
    private final HashMap<K, V> defaults = new HashMap<>();

    /**
     * Entry accumulator. Map entries are removed or added by the
     * {@link #values(Map)} and {@link #remove(Collection)} operations.
     */
    private final HashMap<K, Function<? super V, ? extends V>> entries = new HashMap<>();

    /**
     * All removed keys, i.e. all keys to remove from defaults. This is tracked
     * separately because defaults can be after removals.
     */
    private final HashSet<K> removed = new HashSet<>();

    @Override
    public void clear() {
        this.entries.clear();
        this.removed.clear();
        this.defaults.clear();
    }

    @Override
    public void defaults(final Map<K, V> additional) {

        this.defaults.putAll(additional);

    }

    @Override
    public Map<K, V> merge() {

        /*
         * Start with defaults.
         */
        final HashMap<K, V> rval = new HashMap<>(this.defaults);

        /*
         * get rid of everything for which default has been overridden
         */
        rval.keySet().removeAll(this.removed);

        /*
         * apply accumulated entries.
         */
        this.entries.forEach((k, fv) -> {
            rval.compute(k, (rk, rv) -> fv.apply(rv));
        });

        return rval;
    }

    @Override
    public void remove(final Collection<K> toRemove) {

        this.entries.keySet().removeAll(toRemove);
        this.removed.addAll(toRemove);

    }

    @Override
    public void reset() {
        this.entries.clear();
        this.removed.clear();
    }

    @Override
    public void updates(
            final Map<K, Function<? super V, ? extends V>> additional) {

        additional.forEach(this::addEntry);

    }

    @Override
    public void values(final Map<K, V> additional) {

        additional.forEach((k, v) -> {
            addEntry(k, any -> v);
        });

    }

    private void addEntry(final K k,
            final Function<? super V, ? extends V> mutate) {

        this.entries.merge(k, mutate, Function::andThen);

    }

}
