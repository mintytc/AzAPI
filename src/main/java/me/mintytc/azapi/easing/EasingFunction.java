package me.mintytc.azapi.easing;

/**
 * Represents a 0..1 → 0..1 easing function.
 *
 * @since 1.0.0-R0.1
 */
@FunctionalInterface
public interface EasingFunction {
    /**
     * Apply the easing function to t (0..1)
     *
     * @param t input, usually 0..1
     *
     * @return eased value, usually 0..1
     */
    double apply(double t);
}
