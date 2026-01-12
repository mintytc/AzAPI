package me.mintytc.azapi.api.registry.impl;

import me.mintytc.azapi.api.registry.Registry;

import java.util.*;
import java.util.function.Predicate;

/**
 * @since 1.0.0-R0.1
 *
 */
public class SimpleRegistry<T> implements Registry<T> {

    protected final Set<T> backing = new HashSet<>();

    @Override
    public void register(T value) {
        backing.add(value);
    }

    @Override
    public void unregister(T value) {
        backing.remove(value);
    }

    @Override
    public boolean contains(T value) {
        return backing.contains(value);
    }

    @Override
    public Collection<T> values() {
        return Collections.unmodifiableSet(backing);
    }

    @Override
    public Optional<T> find(Predicate<T> filter) {
        return backing.stream().filter(filter).findFirst();
    }

    @Override
    public void clear() {
        backing.clear();
    }
}
