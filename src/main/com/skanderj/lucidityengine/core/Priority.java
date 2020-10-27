package com.skanderj.lucidityengine.core;

/**
 * 
 * Represents an application object's priority relative to all the others. The
 * higher the priority, the earlier the object will be updated & drawn. This
 * acts like a z-index in most 2D engines & games.
 * 
 * @author Skander Jeddi
 *
 */
public enum Priority {
	MONITOR(100), EXTREMELY_LOW(10), LOW(5), NORMAL(0), HIGH(-5), EXTREMELY_HIGH(-10), CRITICAL(-100);

	private final int priorityIndex;

	Priority(final int prioIndex) {
		this.priorityIndex = prioIndex;
	}

	public int getPriorityIndex() {
		return this.priorityIndex;
	}
}
