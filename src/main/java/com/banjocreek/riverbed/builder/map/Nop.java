package com.banjocreek.riverbed.builder.map;

final class Nop<K extends Enum<K>, V> implements MapDelta<K, V> {

    private static final Nop<?, ?> INSTANCE = new Nop<>();

    @SuppressWarnings("unchecked")
    static <KK extends Enum<KK>, VV> Nop<KK, VV> instance() {
        return (Nop<KK, VV>) INSTANCE;
    }

    @Override
    public void applyTo(final MapKernel<K, V> kernel) {
    }

}
