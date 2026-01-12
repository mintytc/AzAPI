package me.mintytc.azapi.registry.impl;

import me.mintytc.azapi.registry.Freezable;

/**
 * @since 1.0.0-R0.1
 *
 */
public class FreezableKeyedRegistry<K, T> extends SimpleKeyedRegistry<K, T>
        implements Freezable {

    private boolean frozen;

    @Override
    public void register(K key, T value) {
        if (frozen) throw new IllegalStateException("Registry is frozen");
        super.register(key, value);
    }

    @Override
    public void unregister(K key) {
        if (frozen) throw new IllegalStateException("Registry is frozen");
        super.unregister(key);
    }

    public void freeze() {
        frozen = true;
    }

    public boolean isFrozen() {
        return frozen;
    }
}
