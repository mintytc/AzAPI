package me.mintytc.azapi.core.easing;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public final class EasingFunctions {

    public static final EasingFunction LINEAR = t -> t;

    public static final EasingFunction EASE_IN_QUAD = t -> t * t;
    public static final EasingFunction EASE_OUT_QUAD = t -> t * (2 - t);
    public static final EasingFunction EASE_IN_OUT_QUAD = t ->
            t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;

    public static final EasingFunction EASE_IN_CUBIC = t -> t * t * t;
    public static final EasingFunction EASE_OUT_CUBIC = t -> {
        double p = t - 1;
        return p * p * p + 1;
    };
    public static final EasingFunction EASE_IN_OUT_CUBIC = t ->
            t < 0.5 ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;

    public static final EasingFunction EASE_IN_QUART = t -> t * t * t * t;
    public static final EasingFunction EASE_OUT_QUART = t -> {
        double p = t - 1;
        return 1 - p * p * p * p;
    };
    public static final EasingFunction EASE_IN_OUT_QUART = t ->
            t < 0.5 ? 8 * t * t * t * t : 1 - 8 * (t - 1) * (t - 1) * (t - 1) * (t - 1);

    private EasingFunctions() {
    } // prevent instantiation
}
