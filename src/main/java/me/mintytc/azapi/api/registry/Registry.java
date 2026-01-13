package me.mintytc.azapi.api.registry;

import java.util.Collection;
import java.util.Optional;

/**
 * @since 1.0.0-R0.1
 *
 */
public interface Registry<T> {

	void register(T value);

	void unregister(T value);

	boolean contains(T value);

	Collection<T> values();

	Optional<T> find(java.util.function.Predicate<T> filter);

	void clear();
}
