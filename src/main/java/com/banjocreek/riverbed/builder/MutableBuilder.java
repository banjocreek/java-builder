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
 * <p>
 * A mutable builder builds an instance by progressively enhancing common state.
 * This is useful for cases where multiple agents contribute to the final built
 * object.
 * </p>
 * <p>
 * The contract for this builder explicitly forbids clearing state after
 * merging. A client can expect to continue using the builder even after it is
 * merged and expect previously applied state changes to be in force.
 * </p>
 *
 *
 * @param <P>
 *            the parent object. This can be a parent builder or the object
 *            being built.
 */
public interface MutableBuilder<P> {

    /**
     * <p>
     * Merge the current state into the parent object. If this method is invoked
     * repeatedly without intervening state changes, it must produce the same
     * result.
     * </p>
     *
     * @return the parent object with the state of this builder merged into it.
     */
    P merge();

}
