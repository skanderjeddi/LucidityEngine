package com.skanderj.lucidityengine.core;

import java.util.HashSet;
import java.util.Set;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.graphics.transitions.Transition;
import com.skanderj.lucidityengine.input.binds.Binds;
import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;

/**
 * 
 * The {@link Scene} class is used to group a bunch of application objects to be
 * updated & rendered at the same time. This is used to specify which
 * application objects are active at any given time.
 * 
 * @author Skander Jeddi
 *
 */
public abstract class Scene extends ApplicationObject {
	// List of objects' identiifers to be updated & rendered
	private final Set<String> sceneObjectsIdentifiers;
	// Optional transitions
	private Transition inTransition, outTransition;

	/**
	 * Keep a copy of the mother application & initialize the transitions to
	 * <code>null</code>.
	 * 
	 * @param application the mother application
	 */
	public Scene(final Application application) {
		super(application);
		this.sceneObjectsIdentifiers = new HashSet<String>();
		this.inTransition = this.outTransition = null;
	}

	public abstract void enter();

	public abstract void exit();

	/**
	 * Adds multiple objects' identifiers at once. Returns this {@link Scene} object
	 * for easy declaration. Typical usage is:
	 * <code>Scene scene = new Scene(...).addObjects(...);</code>
	 * 
	 * @param list the target identifiers
	 * @return this same scene object
	 */
	public final Scene addObjects(final String[] list) {
		for (final String identifiers : list) {
			this.addComponent(identifiers);
		}
		return this;
	}

	/**
	 * Adds a single object identifier to be updated & rendered in this scene.
	 * 
	 * @param identifier the object's identifier
	 */
	public final void addComponent(final String identifier) {
		this.sceneObjectsIdentifiers.add(identifier);
		Logger.log(Scene.class, LogLevel.DEBUG, "Object '%s' has been added to the scene!", identifier);
	}

	/**
	 * Removes a single object identifier from this scene.
	 * 
	 * @param identifier the object's identifier
	 */
	public final boolean removeComponent(final String identifier) {
		Logger.log(Scene.class, LogLevel.DEBUG, "Object '%s' has been removed from the scene!", identifier);
		return this.sceneObjectsIdentifiers.remove(identifier);
	}

	/**
	 * Updates the scene's logic. Calls {@link Engine#updateObjects()} &
	 * {@link Binds#update(Application)}.
	 */
	@Override
	public final void update() {
		Engine.updateObjects();
		Binds.update(this.application);
	}

	/**
	 * Renders the scene. Calls {@link Engine#renderObjects()}.
	 */
	@Override
	public final void render() {
		Engine.renderObjects();
	}

	/**
	 * Scenes' priority doesn't matter as it is never directly updated or rendered.
	 */
	@Override
	public Priority priority() {
		return Priority.NORMAL;
	}

	/**
	 * @return a set of identifiers
	 */
	public Set<String> sceneObjectsIdentifiers() {
		return this.sceneObjectsIdentifiers;
	}

	/**
	 * @return the entering transition for this scene
	 */
	public Transition inTransition() {
		return this.inTransition;
	}

	/**
	 * @return the exiting transition for this scene
	 */
	public Transition outTransition() {
		return this.outTransition;
	}

	/**
	 * Self explanatory.
	 * 
	 * @param in the new entering transition
	 */
	public void setInTransition(final Transition in) {
		this.inTransition = in;
	}

	/**
	 * Self explanatory.
	 * 
	 * @param in the new exiting transition
	 */
	public void setOutTransation(final Transition out) {
		this.outTransition = out;
	}

	@Override
	public String toString() {
		// TODO
		return null;
	}
}
