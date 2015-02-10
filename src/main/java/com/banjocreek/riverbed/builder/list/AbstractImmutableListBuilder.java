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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.banjocreek.riverbed.builder.AbstractImmutableBuilder;

public class AbstractImmutableListBuilder<E, R, P> extends
        AbstractImmutableBuilder<List<E>, UnaryOperator<List<E>>, R, P> {

    protected AbstractImmutableListBuilder(
            final AbstractImmutableBuilder<List<E>, UnaryOperator<List<E>>, R, P> previous,
            final UnaryOperator<List<E>> delta) {
        super(previous, delta);
    }

    protected AbstractImmutableListBuilder(
            final Function<List<E>, R> rootConstructor,
            final Function<List<E>, P> parentConstructor) {
        super(ArrayList::new, (l, op) -> op.apply(l), rootConstructor,
                parentConstructor);
    }

    protected final UnaryOperator<List<E>> genAdd(final E elem) {
        return Op.add(elem);
    }

    protected final UnaryOperator<List<E>> genAddAll(
            final Collection<? extends E> elems) {
        return Op.addAll(elems);
    }
}
