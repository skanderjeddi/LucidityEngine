package com.skanderj.lucidityengine.graphics.components;

/**
 * Represents all the possible states of a button. IDLE: mouse is out and the
 * button doesn't have focus. HOVERED: mouse is over the button, not clicked and
 * no focus yet. HELD: mouse is over the button and clicked or mouse is clicked
 * and focus is on the button. Basically means you can click and leave the
 * button area to cancel your click. ACTIVE: On the transition between HELD and
 * IDLE or between HELD and HOVERED, where you should assign the actual actions
 * to your button.
 *
 * @author Skander
 *
 */
public enum ComponentState {
	IDLE(0), HOVERED(1), HELD(2), ACTIVE(3);

	// An identifier for easier access in other classes (#Button)
	private final int identifier;

	private ComponentState(final int identifier) {
		this.identifier = identifier;
	}

	public final int identifier() {
		return this.identifier;
	}
}