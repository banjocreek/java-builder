package com.banjocreek.riverbed.builder.map;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

final class Entries<K extends Enum<K>, V> implements MapDelta<K, V> {

    private final EnumMap<K, V> entries;

    public Entries(final K k, final V v) {
        this.entries = new EnumMap<>(Collections.singletonMap(k, v));
    }

    public Entries(final Map<K, ? extends V> entries) {
        this.entries = new EnumMap<>(entries);
    }

    @Override
    public void applyTo(final MapKernel<K, V> kernel) {

        kernel.entries(this.entries);

    }

}
