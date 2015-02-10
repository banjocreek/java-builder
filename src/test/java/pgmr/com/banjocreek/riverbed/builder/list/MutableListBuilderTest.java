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
package pgmr.com.banjocreek.riverbed.builder.list;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.banjocreek.riverbed.builder.list.AbstractMutableListBuilder;

public class MutableListBuilderTest {

    private TestBuilder builder;

    @Before
    public void setup() {
        this.builder = new TestBuilder();
    }

    @Test
    public void testAdd() {

        /*
         * given value and a builder
         */
        // SETUP
        final String v = "Value";

        /*
         * when add is invoked
         */
        this.builder.add(v);

        /*
         * it will produce a list with the added value
         */
        final List<String> actual = this.builder.merge();
        final List<String> expected = Collections.singletonList(v);
        assertEquals(expected, actual);

    }

    @Test
    public void testAddAll() {

        /*
         * given values and a builder with values already
         */
        // SETUP
        this.builder.add("one").add("two");
        final List<String> values = Arrays.asList("v1", "v2");

        /*
         * when add all is invoked
         */
        this.builder.addAll(values);

        /*
         * it will produce a concatenated list
         */
        final List<String> actual = this.builder.merge();
        final List<String> expected = Arrays.asList("one", "two", "v1", "v2");
        assertEquals(expected, actual);

    }

    @Test
    public void testAddMore() {

        /*
         * given value and a builder with values already added
         */
        // SETUP
        this.builder.add("one").add("two");
        final String v = "Value";

        /*
         * when add is invoked
         */
        this.builder.add(v);

        /*
         * it will produce a list with the original and added values in order
         */
        final List<String> actual = this.builder.merge();
        final List<String> expected = Arrays.asList("one", "two", v);
        assertEquals(expected, actual);

    }

    private static final class TestBuilder extends
            AbstractMutableListBuilder<String, List<String>> {

        protected TestBuilder() {
            super(Function.identity());
        }

        TestBuilder add(final String e) {
            doAdd(e);
            return this;
        }

        TestBuilder addAll(final Collection<String> es) {
            doAddAll(es);
            return this;
        }

    }

}
