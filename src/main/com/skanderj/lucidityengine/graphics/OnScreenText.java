package com.skanderj.lucidityengine.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

import com.skanderj.lucidityengine.graphics.OnScreenTextProperties.OnScreenTextPosition;

/**
 * Helper class - very easy to understand.
 *
 * @author Skander
 *
 */
public final class OnScreenText {
	public String content;
	public Color color, shadeColor;
	public Font font;
	public OnScreenTextPosition position;

	public OnScreenText(final String content, final OnScreenTextProperties properties) {
		this.content = content;
		this.color = properties.color;
		this.shadeColor = properties.shadeColor;
		this.font = properties.font;
		this.position = properties.position;
	}

	/**
	 * Self explanatory.
	 */
	public final int getWidth(final Screen screen, final Object... args) {
		final FontMetrics metrics = screen.getFontMetrics(this.font);
		return metrics.stringWidth(String.format(this.content, args));
	}

	/**
	 * Self explanatory.
	 */
	public int getHeight(final Screen screen) {
		final FontMetrics metrics = screen.getFontMetrics(this.font);
		return metrics.getHeight();
	}

	/**
	 * Self explanatory.
	 */
	public int getAugmentedHeight(final Screen screen) {
		final FontMetrics metrics = screen.getFontMetrics(this.font);
		return metrics.getHeight() + metrics.getAscent();
	}

	/**
	 * Self explanatory.
	 */
	public boolean isEmpty() {
		return this.content.isEmpty();
	}

	/**
	 * Self explanatory.
	 */
	public final void draw(final Screen screen, final int x0, final int y0, final int width, final int height,
			final Object... args) {
		switch (this.position) {
		case REGULAR:
			OnScreenText.drawString(screen, x0, y0, this, args);
			break;
		case CENTERED:
			OnScreenText.drawCenteredString(screen, x0, y0, width, height, this, args);
			break;
		case CENTERED_ABSOLUTE:
			OnScreenText.drawCenteredStringAbsolute(screen, x0, y0, height, this, args);
			break;
		case CENTERED_WIDTHLESS:
			OnScreenText.drawCenteredStringWidthless(screen, x0, y0, height, this, args);
			break;
		}
	}

	/**
	 * Self explanatory.
	 */
	private static final void drawString(final Screen screen, final int x0, final int y0, final OnScreenText string,
			final Object... args) {
		final OnScreenText formatted = new OnScreenText(
				args.length > 0 ? String.format(string.content, args) : string.content,
				new OnScreenTextProperties(string.font, string.color, string.shadeColor, string.position));
		if (formatted.shadeColor == null) {
			screen.setColor(formatted.color);
			screen.setFont(formatted.font);
			screen.drawString(formatted.content, x0, y0);
		} else {
			screen.setColor(formatted.shadeColor);
			screen.drawString(formatted.content, x0 + 1, y0 + 1);
			screen.setColor(formatted.color);
			screen.drawString(formatted.content, x0 - 1, y0 - 1);
		}
	}

	/**
	 * Self explanatory.
	 */
	private static final void drawCenteredString(final Screen screen, final int x0, final int y0, final int width,
			final int height, final OnScreenText string, final Object... args) {
		final OnScreenText formatted = new OnScreenText(
				args.length > 0 ? String.format(string.content, args) : string.content,
				new OnScreenTextProperties(string.font, string.color, string.shadeColor, string.position));
		screen.setFont(string.font);
		final FontMetrics fontMetrics = screen.getFontMetrics();
		final Rectangle2D rectangle2d = fontMetrics.getStringBounds(formatted.content, screen.getGraphics());
		final int x = (width - (int) rectangle2d.getWidth()) / 2;
		final int y = ((height - (int) rectangle2d.getHeight()) / 2) + fontMetrics.getAscent();
		if (formatted.shadeColor == null) {
			screen.setColor(string.color);
			screen.drawString(formatted.content, x0 + x, y0 + y);
		} else {
			screen.setColor(formatted.shadeColor);
			screen.drawString(formatted.content, x0 + x + 1, y0 + y + 1);
			screen.setColor(formatted.color);
			screen.drawString(formatted.content, (x0 + x) - 1, (y0 + y) - 1);
		}
	}

	/**
	 * Self explanatory.
	 */
	private static final int drawCenteredStringWidthless(final Screen screen, final int x0, final int y0,
			final int height, final OnScreenText string, final Object... args) {
		final OnScreenText formatted = new OnScreenText(
				args.length > 0 ? String.format(string.content, args) : string.content,
				new OnScreenTextProperties(string.font, string.color, string.shadeColor, string.position));
		screen.setFont(formatted.font);
		screen.setColor(formatted.color);
		final FontMetrics fontMetrics = screen.getFontMetrics();
		final Rectangle2D rectangle2d = fontMetrics.getStringBounds(formatted.content, screen.getGraphics());
		final int y = ((height - (int) rectangle2d.getHeight()) / 2) + fontMetrics.getAscent();
		if (formatted.shadeColor == null) {
			screen.drawString(formatted.content, x0, y0 + y);
		} else {
			screen.setColor(formatted.shadeColor);
			screen.drawString(formatted.content, x0 + 1, y0 + y + 1);
			screen.setColor(formatted.color);
			screen.drawString(formatted.content, x0 - 1, (y0 + y) - 1);
		}
		return y0 + y;
	}

	/**
	 * Self explanatory.
	 */
	private static final int drawCenteredStringAbsolute(final Screen screen, final int x0, final int y0,
			final int height, final OnScreenText string, final Object... args) {
		final OnScreenText formatted = new OnScreenText(
				args.length > 0 ? String.format(string.content, args) : string.content,
				new OnScreenTextProperties(string.font, string.color, string.shadeColor, string.position));
		screen.setFont(formatted.font);
		screen.setColor(formatted.color);
		final FontMetrics metrics = screen.getFontMetrics();
		int y = (height - metrics.getHeight()) / 2;
		screen.setColor(Color.BLACK);
		y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
		if (formatted.shadeColor == null) {
			screen.drawString(formatted.content, x0, y0 + y);
		} else {
			screen.setColor(formatted.shadeColor);
			screen.drawString(formatted.content, x0 + 1, y0 + y + 1);
			screen.setColor(formatted.color);
			screen.drawString(formatted.content, (x0) - 1, (y0 + y) - 1);
		}
		return y0;
	}
}