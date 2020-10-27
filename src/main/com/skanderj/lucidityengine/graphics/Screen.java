package com.skanderj.lucidityengine.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 *
 * Main class used for rendering scenes' objects.
 *
 * @author Skander Jeddi
 *
 */
public final class Screen {
	// The drawing area
	private final Canvas canvas;
	// How many buffers do you need (generally 2/3)
	private final int buffers;

	// The image to be drawn on screen
	private final BufferedImage imageOnScreen;
	// Graphics object to draw on the image
	private final Graphics2D imageGraphics;
	// Graphics object to draw the image on the drawing area
	private Graphics2D canvasGraphics;

	/**
	 * Basic constructor. Initializes graphics objects & creates the image to be
	 * drawn on.
	 * 
	 * @param canvas              provided by the {@link Window} class
	 * @param windowConfiguration used to set the image's size
	 */
	protected Screen(final Canvas canvas, final WindowConfiguration windowConfiguration) {
		this.canvas = canvas;
		this.buffers = windowConfiguration.buffers;
		this.imageOnScreen = new BufferedImage(windowConfiguration.width, windowConfiguration.height,
				BufferedImage.TYPE_INT_ARGB);
		this.imageGraphics = (Graphics2D) this.imageOnScreen.getGraphics();
	}

	/**
	 * Starts a single cycle of drawing.
	 */
	public final void push() {
		final BufferStrategy canvasBufferStrategy = this.canvas.getBufferStrategy();
		if (canvasBufferStrategy == null) {
			this.canvas.createBufferStrategy(this.buffers);
			return;
		}
		this.canvasGraphics = (Graphics2D) canvasBufferStrategy.getDrawGraphics();
		this.clear();
	}

	/**
	 * Ends the current drawing cycle & pushes it on screen.
	 */
	public final void pop() {
		if (this.canvasGraphics == null) {
			return;
		}
		this.canvasGraphics.drawImage(this.imageOnScreen, 0, 0, this.imageOnScreen.getWidth(),
				this.imageOnScreen.getHeight(), null);
		this.canvasGraphics.dispose();
		this.canvas.getBufferStrategy().show();
	}

	/**
	 * Lowers the rendering quality as much as possible - no noticeable performance
	 * gain.
	 */
	public final void focusOnSpeed() {
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_SPEED);
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	}

	/**
	 * Increases the rendering quality as much as possible - no noticeable
	 * performance loss.
	 */
	public final void focusOnQuality() {
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		this.imageGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	}

	/**
	 * Used at the beginning of every cycle.
	 */
	private final void clear() {
		this.imageGraphics.setColor(Color.BLACK);
		this.imageGraphics.fillRect(0, 0, this.imageOnScreen.getWidth(), this.imageOnScreen.getHeight());
	}

	/**
	 * Set the drawing instance's color.
	 * 
	 * @param color the desired color
	 */
	public final void setColor(final Color color) {
		this.imageGraphics.setColor(color);
	}

	/**
	 * Set the drawing instance's font.
	 * 
	 * @param font the desired font
	 */
	public final void setFont(final Font font) {
		this.imageGraphics.setFont(font);
	}

	/**
	 * @return a {@link FontMetrics} instance of the current drawing instance
	 */
	public final FontMetrics getFontMetrics() {
		return this.imageGraphics.getFontMetrics();
	}

	/**
	 * @return a {@link FontMetrics} instance of the current drawing instance for
	 *         the @param font
	 */
	public final FontMetrics getFontMetrics(final Font font) {
		return this.imageGraphics.getFontMetrics(font);
	}

	/**
	 * Self explanatory.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 * @param arcWidth
	 * @param arcHeight
	 * @param fill
	 */
	public final void drawRectangle(final int x, final int y, final int width, final int height, final Color color,
			final int arcWidth, final int arcHeight, final boolean fill) {
		this.imageGraphics.setColor(color);
		if (fill) {
			this.imageGraphics.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
		} else {
			this.imageGraphics.fillRect(x, y, width, height);
		}
	}

	/**
	 * Self explanatory.
	 * 
	 * @param bufferedImage
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawImage(final BufferedImage bufferedImage, final int x, final int y, final int width,
			final int height) {
		this.imageGraphics.drawImage(bufferedImage, x, y, width, height, null);
	}

	/**
	 * Self explanatory.
	 * 
	 * @param string
	 * @param x
	 * @param y
	 */
	protected final void drawString(final String string, final int x, final int y) {
		this.imageGraphics.drawString(string, x, y);
	}

	/**
	 * Self explanatory. Uses the {@link OnScreenText} class.
	 * 
	 * @param text
	 * @param x0
	 * @param y0
	 * @param width
	 * @param height
	 * @param args
	 */
	public final void drawText(final OnScreenText text, final int x0, final int y0, final int width, final int height,
			final Object... args) {
		text.draw(this, x0, y0, width, height, args);
	}

	/**
	 * @return the graphics instance if you need it for direct rendering. Not
	 *         recommended but still usable
	 */
	public Graphics2D getGraphics() {
		return this.imageGraphics;
	}
}
