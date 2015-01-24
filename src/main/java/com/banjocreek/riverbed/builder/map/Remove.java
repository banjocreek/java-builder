package com.banjocreek.riverbed.builder.map;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

final class Remove<K extends Enum<K>, V> implements MapDelta<K, V> {

    private final Set<K> keys;

    public Remove(final Collection<K> keys) {
        this.keys = EnumSet.copyOf(keys);
    }

    public Remove(final K key) {
        this.keys = EnumSet.of(key);
    }

    @Override
    public void applyTo(final MapKernel<K, V> kernel) {
        kernel.remove(this.keys);
    }

}
