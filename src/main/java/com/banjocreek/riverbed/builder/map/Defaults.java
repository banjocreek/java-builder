package com.banjocreek.riverbed.builder.map;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

final class Defaults<K extends Enum<K>, V> implements MapDelta<K, V> {

    private final EnumMap<K, V> entries;

    public Defaults(final K k, final V v) {
        this.entries = new EnumMap<>(Collections.singletonMap(k, v));
    }

    public Defaults(final Map<K, ? extends V> entries) {
        this.entries = new EnumMap<>(entries);
    }

    @Override
    public void applyTo(final MapKernel<K, V> kernel) {

        kernel.defaults(this.entries);

    }

}
