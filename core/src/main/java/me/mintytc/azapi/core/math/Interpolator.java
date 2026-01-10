package me.mintytc.azapi.core.math;

import me.mintytc.azapi.core.easing.EasingFunction;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public final class Interpolator {

    private final double start;
    private final double end;

    public Interpolator(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public double linear(double t) {
        return start + (end - start) * t;
    }

    public double ease(EasingFunction function, double t) {
        return start + (end - start) * function.apply(t);
    }

    public double clamp(double value) {
        if (value < start) return start;
        if (value > end) return end;
        return value;
    }

    public double start() {
        return start;
    }

    public double end() {
        return end;
    }
}
