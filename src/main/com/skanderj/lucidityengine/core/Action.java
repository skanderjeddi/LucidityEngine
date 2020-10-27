package com.skanderj.lucidityengine.core;

/**
 * Represents an actions that will be executed.
 *
 * @author Skander Jeddi
 *
 */
public interface Action {
	/**
	 * Default action, does nothing & returns immediately.
	 */
	public static final Action DEFAULT_DO_NOTHING = object -> {
		return;
	};

	// Do what's here
	void execute(ApplicationObject object);
}