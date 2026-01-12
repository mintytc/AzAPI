package me.mintytc.azapi.easing;

import java.util.Map;
import java.util.TreeMap;

/**
 * @since 1.0.0-R0.1
 *
 */
public class MicroCurve implements Easing.Curve {
    private final TreeMap<Integer, Double> tickProgress = new TreeMap<>();

    public void addPoint(int tick, double percent) {
        tickProgress.put(tick, percent / 100.0);
    }

    @Override
    public double getProgress(double localTime, double totalDuration) {
        int currentTick = (int) Math.floor(localTime);
        Map.Entry<Integer, Double> prev = tickProgress.floorEntry(currentTick);
        Map.Entry<Integer, Double> next = tickProgress.ceilingEntry(currentTick);

        if (prev == null && next == null) return 0;
        if (prev == null) return next.getValue();
        if (next == null) return prev.getValue();
        if (prev.getKey().equals(next.getKey())) return prev.getValue();

        double ratio = (localTime - prev.getKey()) / (next.getKey() - prev.getKey());
        return prev.getValue() + (next.getValue() - prev.getValue()) * ratio;
    }
}
