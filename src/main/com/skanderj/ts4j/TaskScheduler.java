package com.skanderj.ts4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;

/**
 *
 * The main class for the TS4J project. Use this class to schedule and cancel
 * tasks.
 *
 * @author Skander Jeddi
 *
 */
public final class TaskScheduler {
	private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
	private static final Map<String, ScheduledFuture<?>> scheduledFutures = new HashMap<>();
	private static final Map<String, Task> tasks = new HashMap<>();

	private TaskScheduler() {
		return;
	}

	public static boolean scheduleTask(final String identifier, final Task task) {
		for (final String futureId : TaskScheduler.scheduledFutures.keySet()
				.toArray(new String[TaskScheduler.scheduledFutures.keySet().size()])) {
			if (TaskScheduler.scheduledFutures.get(futureId).isDone()
					|| TaskScheduler.scheduledFutures.get(futureId).isCancelled()) {
				TaskScheduler.scheduledFutures.remove(futureId);
				TaskScheduler.tasks.remove(futureId);
				Logger.log(TaskScheduler.class, LogLevel.INFO, "Task '%s' has been removed", identifier);
			}
		}
		ScheduledFuture<?> future = null;
		if (task.getPeriod().value != Task.NO_REPEATS) {
			switch (task.type()) {
			case FIXED_DELAY:
				future = TaskScheduler.executor.scheduleWithFixedDelay(task.asRunnable(), task.getInitialDelay().value,
						task.getInitialDelay().unit.convert(task.getPeriod().value, task.getPeriod().unit),
						task.getInitialDelay().unit);
				break;
			case FIXED_RATE:
				future = TaskScheduler.executor.scheduleAtFixedRate(task.asRunnable(), task.getInitialDelay().value,
						task.getInitialDelay().unit.convert(task.getPeriod().value, task.getPeriod().unit),
						task.getInitialDelay().unit);
				break;
			}
		} else {
			future = TaskScheduler.executor.schedule(task.asRunnable(), task.getInitialDelay().value,
					task.getInitialDelay().unit);
		}
		if (future != null) {
			TaskScheduler.scheduledFutures.put(identifier, future);
			TaskScheduler.tasks.put(identifier, task);
			Logger.log(TaskScheduler.class, LogLevel.INFO, "Task '%s' has been scheduled", identifier);
			return true;
		} else {
			return false;
		}
	}

	public static final Task retrieveTask(final String identifier) {
		return TaskScheduler.tasks.get(identifier);
	}

	public static boolean cancelTask(final String identifier, final boolean finish) {
		final ScheduledFuture<?> future = TaskScheduler.scheduledFutures.get(identifier);
		if (future != null) {
			Logger.log(TaskScheduler.class, LogLevel.INFO, "Task '%s' has been cancelled", identifier);
			return future.cancel(finish);
		} else {
			Logger.log(TaskScheduler.class, LogLevel.INFO, "Task '%s' was not cancelled!", identifier);
			return false;
		}
	}

	public static int getRepeatsCounter(final String identifier) {
		return TaskScheduler.tasks.get(identifier) == null ? -1
				: TaskScheduler.tasks.get(identifier).getRepeatsCounter();
	}
}
