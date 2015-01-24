package com.banjocreek.riverbed.builder.map;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import com.banjocreek.riverbed.builder.AbstractImmutableBuilder;

public abstract class AbstractImmutableMapBuilder<K extends Enum<K>, V, R, P>
        extends AbstractImmutableBuilder<MapKernel<K, V>, MapDelta<K, V>, R, P> {

    private static <KK extends Enum<KK>, VV, X> Function<MapKernel<KK, VV>, X> adaptConstructor(
            final Function<Map<KK, VV>, X> constructor) {

        return mk -> constructor.apply(mk.merge());

    }

    private static <KK extends Enum<KK>, VV> Supplier<MapKernel<KK, VV>> initializer(
            final Class<KK> keyType) {
        return () -> new MapKernel<>(keyType);
    }

    private static final <KK extends Enum<KK>, VV> MapKernel<KK, VV> mutate(
            final MapKernel<KK, VV> t, final MapDelta<KK, VV> u) {
        u.applyTo(t);
        return t;
    }

    protected AbstractImmutableMapBuilder(
            final AbstractImmutableMapBuilder<K, V, R, P> previous,
            final MapDelta<K, V> delta) {
        super(previous, delta);
    }

    protected AbstractImmutableMapBuilder(final Class<K> keyType,
            final Function<Map<K, V>, P> parentConstructor,
            final Function<Map<K, V>, R> rootConstructor) {
        super(initializer(keyType), AbstractImmutableMapBuilder::mutate,
                adaptConstructor(parentConstructor),
                adaptConstructor(rootConstructor));
    }

    /**
     * Create a delta consisting of defaults to register.
     *
     * @param key
     *            key of default entry
     *
     * @param value
     *            value of default entry
     *
     * @return delta
     */
    protected final MapDelta<K, V> defaults(final K key, final V value) {
        return new Defaults<>(key, value);
    }

    /**
     * Create a delta consisting of defaults to register.
     *
     * @param defaults
     *            default entries.
     *
     * @return delta
     */
    protected final MapDelta<K, V> defaults(final Map<K, ? extends V> defaults) {

        return defaults.isEmpty() ? Nop.instance() : new Defaults<>(defaults);

    }

    /**
     * Create a delta consisting of map entries.
     *
     * @param key
     *            key of entry
     *
     * @param value
     *            value of entry
     *
     * @return delta
     */
    protected final MapDelta<K, V> entries(final K key, final V value) {

        return new Entries<>(key, value);

    }

    /**
     * Create a delta consisting of map entries.
     *
     * @param entries
     *            entries.
     *
     * @return delta
     */
    protected final MapDelta<K, V> entries(final Map<K, ? extends V> entries) {
        return entries.isEmpty() ? Nop.instance() : new Entries<>(entries);

    }

    /**
     * Create a delta consisting of items to remove.
     *
     * @param toRemove
     *            collection of items to remove.
     *
     * @return delta
     */
    protected final MapDelta<K, V> remove(final Collection<K> toRemove) {
        return toRemove.isEmpty() ? Nop.instance() : new Remove<>(toRemove);
    }

    /**
     * Create a delta consisting of items to remove.
     *
     * @param toRemove
     *            item to remove
     *
     * @return delta
     */
    protected final MapDelta<K, V> remove(final K toRemove) {
        return new Remove<>(toRemove);
    }

}
