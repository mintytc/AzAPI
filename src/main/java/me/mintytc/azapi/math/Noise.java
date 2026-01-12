package me.mintytc.azapi.math;

import java.util.Random;

/**
 * @since 1.0.0-R0.1
 *
 */
public final class Noise {

    private final Random random;

    public Noise() {
        this.random = new Random();
    }

    public Noise(long seed) {
        this.random = new Random(seed);
    }

    /**
     * Returns a random value in [0,1)
     */
    public double value() {
        return random.nextDouble();
    }

    /**
     * Returns a random value in [min,max)
     */
    public double value(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    /**
     * Returns -1..1
     */
    public double signed() {
        return random.nextDouble() * 2 - 1;
    }

    /**
     * Returns -range..+range
     */
    public double signed(double range) {
        return signed() * range;
    }

    /**
     * Simple 1D noise based on value + repeat
     */
    public double noise1D(double x) {
        int n = (int) x * 1619;
        n = (n << 13) ^ n;
        int nn = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;
        return 1.0 - ((double) nn / 1073741824.0); // -1..1
    }
}
