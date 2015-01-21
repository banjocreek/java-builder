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
package com.banjocreek.riverbed.builder;

/**
 * An immutable builder captures recoverable build state in every operation.
 * This is meant for cases where a client creates common configuration then
 * generates instances by enhancing the common configuration.
 *
 * @param <R>
 *            the root type of this builder.
 * @param <P>
 *            the parent of this builder.
 */
public interface ImmutableBuilder<R, P> {

    /**
     * Build the root type by working up builder hierarchy. This produces the
     * same result as invoking {@link #done()} on this instance and invoking
     * {@link #done()} on the returning instance and so on until reaching the
     * root.
     *
     * @return
     */
    R build();

    /**
     * Return a parent builder. In an immutable builder, the original parent
     * cannot be returned. Rather, a builder of the same type as the original
     * parent but with its state changed by this builder's operations is
     * returned.
     *
     * @return parent builder enhanced by the operations previously performed on
     *         this builder or, if this is the root builder, the instance built
     *         by this builder.
     */
    P done();

}
