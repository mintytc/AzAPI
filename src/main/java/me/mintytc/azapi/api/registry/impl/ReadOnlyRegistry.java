package me.mintytc.azapi.api.registry.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import me.mintytc.azapi.api.registry.KeyedRegistry;

/**
 * @since 1.0.0-R0.1
 *
 */
public class ReadOnlyRegistry<K, T> implements KeyedRegistry<K, T> {

	private final KeyedRegistry<K, T> delegate;

	public ReadOnlyRegistry(KeyedRegistry<K, T> delegate) {
		this.delegate = delegate;
	}

	public void register(K key, T value) {
		throw new UnsupportedOperationException();
	}

	public void unregister(K key) {
		throw new UnsupportedOperationException();
	}

	public boolean contains(K key) {
		return delegate.contains(key);
	}

	public Optional<T> get(K key) {
		return delegate.get(key);
	}

	public Collection<T> values() {
		return delegate.values();
	}

	public Map<K, T> entries() {
		return delegate.entries();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}
}
