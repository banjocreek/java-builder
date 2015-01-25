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

public interface MapKernel<K, V> {

    /**
     * <p>
     * Set or update defaults. Any entries provided here will occur in the
     * resulting map as long as they are not overridden or removed.
     * </p>
     * <p>
     * The order in which this is invoked with respect to
     * {@link #remove(Collection)} and {@link #values(Map)} is unimportant.
     * However the order in which this method is invoked with respect to other
     * invocations of {@link #defaults(Map)} is significant. Later invocations
     * override the defaults set in previous invocations.
     * </p>
     *
     * @param additional
     */
    public void defaults(final Map<K, V> additional);

    public Map<K, V> merge();

    /**
     * <p>
     * Remove entries from the resulting map. Values with the keys presented
     * here will not be in the map unless subsequently set with
     * {@link #values(Map)}. If a removed value is present in an invocation of
     * {@link #defaults(Map)}, the default has no effect and the result will not
     * contain a value for the key.
     * <p>
     * The order in which this is invoked with respect to {@link #values(Map)}
     * and other invocations of {@link #remove(Collection)} determines the the
     * final result with later operations taking precedence. It does not matter
     * the order this method is invoked with respect to {@link #defaults(Map)}.
     * </p>
     *
     * @param toRemove
     *            keys of entries to remove.
     */
    public void remove(final Collection<K> toRemove);

    /**
     * <p>
     * Set or update values. Any entries provide here will occur in the
     * resulting map unless subsequently removed or replaced
     * </p>
     * <p>
     * The order in which this is invoked with respect to
     * {@link #remove(Collection)} and other invocations of {@link #values(Map)}
     * determines the the final result with later operations taking precedence.
     * It does not matter the order this method is invoked with respect to
     * {@link #defaults(Map)}.
     * </p>
     *
     * @param additional
     */
    public void values(final Map<K, V> additional);

}
