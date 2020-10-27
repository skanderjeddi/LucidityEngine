package com.skanderj.lucidityengine.exts.transitions;

import java.awt.Color;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.graphics.transitions.Transition;
import com.skanderj.lucidityengine.util.Utilities;

/**
 * A simple fade in transition.
 *
 * @author Skander Jeddi
 *
 */
public class FadeInTransition extends Transition {
	private final Color color;

	public FadeInTransition(final Application application, final int durationInFrames, final Color color) {
		super(application, durationInFrames);
		this.color = color;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void update() {
		super.update();
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void render() {
		if (!this.isDone) {
			int alpha = 0;
			alpha = (int) Utilities.map(this.timer, 0, this.durationInFrames, 255, 0, true);
			final Color newColor = new Color(this.color.getRed(), this.color.getBlue(), this.color.getGreen(), alpha);
			this.application.screen().drawRectangle(0, 0, this.application.window().getConfiguration().getWidth(),
					this.application.window().getConfiguration().getHeight(), newColor, 0, 0, true);
		}
	}

	@Override
	public String toString() {
		return String.format("FadeInTransition (params: duration=%d, color=%s)", this.durationInFrames, this.color);
	}
}