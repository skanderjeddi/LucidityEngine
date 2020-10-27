package com.skanderj.lucidityengine.graphics;

import java.awt.Color;
import java.awt.Font;

/**
 * Helper class - very easy to understand.
 *
 * @author Skander
 *
 */
public final class OnScreenTextProperties {
	public Font font;
	public Color color, shadeColor;
	public OnScreenTextPosition position;

	public OnScreenTextProperties(final Font font, final Color color, final Color shadeColor,
			final OnScreenTextPosition onScreenTextPosition) {
		this.font = font;
		this.color = color;
		this.shadeColor = shadeColor;
		this.position = onScreenTextPosition;
	}

	/**
	 * Self explanatory.
	 */
	public OnScreenTextProperties build(final Color color) {
		return new OnScreenTextProperties(this.font, color, this.shadeColor, this.position);
	}

	/**
	 * Self explanatory.
	 */
	public OnScreenTextProperties build(final int fontSize) {
		return new OnScreenTextProperties(this.font.deriveFont((float) fontSize), this.color, this.shadeColor,
				this.position);
	}

	public static enum OnScreenTextPosition {
		REGULAR, CENTERED, CENTERED_ABSOLUTE, CENTERED_WIDTHLESS;
	}
}