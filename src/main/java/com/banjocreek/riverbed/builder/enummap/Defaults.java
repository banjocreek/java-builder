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

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

final class Defaults<K extends Enum<K>, V> implements MapDelta<K, V> {

    private final EnumMap<K, V> entries;

    public Defaults(final K k, final V v) {
        this.entries = new EnumMap<>(Collections.singletonMap(k, v));
    }

    public Defaults(final Map<K, ? extends V> entries) {
        this.entries = new EnumMap<>(entries);
    }

    @Override
    public void applyTo(final EnumMapKernel<K, V> kernel) {

        kernel.defaults(this.entries);

    }

}
