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
package pgmr.com.banjocreek.riverbed.builder.map;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.banjocreek.riverbed.builder.map.AbstractMutableMapBuilder;

public class MutableMapBuilderTest {

    private TestBuilder empty;

    @Before
    public void setup() {
        this.empty = new TestBuilder();
    }

    @Test
    public void testClear() {

        /*
         * Given a builder with defaults and values
         */
        final TestBuilder b = this.empty.defa("DA").defb("DB").b("VB").c("VC");

        /*
         * when the builder is cleared
         */
        b.clear();

        /*
         * it produces an empty map
         */
        assertTrue(b.merge().isEmpty());

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
        final Map<TestKey, String> built = b.merge();

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
        final Map<TestKey, String> actual = b.merge();

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
        final Map<TestKey, String> actual = b.merge();

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
        final HashMap<TestKey, String> defs = new HashMap<>();
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
        final HashMap<TestKey, String> ents = new HashMap<>();
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
        final Map<TestKey, String> actual = b.merge();

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
        final Map<TestKey, String> actual = b.merge();

        /*
         * the remove wins
         */
        final Map<TestKey, String> expected = new HashMap<>();
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
        final Map<TestKey, String> actual = b.merge();

        /*
         * it creates the union
         */
        final Map<TestKey, String> expected = new HashMap<>();
        expected.put(TestKey.A, "A");
        expected.put(TestKey.C, "CC");

        assertEquals(expected, actual);
    }

    @Test
    public void testNullDefaultEmmitted() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when a null value is default
         */
        final TestBuilder b = this.empty.defa(null);

        /*
         * the builder will emit the null value for the key
         */
        assertTrue(b.merge().containsKey(TestKey.A));
        assertEquals(Collections.singletonMap(TestKey.A, null), b.merge());

    }

    @Test
    public void testNullValueEmmitted() {

        /*
         * given a builder
         */
        // SETUP

        /*
         * when a null value is set
         */
        final TestBuilder b = this.empty.a(null);

        /*
         * the builder will emit the null value for the key
         */
        assertTrue(b.merge().containsKey(TestKey.A));
        assertEquals(Collections.singletonMap(TestKey.A, null), b.merge());

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
        final Map<TestKey, String> actual = b.merge();

        /*
         * the remove overrides the default
         */
        final Map<TestKey, String> expected = new HashMap<>();
        expected.put(TestKey.B, "B");

        assertEquals(expected, actual);

    }

    @Test
    public void testReset() {

        /*
         * Given a builder with defaults, values, and removed overlapping
         * defaults.
         */
        final TestBuilder b = this.empty.defa("DA").defb("DB").b("VB").c("VC")
                .noa();

        /*
         * when the builder is reset
         */
        b.reset();

        /*
         * it produces a map with precisely the defaults
         */
        final Object actual = b.merge();
        final Object expected = new TestBuilder().a("DA").b("DB").merge();

        assertEquals(expected, actual);

    }

    @Test
    public void testResetMultipleKeys() {

        /*
         * Given a builder with defaults, values, and removed overlapping
         * defaults.
         */
        final TestBuilder b = this.empty.defa("DA").defb("DB").b("VB").c("VC")
                .noa();

        /*
         * when multiple keys are reset
         */
        b.reset(Arrays.asList(TestKey.A, TestKey.C));

        /*
         * it produces a map with the reset keys set to their defaults but all
         * others left as specified.
         */
        final Object actual = b.merge();
        final Object expected = new TestBuilder().a("DA").b("VB").merge();

        assertEquals(expected, actual);

    }

    @Test
    public void testResetSingleKey() {

        /*
         * Given a builder with defaults, values, and removed overlapping
         * defaults.
         */
        final TestBuilder b = this.empty.defa("DA").defb("DB").b("VB").c("VC")
                .noa().nob();

        /*
         * when a single key is reset
         */
        b.reseta();

        /*
         * it produces a map with the reset key set to its default but all
         * others left as specified.
         */
        final Object actual = b.merge();
        final Object expected = new TestBuilder().a("DA").c("VC").merge();

        assertEquals(expected, actual);

    }

    @Test
    public void testUpdateDefault() {

        /*
         * given a builder with default value
         */
        final String value = "hello";
        final TestBuilder b = this.empty.defa(value);

        /*
         * given an update function
         */
        final Function<String, String> f = s -> s + ", world";

        /*
         * when the value is updated with the function
         */
        b.upda(f);

        /*
         * the builder produces an updated value
         */
        assertEquals(f.apply(value), b.merge().get(TestKey.A));

    }

    @Test
    public void testUpdateDefaultedThenSet() {

        /*
         * given a builder with default and set values
         */
        final String value = "hello";
        final TestBuilder b = this.empty.defa("i am replaced").a(value);

        /*
         * given an update function
         */
        final Function<String, String> f = s -> s + ", world";

        /*
         * when the value is updated with the function
         */
        b.upda(f);

        /*
         * the builder produces an updated value
         */
        assertEquals(f.apply(value), b.merge().get(TestKey.A));

    }

    @Test
    public void testUpdateNonDefault() {

        /*
         * given a builder with set value
         */
        final String value = "hello";
        final TestBuilder b = this.empty.a(value);

        /*
         * given an update function
         */
        final Function<String, String> f = s -> s + ", world";

        /*
         * when the value is updated with the function
         */
        b.upda(f);

        /*
         * the builder produces an updated value
         */
        assertEquals(f.apply(value), b.merge().get(TestKey.A));

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
        final Map<TestKey, String> actual = b.merge();

        /*
         * the entry overrides the default
         */
        final Map<TestKey, String> expected = new HashMap<>();
        expected.put(TestKey.A, "AA");
        expected.put(TestKey.B, "B");

        assertEquals(expected, actual);
    }

    static final class TestBuilder extends
            AbstractMutableMapBuilder<TestKey, String, Map<TestKey, String>> {

        public TestBuilder() {
            super(Function.identity());
        }

        public TestBuilder a(final String v) {
            doValues(TestKey.A, v);
            return this;
        }

        public TestBuilder b(final String v) {
            doValues(TestKey.B, v);
            return this;
        }

        public TestBuilder c(final String v) {
            doValues(TestKey.C, v);
            return this;
        }

        public TestBuilder clear() {
            doClear();
            return this;

        }

        public TestBuilder def(final Map<TestKey, String> vs) {
            doDefaults(vs);
            return this;
        }

        public TestBuilder def(final TestKey k, final String v) {
            doDefaults(k, v);
            return this;
        }

        public TestBuilder defa(final String v) {
            doDefaults(TestKey.A, v);
            return this;
        }

        public TestBuilder defb(final String v) {
            doDefaults(TestKey.B, v);
            return this;
        }

        public TestBuilder defc(final String v) {
            doDefaults(TestKey.C, v);
            return this;
        }

        public TestBuilder no(final Collection<TestKey> ks) {
            doRemove(ks);
            return this;
        }

        public TestBuilder no(final TestKey k) {
            doRemove(k);
            return this;
        }

        public TestBuilder noa() {
            doRemove(TestKey.A);
            return this;
        }

        public TestBuilder nob() {
            doRemove(TestKey.B);
            return this;
        }

        public TestBuilder noc() {
            doRemove(TestKey.C);
            return this;
        }

        public TestBuilder reset() {
            doReset();
            return this;
        }

        public TestBuilder reset(final Collection<TestKey> keys) {
            doReset(keys);
            return this;
        }

        public TestBuilder reseta() {
            doReset(TestKey.A);
            return this;
        }

        public TestBuilder resetb() {
            doReset(TestKey.B);
            return this;
        }

        public TestBuilder resetc() {
            doReset(TestKey.C);
            return this;
        }

        public TestBuilder upd(
                final Map<TestKey, Function<? super String, ? extends String>> ms) {
            doUpdates(ms);
            return this;
        }

        public TestBuilder upd(final TestKey k,
                final Function<? super String, ? extends String> m) {
            doUpdates(k, m);
            return this;
        }

        public TestBuilder upda(
                final Function<? super String, ? extends String> m) {
            doUpdates(TestKey.A, m);
            return this;
        }

        public TestBuilder updb(
                final Function<? super String, ? extends String> m) {
            doUpdates(TestKey.B, m);
            return this;
        }

        public TestBuilder updc(
                final Function<? super String, ? extends String> m) {
            doUpdates(TestKey.C, m);
            return this;
        }

        public TestBuilder val(final Map<TestKey, String> vs) {
            doValues(vs);
            return this;
        }

        public TestBuilder val(final TestKey k, final String v) {
            doValues(k, v);
            return this;
        }

    }

    enum TestKey {
        A, B, C;
    }

}
