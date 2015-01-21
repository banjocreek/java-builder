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

import com.banjocreek.riverbed.builder.AbstractMutableBuilder;

public class MutableBuilderTest {

    private SBuilder<String> builder;

    @Before
    public void setup() {
        this.builder = new SBuilder<>(String::valueOf);
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
        final SBuilder<String> b = this.builder.append("Hi").append(" World");

        /*
         * it produces a corresponding object
         */
        assertEquals("Hi World", b.merge());

    }

    static class SBuilder<P> extends
            AbstractMutableBuilder<StringBuilder, Object, P> {
        public SBuilder(final Function<StringBuilder, P> constructor) {
            super(StringBuilder::new, StringBuilder::append, constructor);
        }

        public SBuilder<P> append(final Object obj) {
            apply(obj);
            return this;
        }

    }

}
