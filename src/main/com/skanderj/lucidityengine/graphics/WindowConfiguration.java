package com.skanderj.lucidityengine.graphics;

/**
 *
 * A simple wrapper class for some {@link Window} properties.
 *
 * @author Skander Jeddi
 *
 */
public final class WindowConfiguration {
	protected final String title;
	protected final int width, height;
	protected final boolean fullscreen;
	protected final int buffers;
	protected final int deviceId;

	public WindowConfiguration(final String title, final int width, final int height, final boolean fullscreen,
			final int buffers) {
		this(title, width, height, fullscreen, buffers, 0);
	}

	public WindowConfiguration(final String title, final int width, final int height, final boolean fullscreen,
			final int buffers, final int deviceId) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		this.buffers = buffers;
		this.deviceId = deviceId;
	}

	public String getTitle() {
		return this.title;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getBuffers() {
		return this.buffers;
	}

	public boolean isFullscreen() {
		return this.fullscreen;
	}
}
