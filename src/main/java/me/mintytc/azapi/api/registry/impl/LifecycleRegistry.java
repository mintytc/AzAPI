package me.mintytc.azapi.api.registry.impl;

import me.mintytc.azapi.api.interfaces.Loadable;

/**
 * @since 1.0.0-R0.1
 *
 */
public class LifecycleRegistry<K, T extends Loadable>
        extends SimpleKeyedRegistry<K, T> {

    @Override
    public void register(K key, T value) {
        value.load();
        super.register(key, value);
    }

    @Override
    public void unregister(K key) {
        get(key).ifPresent(Loadable::unload);
        super.unregister(key);
    }

    public void unloadAll() {
        values().forEach(Loadable::unload);
        clear();
    }
}
