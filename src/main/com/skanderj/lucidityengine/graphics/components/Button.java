package com.skanderj.lucidityengine.graphics.components;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.core.Action;
import com.skanderj.lucidityengine.input.Mouse;

/**
 * Represents an abstract button, basis for other button classes which can
 * implement their rendering the way they please.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Button extends Component {
	protected ComponentState previousState, state;
	protected Action[] actions;
	protected boolean hasFocus, mouseWasIn;

	/**
	 * Basic constructor: position.
	 */
	public Button(final Application application) {
		super(application);
		this.previousState = ComponentState.IDLE;
		this.state = ComponentState.IDLE;
		this.actions = new Action[4];
		// Set default actions (do nothing) for every currentState
		for (int index = 0; index < this.actions.length; index += 1) {
			this.actions[index] = Action.DEFAULT_DO_NOTHING;
		}
		this.hasFocus = false;
	}

	/**
	 * This is where all the logic of the button happens. We check the mouse
	 * position and the mouse left click, and we deduce the currentState of the
	 * button then run the appropriate button actions accordingly.
	 */
	@Override
	public void update() {
		this.previousState = this.state;
		final int mouseX = this.application.mouse().getX(), mouseY = this.application.mouse().getY();
		final boolean mouseIn = this.containsMouse(mouseX, mouseY),
				mouseClicked = this.application.mouse().isButtonDown(Mouse.BUTTON_LEFT);
		if (mouseIn && mouseClicked && !this.hasFocus) {
			this.hasFocus = true;
		}
		if (mouseClicked && this.hasFocus && this.mouseWasIn) {
			this.state = ComponentState.HELD;
			this.mouseWasIn = true;
		} else if (mouseIn && !mouseClicked) {
			this.state = ComponentState.HOVERED;
			this.hasFocus = false;
			this.mouseWasIn = true;
		} else {
			this.state = ComponentState.IDLE;
			this.hasFocus = false;
			this.mouseWasIn = false;
		}
		if ((this.previousState == ComponentState.HELD)
				&& ((this.state == ComponentState.IDLE) || (this.state == ComponentState.HOVERED)) && mouseIn) {
			this.state = ComponentState.ACTIVE;
		}
		this.actions[this.state.identifier()].execute(this);
	}

	/**
	 * Sets the button actions that will be executed when the provided currentState
	 * is the current currentState.
	 */
	public void mapActionToState(final ComponentState state, final Action action) {
		this.actions[state.identifier()] = action;
	}

	/**
	 * Related to global components management. We can only switch focus out of the
	 * button if it's completely idle.
	 */
	@Override
	public final boolean canChangeFocus() {
		return this.state == ComponentState.IDLE;
	}

	/**
	 * Related to global components management. Focus management is different for
	 * buttons so these do nothing.
	 */
	@Override
	public final void grantFocus() {
		return;
	}

	/**
	 * Related to global components management. Focus management is different for
	 * buttons so these do nothing.
	 */
	@Override
	public final void revokeFocus() {
		return;
	}

	/**
	 * Self explanatory.
	 */
	public Action[] actions() {
		return this.actions;
	}

	/**
	 * Self explanatory. Can be used to set multiple actions at once.
	 */
	public void setActions(final Action[] actions) {
		this.actions = actions;
	}
}