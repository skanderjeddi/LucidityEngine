package com.skanderj.lucidityengine.graphics.components;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.graphics.OnScreenText;

/**
 * Represents an abstract text, basis for other button classes which can
 * implement their rendering the way they please.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Label extends Component {
	protected OnScreenText text;

	/**
	 * Very basic constructor.
	 */
	public Label(final Application application, final OnScreenText text) {
		super(application);
		this.text = text;
	}

	/**
	 * Related to global components management.
	 */
	@Override
	public final boolean canChangeFocus() {
		return true;
	}

	/**
	 * Related to global components management.
	 */
	@Override
	public final void grantFocus() {
		return;
	}

	/**
	 * Related to global components management.
	 */
	@Override
	public final void revokeFocus() {
		return;
	}

	/**
	 * Self explanatory.
	 */
	public OnScreenText text() {
		return this.text;
	}

	/**
	 * Self explanatory.
	 */
	public void setText(final OnScreenText text) {
		this.text = text;
	}
}