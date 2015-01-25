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

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

final class Helper {

    public static <KK extends Enum<KK>, VV, X> Function<EnumMapKernel<KK, VV>, X> adaptConstructor(
            final Function<Map<KK, VV>, X> constructor) {

        return mk -> constructor.apply(mk.merge());

    }

    public static <KK extends Enum<KK>, VV> Supplier<EnumMapKernel<KK, VV>> initializer(
            final Class<KK> keyType) {
        return () -> new EnumMapKernel<>(keyType);
    }

    public static final <KK extends Enum<KK>, VV> EnumMapKernel<KK, VV> mutate(
            final EnumMapKernel<KK, VV> t, final MapDelta<KK, VV> u) {
        u.applyTo(t);
        return t;
    }

}
