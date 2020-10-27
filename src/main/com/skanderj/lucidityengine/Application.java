package com.skanderj.lucidityengine;

import java.lang.reflect.InvocationTargetException;

import com.skanderj.lucidityengine.ThreadWrapper.ThreadWrapperType;
import com.skanderj.lucidityengine.core.Engine;
import com.skanderj.lucidityengine.graphics.Screen;
import com.skanderj.lucidityengine.graphics.Window;
import com.skanderj.lucidityengine.graphics.WindowConfiguration;
import com.skanderj.lucidityengine.input.Keyboard;
import com.skanderj.lucidityengine.input.Mouse;
import com.skanderj.lucidityengine.input.binds.Binds;
import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;
import com.skanderj.lucidityengine.resources.Fonts;
import com.skanderj.lucidityengine.resources.Images;
import com.skanderj.lucidityengine.resources.audio.Audios;

/**
 *
 * Main class of any application built with the Lucidity Engine.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Application {
	// Updating & rendering threads
	private final ThreadWrapper updatingThreadWrapper, renderingThreadWrapper;
	// How many updates per second/frames per second
	private final double targetUpdatesPerSecond, targetFramesPerSecond;

	// Display area wrapper
	protected final Window window;

	// Input devices
	protected Keyboard keyboard;
	protected Mouse mouse;

	/**
	 * 
	 * @param identifier             a unique identifier used mostly for thread
	 *                               naming
	 * @param targetUpdatesPerSecond how many updates per second are desired
	 * @param targetFramesPerSecond  how many frames per second are desired
	 * @param localizedKeyboardClass localized keyboards allow to access any key on
	 *                               any type of keyboard provided a class for it
	 *                               exists
	 */
	public Application(final String identifier, final double targetUpdatesPerSecond, final double targetFramesPerSecond,
			final Class<? extends Keyboard> localizedKeyboardClass) {
		// Initialize the engine
		Engine.initialize();
		// Create the updating thread first
		this.updatingThreadWrapper = new ThreadWrapper(identifier + "-UTW") {
			@Override
			protected void destroy() {
				Application.this.destroyThread(ThreadWrapperType.UPDATE);
			}

			@Override
			protected void cycle() {
				long startTime = System.nanoTime();
				final double nanosecondsPerTick = 1000000000.0 / targetUpdatesPerSecond;
				int updates = 0;
				long resetTime = System.currentTimeMillis();
				double delta = 0.0D;
				while (this.isRunning) {
					final long endTime = System.nanoTime();
					delta += (endTime - startTime) / nanosecondsPerTick;
					startTime = endTime;
					while (delta >= 1) {
						updates++;
						Application.this.update(delta);
						Application.this.updateInputDevices();
						delta -= 1;
					}
					if ((System.currentTimeMillis() - resetTime) >= 1000) {
						resetTime += 1000;
						Logger.log(this.getClass(), LogLevel.DEVELOPMENT, "Updates for the last second: %d", updates);
						updates = 0;
					}
				}
			}

			@Override
			protected void create() {
				Application.this.createThread(ThreadWrapperType.UPDATE);
			}
		};
		// Then the rendering thread
		this.renderingThreadWrapper = new ThreadWrapper(identifier + "-RTW") {
			@Override
			protected void destroy() {
				Application.this.destroyThread(ThreadWrapperType.RENDER);
			}

			@Override
			protected void cycle() {
				long startTime = System.nanoTime();
				final double nanosecondsPerTick = 1000000000D / targetFramesPerSecond;
				int frames = 0;
				long resetTime = System.currentTimeMillis();
				double delta = 0.0D;
				while (this.isRunning) {
					final long endTime = System.nanoTime();
					delta += (endTime - startTime) / nanosecondsPerTick;
					startTime = endTime;
					boolean shouldRender = false;
					while (delta >= 1) {
						delta -= 1;
						shouldRender = true;
					}
					if (shouldRender) {
						frames++;
						Application.this.render();
					}
					if ((System.currentTimeMillis() - resetTime) >= 1000) {
						resetTime += 1000;
						Logger.log(this.getClass(), LogLevel.DEVELOPMENT, "Frames for the last second: %d", frames);
						frames = 0;
					}
				}
			}

			@Override
			protected void create() {
				Application.this.createThread(ThreadWrapperType.RENDER);
			}
		};
		// Set object fields
		this.targetUpdatesPerSecond = targetUpdatesPerSecond;
		this.targetFramesPerSecond = targetFramesPerSecond;
		this.window = new Window(this, this.windowConfiguration());
		try {
			this.keyboard = localizedKeyboardClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException exception) {
			// If the provided class doesn't exist or isn't a keyboard subclass, we can't
			// run the application
			Logger.log(this.getClass(), LogLevel.FATAL, "Wrong class type for keyboard instantiation: %s",
					exception.getMessage());
		}
		this.mouse = new Mouse();
		// Attach keyboard & mouse to the window
		this.attachInputDevices();
		// Load all the resources here
		this.loadResources();
		// Register your application objects for future use
		this.registerApplicationObjects();
		// Register your scenes here
		this.registerScenes();
		// Register your binds here
		this.registerBinds();
		// Go to the first scene of the application
		Engine.switchToScene(this.firstSceneIdentifier());
	}

	/**
	 * Load all your resources here. See {@link Audios}, {@link Images} &
	 * {@link Fonts}.
	 */
	protected abstract void loadResources();

	/**
	 * Register your application objects here through the registry. See
	 * {@link Engine#setObject(String, com.skanderj.lucidityengine.core.ApplicationObject)}.
	 */
	protected abstract void registerApplicationObjects();

	/**
	 * Register your scenes here through the registry. See
	 * {@link Engine#registerScene(String, com.skanderj.lucidityengine.core.Scene)}.
	 */
	protected abstract void registerScenes();

	/**
	 * Register your binds here through the
	 * {@link Binds#registerBind(String, Integer[], com.skanderj.lucidityengine.input.Keyboard.KeyState[], com.skanderj.lucidityengine.core.Action)}
	 * class.
	 */
	protected abstract void registerBinds();

	/**
	 * Used internally.
	 */
	private final void attachInputDevices() {
		this.window.registerInputDevice(this.keyboard);
		this.window.registerInputDevice(this.mouse);
	}

	/**
	 * Used internally to refresh input devices.
	 */
	private final void updateInputDevices() {
		this.keyboard.update();
		this.mouse.update();
	}

	/**
	 * Used to initalize the application window's properties.
	 * 
	 * @return a new instance of {@link WindowConfiguration} with the needed params
	 */
	protected abstract WindowConfiguration windowConfiguration();

	/**
	 * Used internally to start the application.
	 */
	protected final void start() {
		this.updatingThreadWrapper.start();
		this.renderingThreadWrapper.start();
	}

	/**
	 * Used internally to stop the application.
	 */
	protected final void stop() {
		this.renderingThreadWrapper.stop();
		this.updatingThreadWrapper.stop();
	}

	/**
	 * Invoked upon creation of either the updating or the rendering thread.
	 * 
	 * @param type the thread's type: UPDATE or RENDER
	 */
	protected abstract void createThread(final ThreadWrapperType type);

	/**
	 * Invoked upon destruction of either the updating or the rendering thread.
	 * 
	 * @param type the thread's type: UPDATE or RENDER
	 */
	protected abstract void destroyThread(final ThreadWrapperType type);

	/**
	 * Used internally to update the application's logic.
	 * 
	 * @param delta the time elapsed between now and the last upate
	 */
	protected void update(final double delta) {
		Engine.updateScene();
		if (this.window.isCloseRequested()) {
			this.stop();
		}
	}

	/**
	 * Used internally to render the application's components.
	 */
	protected void render() {
		final Screen screen = this.window.getScreen();
		screen.push();
		Engine.renderScene();
		screen.pop();
	}

	/**
	 * Sets the first scene of the application.
	 * 
	 * @return the identifier of the scene
	 */
	public abstract String firstSceneIdentifier();

	/**
	 * In case you need to get the updating thread wrapper for some reason. USAGE
	 * NOT RECOMMANDED.
	 * 
	 * @return the updating thread wrapper
	 */
	protected final ThreadWrapper getUpdatingThreadWrapper() {
		return this.updatingThreadWrapper;
	}

	/**
	 * In case you need to get the rendering thread wrapper for some reason. USAGE
	 * NOT RECOMMANDED.
	 * 
	 * @return the rendering thread wrapper
	 */
	protected final ThreadWrapper getRenderingThreadWrapper() {
		return this.renderingThreadWrapper;
	}

	/**
	 * Self explanatory.
	 * 
	 * @return the target updates in a single second
	 */
	public final double getTargetUpdatesPerSecond() {
		return this.targetUpdatesPerSecond;
	}

	/**
	 * Self explanatory.
	 * 
	 * @return the target frames in a single second
	 */
	public final double getTargetFramesPerSecond() {
		return this.targetFramesPerSecond;
	}

	/**
	 * Self explanatory.
	 * 
	 * @return this application's window
	 */
	public final Window window() {
		return this.window;
	}

	/**
	 * Self explanatory.
	 * 
	 * @return this application window's screen, which does all the rendering
	 */
	public final Screen screen() {
		return this.window.getScreen();
	}

	/**
	 * Self explanatory.
	 * 
	 * @return this application's keyboard instance
	 */
	public final Keyboard keyboard() {
		return this.keyboard;
	}

	/**
	 * Self explanatory.
	 * 
	 * @return this application's mouse instance
	 */
	public final Mouse mouse() {
		return this.mouse;
	}
}
