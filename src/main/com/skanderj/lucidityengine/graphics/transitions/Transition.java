package com.skanderj.lucidityengine.graphics.transitions;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.core.ApplicationObject;
import com.skanderj.lucidityengine.core.Priority;

/**
 * Represents a basic graphical transition.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Transition extends ApplicationObject {
	protected int durationInFrames, timer;
	protected boolean isDone;

	public Transition(final Application application, final int durationInFrames) {
		super(application);
		this.durationInFrames = durationInFrames;
		this.timer = 0;
		this.isDone = false;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void update() {
		if (!this.isDone) {
			this.timer += 1;
			if (this.timer >= this.durationInFrames) {
				this.isDone = true;
			}
		}
	}

	/**
	 * Self explanatory.
	 */
	public final int durationInFrames() {
		return this.durationInFrames;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public Priority priority() {
		return Priority.EXTREMELY_LOW;
	}

	/**
	 * Self explanatory.
	 */
	public boolean isDone() {
		return this.isDone;
	}

	public final void reset() {
		this.timer = 0;
		this.isDone = false;
	}
}