package me.mintytc.azapi.core.interfaces;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public interface Weightable {
    /**
     * @return a numeric weight, higher usually = more important
     */
    double weight();
}