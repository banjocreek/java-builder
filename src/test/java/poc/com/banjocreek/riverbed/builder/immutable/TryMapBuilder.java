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
package poc.com.banjocreek.riverbed.builder.immutable;

import java.util.Map;

public class TryMapBuilder {

    public static void main(final String[] args) {

        final MapBuilder<Map<String, String>, Map<String, String>> b0 = MapBuilder
                .create();
        final MapBuilder<Map<String, String>, Map<String, String>> b1 = b0
                .withEntry("Key", "Value");
        final MapBuilder<Map<String, String>, Map<String, String>> b2 = b1
                .withEntry("Key2", "ValueTwo");

        System.out.println(b0.build());
        System.out.println(b1.build());
        System.out.println(b2.build());
        System.out.println(b1.build());
        System.out.println(b0.build());

        System.out.println(b0.done());
        System.out.println(b1.done());
        System.out.println(b2.done());
        System.out.println(b1.done());
        System.out.println(b0.done());

        final ThingBuilder<Map<String, String>, MapBuilder<Map<String, String>, Map<String, String>>> b4 = b2
                .withEntry("extra").append("Hi");
        final ThingBuilder<Map<String, String>, MapBuilder<Map<String, String>, Map<String, String>>> b5 = b4
                .append(" World");

        System.out.println(b4.build());
        System.out.println(b5.build());
        System.out.println(b4.build());

        System.out.println(b4.done().done());
        System.out.println(b5.done().done());
        System.out.println(b4.done().done());

        System.out.println(b5.done().withEntry("more").append("Carl").build());

    }

}
