package com.skanderj.lucidityengine.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.skanderj.lucidityengine.graphics.transitions.Transition;
import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;
import com.skanderj.lucidityengine.util.Utilities;
import com.skanderj.ts4j.Task;
import com.skanderj.ts4j.TaskScheduler;
import com.skanderj.ts4j.TaskType;

/**
 *
 * Main engine class. All the objects & scenes registering happens here. Scene
 * switching, updating & rendering is also handled in this class. Takes care of
 * redirecting system outputs (See {@link Engine#initialize()}.
 *
 * @author Skander Jeddi
 *
 */
public final class Engine {
	// Release name/number
	public static final String RELEASE = "A0f-r";
	// No point in initializing the engine multiple times
	private static boolean isEngineInitialized = false;

	// Main registry for application objects
	private static final Map<String, ApplicationObject> registry = new HashMap<String, ApplicationObject>();

	// Scenes map
	private static final Map<String, Scene> scenesMap = new HashMap<String, Scene>();
	// Current active scene being updated & drawn
	private static Scene currentScene;

	/**
	 * Not instanciable.
	 */
	private Engine() {
		return;
	}

	/**
	 * Self explanatory.
	 *
	 * @param identifier a unique identifier for the scene
	 * @param scene      the actual scene
	 */
	public static final void registerScene(final String identifier, final Scene scene) {
		Engine.registry.put(identifier, scene);
		Engine.scenesMap.put(identifier, scene);
	}

	/**
	 * Switches to a new scene. Does nothing if the provided scene identifier
	 * matches the current's. Handles transitions if there are any.
	 *
	 * @param newSceneIdentifier the new scene to lookup & enter
	 */
	public static final void switchToScene(final String newSceneIdentifier) {
		// If the identifier matches the current scene's, do nothing
		if (Engine.currentScene == Engine.scenesMap.get(newSceneIdentifier)) {
			return;
		}
		if (Engine.currentScene == null) { // If there's no current scene, we can go straight to the in animation of the
											// new animation
			Engine.currentScene = Engine.scenesMap.get(newSceneIdentifier);
			if (Engine.currentScene != null) { // If a scene with the specified identifier exists, do the rest
				if (Engine.currentScene.inTransition() != null) { // If the new scene has an in animation, play it
					Engine.setObject("current-scene-in-transition", Engine.currentScene.inTransition()); // Register
																											// the
					// transition in
					// the registry
					Engine.currentScene.addComponent("current-scene-in-transition"); // Add the transition to the scene
																						// so it is rendered
					((Transition) Engine.getObject("current-scene-in-transition")).reset();
					TaskScheduler.cancelTask("transition", false);
//					if (TaskScheduler.cancelTask("transition", false)) { // If there's already an
//																			// transition
//																			// playing, cancel it and
//																			// simulate a
//																			// "natural" finish
//						Engine.currentScene.removeComponent("current-scene-in-transition");
//					}
					// Schedule a task to remove the transition for the objects list & remove it
					// from the registry for good measure after the transition duration has elapsed
					TaskScheduler.scheduleTask("transition",
							new Task(
									Utilities.framesToMS(Engine.currentScene.inTransition().durationInFrames(),
											Engine.currentScene.application().getTargetUpdatesPerSecond()),
									Task.NO_REPEATS, TimeUnit.MILLISECONDS) {
								@Override
								public TaskType type() {
									return TaskType.FIXED_DELAY;
								}

								@Override
								public void execute() {
									// The transition should be done or basically done by now, so remove it for the
									// scene & from the registry again for good measure
									Engine.currentScene.removeComponent("current-scene-in-transition");
								}
							});
				} else { // If the new scene doesn't have an in transition, immediately show it
					Engine.currentScene = Engine.scenesMap.get(newSceneIdentifier);
				}
			}
		} else if (Engine.currentScene != null) { // 2nd case: if there's currently a scene active
			if (Engine.currentScene.outTransition() != null) { // If the current active scene has an out transition, add
																// the
				// transition to the scene objects & to the registry of course
				Engine.setObject("current-scene-out-transition", Engine.currentScene.outTransition());
				Engine.currentScene.addComponent("current-scene-out-transition");
				((Transition) Engine.getObject("current-scene-out-transition")).reset();
				TaskScheduler.cancelTask("transition", false);
//				if (TaskScheduler.cancelTask("transition", false)) { // If there's already an transition
//					// playing, cancel it and simulate a
//					// "natural" finish
//					Engine.currentScene.removeComponent("current-scene-out-transition");
//				}
				TaskScheduler.scheduleTask("transition",
						new Task(
								Utilities.framesToMS(Engine.currentScene.outTransition().durationInFrames(),
										Engine.currentScene.application().getTargetUpdatesPerSecond()),
								Task.NO_REPEATS, TimeUnit.MILLISECONDS) {
							@Override
							public TaskType type() {
								return TaskType.FIXED_DELAY;
							}

							@Override
							public void execute() {
								Engine.currentScene.removeComponent("current-scene-out-transition");
								Engine.currentScene = Engine.scenesMap.get(newSceneIdentifier); // Set the new scene
								if (Engine.currentScene.inTransition() != null) { // If the new scene has an in
																					// transition,
									// play it in the exact same manner as above
									// (should be refactorable but this is a
									// pain in the ass right now)
									Engine.setObject("current-scene-in-transition", Engine.currentScene.inTransition());
									Engine.currentScene.addComponent("current-scene-in-transition");
									((Transition) Engine.getObject("current-scene-in-transition")).reset();
									TaskScheduler.cancelTask("transition", false);
									TaskScheduler.scheduleTask("transition",
											new Task(
													Utilities.framesToMS(
															Engine.currentScene.inTransition().durationInFrames(),
															Engine.currentScene.application()
																	.getTargetUpdatesPerSecond()),
													Task.NO_REPEATS, TimeUnit.MILLISECONDS) {
												@Override
												public TaskType type() {
													return TaskType.FIXED_DELAY;
												}

												@Override
												public void execute() {
													// The transition should be done or basically done by now, so remove
													// it for the
													// scene & from the registry again for good measure
													Engine.currentScene.removeComponent("current-scene-in-transition");
												}
											});
								}
							}
						});
			} else { // If the previous scene doesn't have an out transition, immediately change to
						// the new scene
				Engine.currentScene = Engine.scenesMap.get(newSceneIdentifier);
				if (Engine.currentScene.inTransition() != null) { // If the new scene has an in animation, play it
					Engine.setObject("current-scene-in-transition", Engine.currentScene.inTransition());
					((Transition) Engine.getObject("current-scene-in-transition")).reset();
					Engine.currentScene.addComponent("current-scene-in-transition");
					TaskScheduler.cancelTask("transition", false);
//					if (TaskScheduler.cancelTask("transition", false)) { // If there's already an
//																						// transition
//						// playing, cancel it and simulate a
//						// "natural" finish
//						Engine.currentScene.removeComponent("current-scene-out-transition");
//					}
					TaskScheduler.scheduleTask("transition",
							new Task(
									Utilities.framesToMS(Engine.currentScene.inTransition().durationInFrames(),
											Engine.currentScene.application().getTargetUpdatesPerSecond()),
									Task.NO_REPEATS, TimeUnit.MILLISECONDS) {
								@Override
								public TaskType type() {
									return TaskType.FIXED_DELAY;
								}

								@Override
								public void execute() {
									// The transition should be done or basically done by now, so remove
									// it for the
									// scene & from the registry again for good measure
									Engine.currentScene.removeComponent("current-scene-in-transition");
								}
							});
				} else { // If the new scene doesn't have an in transition, immediately show it
					Engine.currentScene = Engine.scenesMap.get(newSceneIdentifier);
				}
			}
		}
	}

	/**
	 * Self explanatory.
	 *
	 * @param identifier an unique identifier for the object
	 * @param object     the actual application object
	 */
	public static final void setObject(final String identifier, final ApplicationObject object) {
		String name;
		if (object instanceof ApplicationObject) {
			name = "InnerApplicationObject";
		} else {
			name = object.getClass().getSimpleName();
		}
		Engine.registry.put(identifier, object);
		Logger.log(Engine.class, LogLevel.INFO, "Added to registry: '%s' <- <class : %s>", identifier,
				object.getClass().getSimpleName().equals("")
						? object.getClass().getEnclosingClass().getSimpleName() + "#" + name
						: object.getClass().getSimpleName());
	}

	/**
	 * Self explanatory.
	 *
	 * @param identifier the identifier of the object to unregister
	 */
	public static final void unsetObject(final String identifier) {
		if (Engine.registry.remove(identifier) != null) {
			Logger.log(Engine.class, LogLevel.INFO, "Removed from registry: '%s'", identifier);
		}
	}

	/**
	 * Self explanatory.
	 *
	 * @param identifier the target object's unique identifier
	 * @return the object if present or <code>null</code>
	 */
	public static final ApplicationObject getObject(final String identifier) {
		return Engine.registry.get(identifier);
	}

	/**
	 * Updates the current scene if it's not <code>null</code>. Calls
	 * {@link Scene#update()}.
	 */
	public static final void updateScene() {
		if (Engine.currentScene != null) {
			Engine.currentScene.update();
		}
	}

	/**
	 * Renders the current scene if it's not <code>null</code>. Calls
	 * {@link Scene#render()}.
	 */
	public static final void renderScene() {
		if (Engine.currentScene != null) {
			Engine.currentScene.render();
		}
	}

	/**
	 * Used internally to update the objects after sorting them by priority. Called
	 * by {@link Scene#update()}.
	 */
	protected static final void updateObjects() {
		final Set<String> identifiersToUpdate = Engine.currentScene.sceneObjectsIdentifiers();
		final List<ApplicationObject> objects = new ArrayList<ApplicationObject>();
		for (final String identifier : Engine.registry.keySet()) {
			if (identifiersToUpdate.contains(identifier)) {
				objects.add(Engine.registry.get(identifier));
			}
		}
		Collections.sort(objects);
		for (final ApplicationObject object : objects) {
			object.update();
		}
	}

	/**
	 * Used internally to render the objects after sorting them by priority. Called
	 * by {@link Scene#render()}.
	 */
	protected static final void renderObjects() {
		final Set<String> identifiersToUpdate = Engine.currentScene.sceneObjectsIdentifiers();
		final List<ApplicationObject> objects = new ArrayList<ApplicationObject>();
		for (final String identifier : Engine.registry.keySet()) {
			if (identifiersToUpdate.contains(identifier)) {
				objects.add(Engine.registry.get(identifier));
			}
		}
		Collections.sort(objects);
		for (final ApplicationObject object : objects) {
			object.render();
		}
	}

	/**
	 * Redirects the system's default output streams. Calls
	 * {@link Logger#redirectSystemOutput()}.
	 */
	public static final void initialize() {
		if (!Engine.isEngineInitialized) {
			Logger.redirectSystemOutput();
			Engine.isEngineInitialized = true;
		}
	}

	/**
	 * Self explanatory.
	 */
	public static boolean isEngineInitialized() {
		return Engine.isEngineInitialized;
	}

	/**
	 * @return the current scene
	 */
	public static final Scene currentScene() {
		return Engine.currentScene;
	}
}
