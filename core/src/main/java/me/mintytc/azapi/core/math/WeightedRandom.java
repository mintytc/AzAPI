package me.mintytc.azapi.core.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @since 1.0.0-R0.1-BASE
 *
 */
public final class WeightedRandom<T> {

    private final List<Entry<T>> entries = new ArrayList<>();
    private double totalWeight = 0;

    public void add(T value, double weight) {
        if (weight <= 0) return;
        totalWeight += weight;
        entries.add(new Entry<>(value, weight));
    }

    public T next(Random random) {
        if (entries.isEmpty()) return null;
        double r = random.nextDouble() * totalWeight;
        double sum = 0;
        for (Entry<T> entry : entries) {
            sum += entry.weight;
            if (r < sum) return entry.value;
        }
        return entries.get(entries.size() - 1).value; // fallback
    }

    public T next() {
        return next(new Random());
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    private static final class Entry<T> {
        final T value;
        final double weight;

        Entry(T value, double weight) {
            this.value = value;
            this.weight = weight;
        }
    }
}
