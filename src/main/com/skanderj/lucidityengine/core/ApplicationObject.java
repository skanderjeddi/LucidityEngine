package com.skanderj.lucidityengine.core;

import com.skanderj.lucidityengine.Application;

/**
 * 
 * Application objects are the building blocks of every application & can
 * represent multiple elements to be updated & drawn.
 * 
 * @author Skander Jeddi
 *
 */
public abstract class ApplicationObject implements Comparable<ApplicationObject> {
	// Always keep a reference to the mother application
	protected final Application application;

	/**
	 * Basic constructor. Saves the mother application.
	 * 
	 * @param application
	 */
	public ApplicationObject(final Application application) {
		this.application = application;
	}

	/**
	 * Called every cycle of the updating thread.
	 */
	public abstract void update();

	/**
	 * Called every cycle of the rendering thread.
	 */
	public abstract void render();

	/**
	 * Self explanatory.
	 * 
	 * @return the mother application
	 */
	public Application application() {
		return this.application;
	}

	/**
	 * Self explanatory.
	 * 
	 * @return the priority of the object for updating & rendering (See
	 *         {@link Priority}
	 */
	public abstract Priority priority();

	/**
	 * From the {@link Comparable} interface.
	 */
	@Override
	public final int compareTo(final ApplicationObject object) {
		return (this.priority().getPriorityIndex() - object.priority().getPriorityIndex());
	}

	/**
	 * Force all subclasses to have an actual description when printed.
	 */
	@Override
	public abstract String toString();
}
