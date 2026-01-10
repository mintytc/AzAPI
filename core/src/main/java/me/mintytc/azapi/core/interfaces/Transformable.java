package me.mintytc.azapi.core.interfaces;

import java.util.function.Function;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public interface Transformable<T> {
    /**
     * Transforms this object into another of the same type.
     *
     * @param transformer function describing how to transform
     *
     * @return transformed object
     */
    T transform(Function<T, T> transformer);
}