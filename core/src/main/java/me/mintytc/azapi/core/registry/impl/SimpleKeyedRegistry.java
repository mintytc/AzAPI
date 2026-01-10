package me.mintytc.azapi.core.registry.impl;

import me.mintytc.azapi.core.registry.KeyedRegistry;

import java.util.*;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public class SimpleKeyedRegistry<K, T> implements KeyedRegistry<K, T> {

    protected final Map<K, T> backing = new HashMap<>();

    @Override
    public void register(K key, T value) {
        backing.put(key, value);
    }

    @Override
    public void unregister(K key) {
        backing.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return backing.containsKey(key);
    }

    @Override
    public Optional<T> get(K key) {
        return Optional.ofNullable(backing.get(key));
    }

    @Override
    public Collection<T> values() {
        return Collections.unmodifiableCollection(backing.values());
    }

    @Override
    public Map<K, T> entries() {
        return Collections.unmodifiableMap(backing);
    }

    @Override
    public void clear() {
        backing.clear();
    }
}
