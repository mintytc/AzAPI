package me.mintytc.azapi.math;

/**
 * @since 1.0.0-R0.1
 *
 */
public final class Range<T extends Number & Comparable<T>> {

    private final T min;
    private final T max;

    public Range(T min, T max) {
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("min cannot be greater than max");
        }
        this.min = min;
        this.max = max;
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }

    public boolean contains(T value) {
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    public double lerp(double t) {
        return min.doubleValue() + t * (max.doubleValue() - min.doubleValue());
    }

    public double clamp(double value) {
        return Math.max(min.doubleValue(), Math.min(max.doubleValue(), value));
    }

    @Override
    public String toString() {
        return "Range[" + min + ", " + max + "]";
    }
}
