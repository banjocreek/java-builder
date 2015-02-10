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
import java.util.function.UnaryOperator;

import org.junit.Before;
import org.junit.Test;

import com.banjocreek.riverbed.builder.list.AbstractImmutableListBuilder;

public class ImmutableListBuilderTest {

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
        final TestBuilder b = this.builder.add(v);

        /*
         * it will produce a list with the added value
         */
        final List<String> actual = b.done();
        final List<String> expected = Collections.singletonList(v);
        assertEquals(expected, actual);

    }

    @Test
    public void testAddAll() {

        /*
         * given values and a builder with values already
         */
        // SETUP
        final TestBuilder b = this.builder.add("one").add("two");
        final List<String> values = Arrays.asList("v1", "v2");

        /*
         * when add all is invoked
         */
        final TestBuilder b1 = b.addAll(values);

        /*
         * it will produce a concatenated list
         */
        final List<String> actual = b1.done();
        final List<String> expected = Arrays.asList("one", "two", "v1", "v2");
        assertEquals(expected, actual);

    }

    @Test
    public void testAddMore() {

        /*
         * given value and a builder with values already added
         */
        // SETUP
        final TestBuilder b = this.builder.add("one").add("two");
        final String v = "Value";

        /*
         * when add is invoked
         */
        final TestBuilder b1 = b.add(v);

        /*
         * it will produce a list with the original and added values in order
         */
        final List<String> actual = b1.done();
        final List<String> expected = Arrays.asList("one", "two", v);
        assertEquals(expected, actual);

    }

    @Test
    public void testClear() {

        /*
         * given a builder with values
         */
        final TestBuilder b = this.builder.add("one").add("two");

        /*
         * when clear is invoked
         */
        final TestBuilder b1 = b.clear();

        /*
         * the builder will produce an empty instance
         */
        assertTrue(b1.done().isEmpty());

    }

    private static final class TestBuilder extends
            AbstractImmutableListBuilder<String, List<String>, List<String>> {

        protected TestBuilder() {
            super(Function.identity(), Function.identity());
        }

        private TestBuilder(final TestBuilder b,
                final UnaryOperator<List<String>> delta) {
            super(b, delta);
        }

        public TestBuilder clear() {
            return new TestBuilder(this, genClear());
        }

        TestBuilder add(final String e) {
            return new TestBuilder(this, genAdd(e));
        }

        TestBuilder addAll(final Collection<String> es) {
            return new TestBuilder(this, genAddAll(es));
        }

    }

}
