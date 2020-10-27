package com.skanderj.lucidityengine.input.binds;

import java.util.Map;

import com.skanderj.lucidityengine.core.Action;
import com.skanderj.lucidityengine.core.Engine;
import com.skanderj.lucidityengine.core.Scene;
import com.skanderj.lucidityengine.input.Keyboard;
import com.skanderj.lucidityengine.input.Keyboard.KeyState;
import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;

/**
 *
 * @author Skander Jeddi
 *
 */
public class Bind {
	private final Scene targetScene;
	private final Integer[] targetKeycodes;
	private final Keyboard.KeyState[] targetKeyStates;
	private final Action action;
	private boolean skipChecks;

	public Bind(final Scene scene, final Map<Integer, KeyState> mappings, final Action action) {
		this.targetScene = scene;
		this.targetKeycodes = mappings.keySet().toArray(new Integer[mappings.size()]);
		this.targetKeyStates = mappings.values().toArray(new KeyState[mappings.size()]);
		this.action = action;
		this.skipChecks = (scene == null);
		Logger.log(Bind.class, LogLevel.DEVELOPMENT, "Skip checks (C1)? " + this.skipChecks);
	}

	public Bind(final String sceneIdentifier, final Integer[] keycodes, final KeyState[] states, final Action action) {
		this((Scene) Engine.getObject(sceneIdentifier), keycodes, states, action);
		this.skipChecks = sceneIdentifier.equals("*") || ((Scene) Engine.getObject(sceneIdentifier) == null);
		Logger.log(Bind.class, LogLevel.DEVELOPMENT,
				"Skip checks (C2)? " + this.skipChecks + ", " + Engine.getObject(sceneIdentifier));
	}

	public Bind(final Scene scene, final Integer[] keycodes, final KeyState[] states, final Action action) {
		if (keycodes.length != states.length) {
			Logger.log(Bind.class, LogLevel.FATAL,
					"Size mismatch between keys' array size and keystates' array size (%d vs %d)", keycodes.length,
					states.length);
		}
		this.targetScene = scene;
		this.targetKeycodes = keycodes;
		this.targetKeyStates = states;
		this.action = action;
		this.skipChecks = (scene == null);
		Logger.log(Bind.class, LogLevel.DEVELOPMENT, "Skip checks (C3)? " + this.skipChecks);
	}

	/**
	 * Self explanatory.
	 */
	public Scene targetScene() {
		return this.targetScene;
	}

	/**
	 * Self explanatory.
	 */
	public Integer[] targetKeyCodes() {
		return this.targetKeycodes;
	}

	/**
	 * Self explanatory.
	 */
	public Keyboard.KeyState[] targetKeyStates() {
		return this.targetKeyStates;
	}

	/**
	 * Self explanatory.
	 */
	public Action action() {
		return this.action;
	}

	/**
	 * Self explanatory.
	 */
	public boolean skipChecks() {
		return this.skipChecks;
	}

	@Override
	public String toString() {
		// TODO
		return null;
	}
}