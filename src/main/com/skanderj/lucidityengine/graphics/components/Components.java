package com.skanderj.lucidityengine.graphics.components;

import com.skanderj.lucidityengine.core.Engine;

/**
 * A class used for dealing with the focus. Can't be instantiated, only static
 * methods.
 *
 * @author Skander Jeddi
 *
 */
public final class Components {
	// Graphical debug - mostly drawing components' bounds
	public static final boolean GRAPHICAL_DEBUG = false;

	// Components map, for easily finding any component from any class
	// Makes it possible to not instantiate any actual component
	private static Component inFocus = null;

	private Components() {
		return;
	}

	/**
	 * Gives focus the provided component if focus can be revoked from the currently
	 * focused component.
	 */
	public static synchronized void giveFocus(final String identifier) {
		final Component component = (Component) Engine.getObject(identifier);
		if (component == null) {
			Components.inFocus = null;
			return;
		}
		if (Components.inFocus == null) {
			component.grantFocus();
			Components.inFocus = component;
		} else {
			if (Components.inFocus.canChangeFocus()) {
				Components.inFocus.revokeFocus();
				component.grantFocus();
				Components.inFocus = component;
			}
		}
	}

	/**
	 * Gives focus the provided component if focus can be revoked from the currently
	 * focused component.
	 */
	public static synchronized void giveFocus(final Component component) {
		if (component == null) {
			Components.inFocus = null;
			return;
		}
		if (Components.inFocus == null) {
			component.grantFocus();
			Components.inFocus = component;
		} else {
			if (Components.inFocus.canChangeFocus()) {
				Components.inFocus.revokeFocus();
				component.grantFocus();
				Components.inFocus = component;
			}
		}
	}

	/**
	 * Resets the currently focused component.
	 */
	public static synchronized void revokeFocus() {
		Components.inFocus = null;
	}

	/**
	 * Self explanatory.
	 *
	 * @return
	 */
	public static Component getInFocus() {
		return Components.inFocus;
	}
}