package me.mintytc.azapi.core.registry;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public interface Freezable {
    void freeze();

    boolean isFrozen();
}
