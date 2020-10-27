package com.skanderj.lucidityengine;

import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;

/**
 *
 * A class used to simplify operations on threads & make them pausable.
 *
 * @author Skander Jeddi
 *
 */
public abstract class ThreadWrapper implements Runnable {
	// The thread object
	protected final Thread thread;
	// These state variables need to be volatile to have any effect
	protected volatile boolean isRunning, isPaused;

	/**
	 * Basic constructor. Creates the thread object & initalizes the state
	 * variables.
	 *
	 * @param threadName the name of the thread
	 */
	public ThreadWrapper(final String threadName) {
		this.thread = new Thread(this, threadName);
		this.isRunning = this.isPaused = false;
	}

	/**
	 * Only starts the thread if it wasn't started already.
	 */
	protected final void start() {
		if (this.isRunning) {
			return;
		} else {
			this.isRunning = true;
			this.thread.start();
		}
	}

	/**
	 * Only stops the thread if it was running already.
	 */
	protected final void stop() {
		if (this.isRunning) {
			this.isRunning = false;
			try {
				this.thread.join(1);
			} catch (final InterruptedException interruptedException) {
				Logger.log(this.getClass(), LogLevel.DEBUG,
						"An interrupted exception has been raised while stopping a thread and can be safely ignored.");
			}
		} else {
			return;
		}
	}

	/**
	 * This is the thread's object main loop. Calls {@link ThreadWrapper#create()}
	 * before entering the main loop & {@link ThreadWrapper#destroy()} after exiting
	 * the main loop. If thread is paused, sleep for 1 millisecond to allow
	 * resuming.
	 */
	@Override
	public final void run() {
		this.create();
		while (this.isRunning) {
			if (this.isPaused) {
				try {
					Thread.sleep(1);
				} catch (final InterruptedException interruptedException) {
					Logger.log(this.getClass(), LogLevel.DEBUG,
							"An interrupted exception has been raised while a paused thread sleeps and can be safely ignored.");
				}
			} else {
				this.cycle();
			}
		}
		this.destroy();
	}

	/**
	 * Called before entering the main loop.
	 */
	protected abstract void create();

	/**
	 * Called after exiting the main loop.
	 */
	protected abstract void destroy();

	/**
	 * Called every cycle in the main loop.
	 */
	protected abstract void cycle();

	/**
	 * Self explanatory.
	 *
	 * @return whether the thread is running or not.
	 */
	public final boolean isRunning() {
		return this.isRunning;
	}

	/**
	 * Self explanatory.
	 *
	 * @return whether the thread is paused or not.
	 */
	public final boolean isPaused() {
		return this.isPaused;
	}

	/**
	 * Pauses the thread's execution.
	 */
	public final void pause() {
		this.isPaused = true;
	}

	/**
	 * Resumes the thread's execution.
	 */
	public final void resume() {
		this.isPaused = false;
	}

	/**
	 * Represents the two possible types of thread wrappers.
	 *
	 * @author Skander Jeddi
	 *
	 */
	public static enum ThreadWrapperType {
		UPDATE, RENDER;
	}
}
