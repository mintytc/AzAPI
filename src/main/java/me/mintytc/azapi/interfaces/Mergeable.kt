package me.mintytc.azapi.interfaces

/**
 * @since 1.0.0-R0.1
 */
interface Mergeable<T> {
	/**
	 * Merge this object with another of the same type.
	 *
	 * @param other the other object
	 *
	 * @return a new merged instance
	 */
	fun merge(other: T?): T?
}