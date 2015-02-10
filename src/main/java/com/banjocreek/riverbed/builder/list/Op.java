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
package com.banjocreek.riverbed.builder.list;

import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

interface Op {

    static <E> UnaryOperator<List<E>> add(final E elem) {
        return l -> {
            l.add(elem);
            return l;
        };
    }

    static <E> UnaryOperator<List<E>> addAll(final Collection<? extends E> elems) {
        return l -> {
            l.addAll(elems);
            return l;
        };
    }

    static <E> UnaryOperator<List<E>> clear() {
        return l -> {
            l.clear();
            return l;
        };
    }

}
