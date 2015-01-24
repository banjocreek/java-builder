package com.banjocreek.riverbed.builder.map;

public interface MapDelta<K extends Enum<K>, V> {

    void applyTo(MapKernel<K, V> kernel);

}
