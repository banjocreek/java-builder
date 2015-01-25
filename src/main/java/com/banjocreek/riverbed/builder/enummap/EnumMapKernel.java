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
package com.banjocreek.riverbed.builder.enummap;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import com.banjocreek.riverbed.builder.map.MapKernel;

final class EnumMapKernel<K extends Enum<K>, V> implements MapKernel<K, V> {

    /**
     * Defaults accumulator. Default entries are added or updated with the
     * {@link #defaults(Map)} method.
     */
    private final EnumMap<K, V> defaults;

    /**
     * Entry accumulator. Map entries are removed or added by the
     * {@link #values(Map)} and {@link #remove(Collection)} operations.
     */
    private final EnumMap<K, V> entries;

    /**
     * All seen keys, i.e. all keys for which default has been overridden. A
     * seen key comes from a {@link #remove(Collection)} or {@link #values(Map)}
     * invocation.
     */
    private final EnumSet<K> seen;

    public EnumMapKernel(final Class<K> keyType) {
        this.defaults = new EnumMap<>(keyType);
        this.entries = new EnumMap<>(keyType);
        this.seen = EnumSet.noneOf(keyType);
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
        final EnumMap<K, V> rval = new EnumMap<>(this.defaults);

        /*
         * get rid of everything for which default has been overridden
         */
        rval.keySet().removeAll(this.seen);

        /*
         * apply accumulated entries.
         */
        rval.putAll(this.entries);

        return rval;
    }

    @Override
    public void remove(final Collection<K> toRemove) {

        this.entries.keySet().removeAll(toRemove);
        this.seen.addAll(toRemove);

    }

    @Override
    public void values(final Map<K, V> additional) {

        this.entries.putAll(additional);
        this.seen.addAll(additional.keySet());

    }

}
