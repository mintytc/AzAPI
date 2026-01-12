package me.mintytc.azapi.interfaces

import java.util.function.Function

/**
 * @since 1.0.0-R0.1
 */
interface Transformable<T> {
	/**
	 * Transforms this object into another of the same type.
	 *
	 * @param transformer function describing how to transform
	 *
	 * @return transformed object
	 */
	fun transform(transformer: Function<T?, T?>?): T?
}