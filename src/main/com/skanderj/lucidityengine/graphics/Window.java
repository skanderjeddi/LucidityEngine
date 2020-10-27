package com.skanderj.lucidityengine.graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.input.InputDevice;
import com.skanderj.lucidityengine.input.Keyboard;
import com.skanderj.lucidityengine.input.Mouse;
import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;

/**
 *
 * This class represents the application's main frame.
 * 
 * @author Skander Jeddi
 *
 */
public final class Window {
	protected final Application application;

	protected final WindowConfiguration configuration;

	protected final Frame frame;
	protected final Canvas canvas;

	protected boolean isCloseRequested;

	protected final Screen screen;

	public Window(final Application application, final String title, final int width, final int height,
			final boolean fullscreen) {
		this(application, title, width, height, fullscreen, 2);
	}

	public Window(final Application application, final String title, final int width, final int height,
			final boolean fullscreen, final int buffers) {
		this(application, title, width, height, fullscreen, buffers, 0);
	}

	public Window(final Application application, final String title, final int width, final int height,
			final boolean fullscreen, final int buffers, final int deviceId) {
		this(application, new WindowConfiguration(title, width, height, fullscreen, buffers, deviceId));
	}

	public Window(final Application application, final WindowConfiguration configuration) {
		this.application = application;
		this.frame = new Frame(configuration.title);
		this.canvas = new Canvas();
		// Canvas
		{
			this.canvas.setMinimumSize(new Dimension(configuration.width, configuration.height));
			this.canvas.setMaximumSize(new Dimension(configuration.width, configuration.height));
			this.canvas.setPreferredSize(new Dimension(configuration.width, configuration.height));
		}
		// Frame
		{
			this.frame.add(this.canvas);
		}
		boolean canUseFullscreen = true;
		final GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		GraphicsDevice device;
		if ((configuration.deviceId < 0) || (configuration.deviceId >= devices.length)) {
			Logger.log(this.getClass(), LogLevel.SEVERE, "Invalid device ID %d, falling back to default",
					configuration.deviceId);
			device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		} else {
			device = devices[configuration.deviceId];
		}
		if (configuration.fullscreen) {
			if (device.isFullScreenSupported()) {
				this.frame.setUndecorated(true);
				device.setFullScreenWindow(this.frame);
			} else {
				Logger.log(this.getClass(), LogLevel.SEVERE,
						"Can't enter fullscreen or change display mode on this device!");
				canUseFullscreen = false;
			}
		}
		if (!configuration.fullscreen || !canUseFullscreen) {
			this.frame.pack();
			this.frame.setLocationRelativeTo(null);
			this.frame.setResizable(false);
		}
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent windowEvent) {
				Window.this.isCloseRequested = true;
			}
		});
		this.isCloseRequested = false;
		this.screen = new Screen(this.canvas, configuration);
		this.configuration = configuration;
	}

	public final Application getApplication() {
		return this.application;
	}

	public final boolean isCloseRequested() {
		return this.isCloseRequested;
	}

	public final void show() {
		this.frame.setVisible(true);
	}

	public final void hide(final boolean dispose) {
		this.frame.setVisible(false);
		if (dispose) {
			this.frame.dispose();
		}
	}

	public final void registerInputDevice(final InputDevice device) {
		switch (device.type()) {
		case KEYBOARD:
			this.canvas.addKeyListener((Keyboard) device);
			break;
		case MOUSE:
			this.canvas.addMouseListener((Mouse) device);
			this.canvas.addMouseMotionListener((Mouse) device);
			this.canvas.addMouseWheelListener((Mouse) device);
			break;
		}
		this.canvas.requestFocus();
	}

	public final Screen getScreen() {
		return this.screen;
	}

	public WindowConfiguration getConfiguration() {
		return this.configuration;
	}
}
