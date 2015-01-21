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
package pgmr.com.banjocreek.riverbed.builder;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.banjocreek.riverbed.builder.AbstractImmutableBuilder;

public class ImmutableBuilderTest {

    Function<StringBuilder, String> constructor;

    Function<StringBuilder, String> rootConstructor;
    private SBuilder<String, String> builder;

    @Before
    public void setup() {

        this.constructor = String::valueOf;
        this.rootConstructor = sb -> "Built: " + sb;

        this.builder = new SBuilder<>(this.rootConstructor, this.constructor);
    }

    @Test
    public void testConstructor() {

        /*
         * given a mutated builder with a known constructor
         */
        // setup
        final SBuilder<String, String> b = this.builder.append("Value");

        /*
         * when done is invoked
         */
        final String constructed = b.done();

        /*
         * it produces a value through the constructor
         */
        assertEquals(this.constructor.apply(new StringBuilder("Value")),
                constructed);

    }

    @Test
    public void testImmutable() {

        /*
         * given a mutated builder and the value it constructs
         */
        final SBuilder<String, String> b = this.builder.append("value");
        final String initial = b.build();

        /*
         * when the mutated builder is itself mutated
         */
        b.append("another").append("value").append("here");

        /*
         * it does not affect the original builder
         */
        assertEquals(initial, b.build());

    }

    @Test
    public void testMutate() {

        /*
         * given a builder
         */
        // setup

        /*
         * when it is mutated
         */
        final SBuilder<String, String> b = this.builder.append("Hi").append(
                " World");

        /*
         * it produces a corresponding object
         */
        assertEquals("Hi World", b.done());

    }

    @Test
    public void testRootConstructor() {

        /*
         * given a mutated builder with a known root constructor
         */
        // setup
        final SBuilder<String, String> b = this.builder.append("Value");

        /*
         * when build is invoked
         */
        final String constructed = b.build();

        /*
         * it produces a value through the root constructor
         */
        assertEquals(this.rootConstructor.apply(new StringBuilder("Value")),
                constructed);

    }

    static class SBuilder<R, P> extends
            AbstractImmutableBuilder<StringBuilder, Object, R, P> {
        public SBuilder(final Function<StringBuilder, R> builder,
                final Function<StringBuilder, P> constructor) {
            super(StringBuilder::new, StringBuilder::append, constructor,
                    builder);
        }

        private SBuilder(final SBuilder<R, P> prev, final Object delta) {
            super(prev, delta);
        }

        public SBuilder<R, P> append(final Object obj) {
            return new SBuilder<>(this, obj);
        }

    }

}
