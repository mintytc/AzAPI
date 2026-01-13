package me.mintytc.azapi.api.scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * Group of Tasks that can be tracked and cancelled together.
 * Works with Bukkit-scheduled Tasks.
 *
 * @since 1.0.0-R0.1
 */
public class TaskGroup {

	private final List<Task> tasks = new ArrayList<>();

	/**
	 * Track a Task in this group
	 */
	public TaskGroup track(Task task) {
		if (task != null) tasks.add(task);
		return this;
	}

	/**
	 * Cancel all tasks in this group
	 */
	public void cancelAll() {
		for (Task task : tasks) {
			task.cancel();
		}
		tasks.clear();
	}

	/**
	 * Check if all tasks are completed/cancelled
	 */
	public boolean isEmpty() {
		// Remove any finished tasks
		tasks.removeIf(task -> !task.isRunning());
		return tasks.isEmpty();
	}

	/**
	 * Convenience: cancel tasks that are finished automatically
	 */
	public void cleanupFinished() {
		tasks.removeIf(task -> !task.isRunning());
	}

	/**
	 * Get snapshot of tracked tasks
	 */
	public List<Task> getTasks() {
		return new ArrayList<>(tasks);
	}
}
