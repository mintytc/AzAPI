package me.mintytc.azapi.registry;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @since 1.0.0-R0.1
 *
 */
public interface KeyedRegistry<K, T> {

    void register(K key, T value);

    void unregister(K key);

    boolean contains(K key);

    Optional<T> get(K key);

    Collection<T> values();

    Map<K, T> entries();

    void clear();
}
