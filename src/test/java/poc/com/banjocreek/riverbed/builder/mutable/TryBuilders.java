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
package poc.com.banjocreek.riverbed.builder.mutable;

import java.util.Map;

public class TryBuilders {

    public static void main(final String[] args) {

        final MapBuilder<Map<String, String>> b0 = MapBuilder.create();
        System.out.println(b0.merge());
        final MapBuilder<Map<String, String>> b1 = b0.withEntry("Key", "Value");
        System.out.println(b0.merge());
        System.out.println(b1.merge());
        final MapBuilder<Map<String, String>> b2 = b1.withEntry("Key2",
                "ValueTwo");
        System.out.println(b0.merge());
        System.out.println(b1.merge());
        System.out.println(b2.merge());

        final ThingBuilder<String> b4 = ThingBuilder.create().append("Hi");
        System.out.println(b4.merge());
        final ThingBuilder<String> b5 = b4.append(" World");
        System.out.println(b4.merge());
        System.out.println(b5.merge());

        final ThingBuilder<MapBuilder<Map<String, String>>> b6 = b2
                .withEntry("greet").append("Hi").append(" World");
        System.out.println(b2.merge());
        System.out.println(b0.merge());
        b6.merge();
        System.out.println(b2.merge());
        System.out.println(b0.merge());

        // final ThingBuilder<Map<String, String>, MapBuilder<Map<String,
        // String>, Map<String, String>>> b4 = b2
        // .withEntry("extra").append("Hi");
        // final ThingBuilder<Map<String, String>, MapBuilder<Map<String,
        // String>, Map<String, String>>> b5 = b4
        // .append(" World");
        //
        // System.out.println(b4.build());
        // System.out.println(b5.build());
        // System.out.println(b4.build());
        //
        // System.out.println(b4.done().done());
        // System.out.println(b5.done().done());
        // System.out.println(b4.done().done());
        //
        // System.out.println(b5.done().withEntry("more").append("Carl").build());

    }

}
