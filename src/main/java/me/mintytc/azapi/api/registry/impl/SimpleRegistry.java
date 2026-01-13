package me.mintytc.azapi.api.registry.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import me.mintytc.azapi.api.registry.Registry;

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
