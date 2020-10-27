package com.skanderj.lucidityengine.graphics.components;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.input.Mouse;

/**
 * Represents an abstract slider, basis for other slider classes which can
 * implements their rendering the way they please.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Slider extends Component {
	protected double minimumValue, maximumValue;
	protected boolean hasFocus, globalFocus;

	/**
	 * Basic constructor: position, size, minimum/maximum value, default value,
	 */
	public Slider(final Application application, final float min, final float max, final float defaultValue) {
		super(application);
		this.minimumValue = Math.min(min, max);
		this.maximumValue = Math.max(min, max);
		this.hasFocus = false;
		this.globalFocus = false;
	}

	/**
	 * This is where all the logic of the slider happens. We check the mouse
	 * position and the mouse left click, and we deduce the currentState of the
	 * slider then move the slider accordingly.
	 */
	@Override
	public synchronized void update() {
		if ((this.containsMouse(this.application.mouse().getX(), this.application.mouse().getY()) || this.hasFocus)
				&& this.globalFocus) {
			if (this.application.mouse().isButtonDown(Mouse.BUTTON_LEFT)) {
				this.hasFocus = true;
			} else {
				this.hasFocus = false;
			}
		}
	}

	/**
	 * Related to global components management. We can only switch focus out of the
	 * slider if it's not currently being moved (represented by the Slider#hasFocus
	 * variable).
	 */
	@Override
	public final boolean canChangeFocus() {
		return !this.hasFocus;
	}

	/**
	 * Gives global focus.
	 */
	@Override
	public final void grantFocus() {
		this.globalFocus = true;
	}

	/**
	 * Removes global focus.
	 */
	@Override
	public final void revokeFocus() {
		this.globalFocus = false;
	}

	/**
	 * Self explanatory
	 */
	public abstract double value();

	/**
	 * Self explanatory.
	 */
	public double minimumValue() {
		return this.minimumValue;
	}

	/**
	 * Self explanatory.
	 */
	public double maximumValue() {
		return this.maximumValue;
	}

	/**
	 * Self explanatory.
	 */
	public void setMinimumValue(final float minimumValue) {
		this.minimumValue = minimumValue;
	}

	/**
	 * Self explanatory.
	 */
	public void setMaximumValue(final float maximumValue) {
		this.maximumValue = maximumValue;
	}
}