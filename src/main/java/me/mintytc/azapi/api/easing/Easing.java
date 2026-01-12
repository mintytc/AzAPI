package me.mintytc.azapi.api.easing;

import me.mintytc.azapi.api.scheduler.Task;
import me.mintytc.azapi.api.scheduler.TaskGroup;
import me.mintytc.azapi.api.scheduler.TaskRunnable;
import me.mintytc.azapi.plugin.AzAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Easing system using AzAPI Task framework
 *
 * @since 1.0.0-R0.1
 */
public abstract class Easing<T> {

    protected final List<Keyframe<T>> keyframes = new ArrayList<>();

    public void addKeyframe(T value, double time, Curve curve) {
        keyframes.add(new Keyframe<>(value, time, curve));
    }

    public abstract T interpolate(T from, T to, double progress);

    /**
     * Plays the easing automatically using the Task system.
     *
     * @param consumer Called each tick with current keyframe and value
     * @param mode     Playback mode (NORMAL, LOOP, PINGPONG)
     * @param onFinish Optional callback when NORMAL playback finishes
     * @param group    Optional TaskGroup to attach this easing to
     *
     * @return PlaybackHandle to cancel manually
     */
    public PlaybackHandle play(BiConsumer<Keyframe<T>, T> consumer,
                               PlaybackMode mode,
                               Consumer<Void> onFinish,
                               TaskGroup group) {
        if (keyframes.size() < 2) return null;
        final double totalTime = keyframes.get(keyframes.size() - 1).time;

        TaskRunnable runnable = new TaskRunnable() {
            double tick = 0;
            boolean forward = true;

            @Override
            public void run() {
                if (tick > totalTime) {
                    switch (mode) {
                        case NORMAL:
                            cancel();
                            if (onFinish != null) onFinish.accept(null);
                            return;
                        case LOOP:
                            tick = 0;
                            break;
                        case PINGPONG:
                            forward = !forward;
                            tick = forward ? 0 : totalTime;
                            break;
                    }
                }

                double currentTick = forward ? tick : totalTime - tick;
                T value = getValue(currentTick);
                Keyframe<T> currentKeyframe = getCurrentKeyframe(currentTick);
                consumer.accept(currentKeyframe, value);

                tick++;
            }

            @Override
            public void onCancel() {
                if (onFinish != null && mode != PlaybackMode.NORMAL) {
                    onFinish.accept(null);
                }
            }
        };

        // Build the task using TaskBuilder
        Task task = Task.builder(AzAPI.INSTANCE).runnable(runnable)
                .interval(1) // run every tick
                .build();

        // Attach to group if provided
        if (group != null) group.track(task);

        return new PlaybackHandle(task);
    }

    public T getValue(double elapsedTime) {
        if (keyframes.isEmpty()) return null;
        if (keyframes.size() == 1) return keyframes.get(0).value;

        Keyframe<T> prev = keyframes.get(0);
        for (int i = 1; i < keyframes.size(); i++) {
            Keyframe<T> next = keyframes.get(i);
            if (elapsedTime <= next.time) {
                double segmentDuration = next.time - prev.time;
                double localTime = elapsedTime - prev.time;
                double progress = prev.curve.getProgress(localTime, segmentDuration);
                return interpolate(prev.value, next.value, progress);
            }
            prev = next;
        }
        return keyframes.get(keyframes.size() - 1).value;
    }

    private Keyframe<T> getCurrentKeyframe(double elapsedTime) {
        Keyframe<T> prev = keyframes.get(0);
        for (int i = 1; i < keyframes.size(); i++) {
            Keyframe<T> next = keyframes.get(i);
            if (elapsedTime <= next.time) return prev;
            prev = next;
        }
        return prev;
    }

    public enum PlaybackMode {
        NORMAL,   // play once forward
        LOOP,     // repeat from start
        PINGPONG  // forward then backward repeatedly
    }

    // -----------------------------
    // Inner classes
    // -----------------------------

    public interface Curve {
        double getProgress(double localTime, double totalDuration);
    }

    public static class Keyframe<T> {
        public final T value;
        public final double time;
        public final Curve curve;

        public Keyframe(T value, double time, Curve curve) {
            this.value = value;
            this.time = time;
            this.curve = curve;
        }
    }

    /**
     * Playback handle for manual cancellation
     */
    public static class PlaybackHandle {
        private final Task task;

        public PlaybackHandle(Task task) {
            this.task = task;
        }

        public void cancel() {
            task.cancel();
        }

        public boolean isCancelled() {
            return !task.isRunning();
        }

        public long getTicks() {
            return task.getTicks();
        }
    }
}
