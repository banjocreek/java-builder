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
import java.util.Map;
import java.util.function.Function;

import com.banjocreek.riverbed.builder.AbstractImmutableBuilder;

public abstract class AbstractImmutableMapBuilder<K, V, R, P> extends
        AbstractImmutableBuilder<HashMapKernel<K, V>, MapDelta<K, V>, R, P> {

    protected AbstractImmutableMapBuilder(
            final AbstractImmutableMapBuilder<K, V, R, P> previous,
            final MapDelta<K, V> delta) {
        super(previous, delta);
    }

    protected AbstractImmutableMapBuilder(
            final Function<Map<K, V>, R> rootConstructor,
            final Function<Map<K, V>, P> parentConstructor) {
        super(HashMapKernel::new, Helper::mutate, Helper
                .adaptConstructor(rootConstructor), Helper
                .adaptConstructor(parentConstructor));
    }

    /**
     * Create a delta consisting of defaults to register.
     *
     * @param key
     *            key of default entry
     *
     * @param value
     *            value of default entry
     *
     * @return delta
     */
    protected final MapDelta<K, V> defaults(final K key, final V value) {
        return new Defaults<>(key, value);
    }

    /**
     * Create a delta consisting of defaults to register.
     *
     * @param defaults
     *            default entries.
     *
     * @return delta
     */
    protected final MapDelta<K, V> defaults(final Map<K, ? extends V> defaults) {

        return defaults.isEmpty() ? Nop.instance() : new Defaults<>(defaults);

    }

    /**
     * Create a delta consisting of items to remove.
     *
     * @param toRemove
     *            collection of items to remove.
     *
     * @return delta
     */
    protected final MapDelta<K, V> remove(final Collection<K> toRemove) {
        return toRemove.isEmpty() ? Nop.instance() : new Remove<>(toRemove);
    }

    /**
     * Create a delta consisting of items to remove.
     *
     * @param toRemove
     *            item to remove
     *
     * @return delta
     */
    protected final MapDelta<K, V> remove(final K toRemove) {
        return new Remove<>(toRemove);
    }

    /**
     * Create a delta consisting of a single update.
     *
     * @param key
     *            key of entry to update.
     *
     * @param mutate
     *            mutation specification.
     */
    protected final MapDelta<K, V> updates(final K key,
            final Function<? super V, ? extends V> mutate) {
        return new Update<>(key, mutate);
    }

    /**
     * Create a delta consisting of updates.
     *
     * @param mutators
     *            mutations to apply
     */
    protected final MapDelta<K, V> updates(
            final Map<K, Function<? super V, ? extends V>> mutators) {
        return mutators.isEmpty() ? Nop.instance() : new Update<>(mutators);
    }

    /**
     * Create a delta consisting of map entries.
     *
     * @param key
     *            key of entry
     *
     * @param value
     *            value of entry
     *
     * @return delta
     */
    protected final MapDelta<K, V> values(final K key, final V value) {

        return new Values<>(key, value);

    }

    /**
     * Create a delta consisting of map entries.
     *
     * @param entries
     *            entries.
     *
     * @return delta
     */
    protected final MapDelta<K, V> values(final Map<K, ? extends V> entries) {
        return entries.isEmpty() ? Nop.instance() : new Values<>(entries);

    }

}
