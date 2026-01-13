package me.mintytc.azapi.api.interfaces;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public interface Mergeable<T> {
	/**
	 * Merge this object with another of the same type.
	 *
	 * @param other the other object
	 * @return a new merged instance
	 */
	T merge(T other);
}