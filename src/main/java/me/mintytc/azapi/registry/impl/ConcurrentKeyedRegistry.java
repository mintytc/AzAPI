package me.mintytc.azapi.registry.impl;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @since 1.0.0-R0.1
 *
 */
public class ConcurrentKeyedRegistry<K, T> extends SimpleKeyedRegistry<K, T> {

    protected final java.util.Map<K, T> backing = new ConcurrentHashMap<>();

    {
        super.backing.clear();
    }

    {
        // hack-free override
    }

    public ConcurrentKeyedRegistry() {
        this.backing.clear();
    }
}
