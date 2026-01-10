package me.mintytc.azapi.core.scheduler;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Represents a scheduled task using Bukkit scheduler.
 * Supports one-off delayed tasks or repeating tasks.
 *
 * @since 1.0.0-R0.1-BASE
 */
public class Task {

    private final JavaPlugin plugin;
    private final TaskRunnable runnable;
    private final int interval; // ticks between executions, 0 = one-off
    private final int delay;    // ticks before first execution
    private BukkitTask bukkitTask;
    private boolean running = false;

    private Task(JavaPlugin plugin, TaskRunnable runnable, int interval, int delay) {
        this.plugin = plugin;
        this.runnable = runnable;
        this.interval = Math.max(0, interval);
        this.delay = Math.max(0, delay);
        schedule();
    }

    public static Builder builder(JavaPlugin plugin) {
        return new Builder(plugin);
    }

    private void schedule() {
        running = true;

        if (interval == 0) {
            // One-off delayed task
            bukkitTask = plugin.getServer().getScheduler().runTaskLater(
                    plugin,
                    () -> {
                        if (!runnable.isCancelled()) runnable.tick();
                        running = false;
                    },
                    delay
            );
        } else {
            // Repeating task
            bukkitTask = plugin.getServer().getScheduler().runTaskTimer(
                    plugin,
                    () -> {
                        if (runnable.isCancelled()) {
                            cancel();
                            return;
                        }
                        runnable.tick();
                    },
                    delay,
                    interval
            );
        }
    }

    public void cancel() {
        if (bukkitTask != null) bukkitTask.cancel();
        runnable.cancel();
        running = false;
    }

    public boolean isRunning() {
        return running && !runnable.isCancelled();
    }

    // ----------------------------
    // BUILDER
    // ----------------------------

    public long getTicks() {
        return runnable.getTickCount();
    }

    public static class Builder {
        private final JavaPlugin plugin;
        private TaskRunnable runnable;
        private int delay = 0;
        private int interval = 0;

        Builder(JavaPlugin plugin) {
            this.plugin = plugin;
        }

        public Builder runnable(TaskRunnable runnable) {
            this.runnable = runnable;
            return this;
        }

        /**
         * Set delay before first execution (in ticks)
         */
        public Builder delay(int delay) {
            this.delay = delay;
            return this;
        }

        /**
         * Set interval between executions (in ticks)
         * Set interval = 0 for one-off delayed task
         */
        public Builder interval(int interval) {
            this.interval = interval;
            return this;
        }

        public Task build() {
            if (runnable == null)
                throw new IllegalStateException("TaskRunnable cannot be null");
            return new Task(plugin, runnable, interval, delay);
        }
    }
}