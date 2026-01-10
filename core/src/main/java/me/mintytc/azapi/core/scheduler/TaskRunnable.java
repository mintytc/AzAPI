package me.mintytc.azapi.core.scheduler;

/**
 * Runnable with tick tracking and cancellation
 *
 * @since 1.0.0-R0.1-BASE
 */
public abstract class TaskRunnable {

    private boolean cancelled = false;
    private long tickCount = 0;

    /**
     * Called each time the task executes
     */
    public abstract void run();

    /**
     * Called internally each tick by Task
     */
    void tick() {
        if (!cancelled) {
            tickCount++;
            run();
        }
    }

    public void cancel() {
        cancelled = true;
        onCancel();
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public long getTickCount() {
        return tickCount;
    }

    public void onCancel() {
    }
}
