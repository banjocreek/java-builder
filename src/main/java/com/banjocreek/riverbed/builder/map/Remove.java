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
import java.util.EnumSet;
import java.util.Set;

final class Remove<K extends Enum<K>, V> implements MapDelta<K, V> {

    private final Set<K> keys;

    public Remove(final Collection<K> keys) {
        this.keys = EnumSet.copyOf(keys);
    }

    public Remove(final K key) {
        this.keys = EnumSet.of(key);
    }

    @Override
    public void applyTo(final MapKernel<K, V> kernel) {
        kernel.remove(this.keys);
    }

}
