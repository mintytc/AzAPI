package me.mintytc.azapi.core.interfaces;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public interface Similar<T> {

    /**
     * Calculates the similarity between this object and another.
     *
     * @param other the other object
     *
     * @return a double between 0.0 and 1.0 (0 = no similarity, 1 = identical)
     */
    double similarity(T other);

}