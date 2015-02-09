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

import com.banjocreek.riverbed.builder.AbstractMutableBuilder;

public abstract class AbstractMutableMapBuilder<K, V, P> extends
        AbstractMutableBuilder<HashMapKernel<K, V>, MapDelta<K, V>, P> {

    protected AbstractMutableMapBuilder(final Function<Map<K, V>, P> constructor) {
        super(HashMapKernel::new, Helper::mutate, Helper
                .adaptConstructor(constructor));
    }

    /**
     * Post a delta consisting of defaults to register.
     *
     * @param key
     *            key of default entry
     *
     * @param value
     *            value of default entry
     *
     *
     */
    protected final void defaults(final K key, final V value) {
        apply(new Defaults<>(key, value));
    }

    /**
     * Post a delta consisting of defaults to register.
     *
     * @param defaults
     *            default entries.
     *
     *
     */
    protected final void defaults(final Map<K, ? extends V> defaults) {

        apply(defaults.isEmpty() ? Nop.instance() : new Defaults<>(defaults));

    }

    /**
     * Post a delta consisting of items to remove.
     *
     * @param toRemove
     *            collection of items to remove.
     *
     *
     */
    protected final void remove(final Collection<K> toRemove) {
        apply(toRemove.isEmpty() ? Nop.instance() : new Remove<>(toRemove));
    }

    /**
     * Post a delta consisting of items to remove.
     *
     * @param toRemove
     *            item to remove
     *
     *
     */
    protected final void remove(final K toRemove) {

        apply(new Remove<>(toRemove));
    }

    /**
     * Post a delta consisting of a single update.
     *
     * @param key
     *            key of entry to update.
     *
     * @param mutate
     *            mutation specification.
     */
    protected final void updates(final K key,
            final Function<? super V, ? extends V> mutate) {
        apply(new Update<>(key, mutate));
    }

    /**
     * Post a delta consisting of updates.
     *
     * @param mutators
     *            mutations to apply
     */
    protected final void updates(
            final Map<K, Function<? super V, ? extends V>> mutators) {
        apply(mutators.isEmpty() ? Nop.instance() : new Update<>(mutators));
    }

    /**
     * Post a delta consisting of map entries.
     *
     * @param key
     *            key of entry
     *
     * @param value
     *            value of entry
     *
     *
     */
    protected final void values(final K key, final V value) {

        apply(new Values<>(key, value));

    }

    /**
     * Post a delta consisting of map entries.
     *
     * @param entries
     *            entries.
     *
     *
     */
    protected final void values(final Map<K, ? extends V> entries) {
        apply(entries.isEmpty() ? Nop.instance() : new Values<>(entries));

    }

}
