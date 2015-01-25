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

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

final class Helper {

    public static <K, V, X> Function<HashMapKernel<K, V>, X> adaptConstructor(
            final Function<Map<K, V>, X> constructor) {
        return mk -> constructor.apply(mk.merge());
    }

    public static final <K, V> HashMapKernel<K, V> mutate(
            final HashMapKernel<K, V> t, final MapDelta<K, V> u) {
        u.applyTo(t);
        return t;
    }

    public static void requireKeys(final Set<?> s) {
        if (s.contains(null)) {
            throw new NullPointerException("null key is not allowed");
        }
    }

}
