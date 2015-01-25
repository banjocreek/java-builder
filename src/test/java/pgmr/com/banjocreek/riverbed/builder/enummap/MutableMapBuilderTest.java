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
package pgmr.com.banjocreek.riverbed.builder.enummap;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.banjocreek.riverbed.builder.enummap.AbstractMutableEnumMapBuilder;

public class MutableMapBuilderTest {

    private TestBuilder empty;

    @Before
    public void setup() {
        this.empty = new TestBuilder();
    }

    @Test
    public void testConstructParent() {

        /*
         * given a builder with entries
         */
        final TestBuilder b = this.empty.a("A").b("B");

        /*
         * when build is invoked
         */
        final Map<TestKey, Object> built = b.merge();

        /*
         * it creates a corresponding object
         */
        final HashMap<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.A, "A");
        expected.put(TestKey.B, "B");

        assertEquals(expected, built);

    }

    @Test
    public void testDefaultDoesNotOverrideRemove() {

        /*
         * given a remove followed by default
         */
        final TestBuilder b = this.empty.noa().defa("A").defb("B");

        /*
         * when an object is built
         */
        final Map<TestKey, Object> actual = b.merge();

        /*
         * the default does not override the remove
         */
        final Map<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.B, "B");

        assertEquals(expected, actual);

    }

    @Test
    public void testDefaultDoesNotOverrideValue() {

        /*
         * given value followed by default
         */
        final TestBuilder b = this.empty.a("AA").defa("A").defb("B");

        /*
         * when an object is built
         */
        final Map<TestKey, Object> actual = b.merge();

        /*
         * the default does not override the valuet
         */
        final Map<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.A, "AA");
        expected.put(TestKey.B, "B");

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testDefaultNullKeyFails() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when an attempt to set a default with a null key is made
         */
        this.empty.def(null, "VALUE");

        /*
         * it throws a null pointer exception
         */
        // ANNOTATION

    }

    @Test(expected = NullPointerException.class)
    public void testDefaultsWithNullKeyFails() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when an attempt to set a default with a null key is made
         */
        final HashMap<TestKey, Object> defs = new HashMap<>();
        defs.put(TestKey.A, "A");
        defs.put(null, "B");
        this.empty.def(defs);

        /*
         * it throws a null pointer exception
         */
        // ANNOTATION

    }

    @Test(expected = NullPointerException.class)
    public void testEntriesWithNullKeyFails() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when an attempt to set a value with a null key is made
         */
        final HashMap<TestKey, Object> ents = new HashMap<>();
        ents.put(TestKey.A, "A");
        ents.put(null, "B");
        this.empty.val(ents);

        /*
         * it throws a null pointer exception
         */
        // ANNOTATION

    }

    @Test(expected = NullPointerException.class)
    public void testEntryNullKeyFails() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when an attempt to set a value with a null key is made
         */
        this.empty.val(null, "VALUE");

        /*
         * it throws a null pointer exception
         */
        // ANNOTATION

    }

    @Test
    public void testLastWinsRemoveThenSet() {

        /*
         * given a remove followed by a value
         */
        final TestBuilder b = this.empty.a("AA").nob().b("BB");

        /*
         * when an object is built
         */
        final Map<TestKey, Object> actual = b.merge();

        /*
         * the remove wins
         */
        final Map<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.A, "AA");
        expected.put(TestKey.B, "BB");

        assertEquals(expected, actual);

    }

    @Test
    public void testLastWinsSetThenRemove() {

        /*
         * given a value followed by a remove
         */
        final TestBuilder b = this.empty.a("AA").b("BB").nob();

        /*
         * when an object is built
         */
        final Map<TestKey, Object> actual = b.merge();

        /*
         * the remove wins
         */
        final Map<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.A, "AA");

        assertEquals(expected, actual);

    }

    @Test
    public void testNonOverlappingDefaultsAndValues() {

        /*
         * given distinct default and entry
         */
        final TestBuilder b = this.empty.defa("A").c("CC");

        /*
         * when an object is built
         */
        final Map<TestKey, Object> actual = b.merge();

        /*
         * it creates the union
         */
        final Map<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.A, "A");
        expected.put(TestKey.C, "CC");

        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testRemoveKeysWitnNullKeyFails() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when an attempt to remove a null key is made
         */
        this.empty.no(Arrays.asList(TestKey.A, null, TestKey.B));

        /*
         * it throws a null pointer exception
         */
        // ANNOTATION

    }

    @Test(expected = NullPointerException.class)
    public void testRemoveNullKeyFails() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when an attempt to remove a null key is made
         */
        this.empty.no((TestKey) null);

        /*
         * it throws a null pointer exception
         */
        // ANNOTATION

    }

    @Test
    public void testRemoveOverrideDefault() {

        /*
         * given a default followed by a remove
         */
        final TestBuilder b = this.empty.defa("A").defb("B").noa();

        /*
         * when an object is built
         */
        final Map<TestKey, Object> actual = b.merge();

        /*
         * the remove overrides the default
         */
        final Map<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.B, "B");

        assertEquals(expected, actual);

    }

    @Test
    public void testValueOverrideDefault() {

        /*
         * defaults followed by overlapping entry
         */
        final TestBuilder b = this.empty.defa("A").defb("B").a("AA");

        /*
         * when an object is built
         */
        final Map<TestKey, Object> actual = b.merge();

        /*
         * the entry overrides the default
         */
        final Map<TestKey, Object> expected = new HashMap<>();
        expected.put(TestKey.A, "AA");
        expected.put(TestKey.B, "B");

        assertEquals(expected, actual);
    }

    static final class TestBuilder
            extends
            AbstractMutableEnumMapBuilder<TestKey, Object, Map<TestKey, Object>> {

        public TestBuilder() {
            super(TestKey.class, Function.identity());
        }

        public TestBuilder a(final Object v) {
            entries(TestKey.A, v);
            return this;
        }

        public TestBuilder b(final Object v) {
            entries(TestKey.B, v);
            return this;
        }

        public TestBuilder c(final Object v) {
            entries(TestKey.C, v);
            return this;
        }

        public TestBuilder def(final Map<TestKey, Object> vs) {
            defaults(vs);
            return this;
        }

        public TestBuilder def(final TestKey k, final Object v) {
            defaults(k, v);
            return this;
        }

        public TestBuilder defa(final Object v) {
            defaults(TestKey.A, v);
            return this;
        }

        public TestBuilder defb(final Object v) {
            defaults(TestKey.B, v);
            return this;
        }

        public TestBuilder defc(final Object v) {
            defaults(TestKey.C, v);
            return this;
        }

        public TestBuilder no(final Collection<TestKey> ks) {
            remove(ks);
            return this;
        }

        public TestBuilder no(final TestKey k) {
            remove(k);
            return this;
        }

        public TestBuilder noa() {
            remove(TestKey.A);
            return this;
        }

        public TestBuilder nob() {
            remove(TestKey.B);
            return this;
        }

        public TestBuilder noc() {
            remove(TestKey.C);
            return this;
        }

        public TestBuilder val(final Map<TestKey, Object> vs) {
            entries(vs);
            return this;
        }

        public TestBuilder val(final TestKey k, final Object v) {
            entries(k, v);
            return this;
        }
    }

    enum TestKey {
        A, B, C;
    }

}
