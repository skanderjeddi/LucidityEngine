package com.skanderj.lucidityengine.exts;

import java.awt.Color;
import java.util.Random;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.core.ApplicationObject;
import com.skanderj.lucidityengine.core.Priority;
import com.skanderj.lucidityengine.graphics.OnScreenText;
import com.skanderj.lucidityengine.graphics.OnScreenTextProperties;
import com.skanderj.lucidityengine.util.Utilities;

/**
 * 
 * @author Skander Jeddi
 *
 */
public class FlickeringLabel extends ApplicationObject {
	private final int x0, y0, width, height;
	private final String content;
	private OnScreenTextProperties properties;
	private int xFlicker, yFlicker;
	private Color flickerColor;
	private final Random random;

	public FlickeringLabel(final Application application, final int x0, final int y0, final int width, final int height,
			final String content, final OnScreenTextProperties onScreenTextProperties) {
		super(application);
		this.x0 = x0;
		this.y0 = y0;
		this.width = width;
		this.height = height;
		this.content = content;
		this.properties = onScreenTextProperties;
		this.xFlicker = 0;
		this.yFlicker = 0;
		this.random = new Random();
	}

	@Override
	public void update() {
		if (this.random.nextDouble() > 0.85) {
			this.xFlicker += this.random.nextDouble() > 0.5 ? -5 : 5;
			this.yFlicker += this.random.nextDouble() > 0.5 ? -3 : 3;
			this.flickerColor = Utilities.buildAgainst(this.properties.color, this.random.nextInt(100) + 50);
		} else {
			this.xFlicker = 0;
			this.yFlicker = 0;
			this.flickerColor = this.properties.color;
		}
	}

	@Override
	public void render() {
		new OnScreenText(this.content, this.properties.build(this.flickerColor)).draw(this.application.screen(),
				this.x0 + this.xFlicker, this.y0 + this.yFlicker, this.width, this.height);
	}

	public OnScreenTextProperties getProperties() {
		return this.properties;
	}

	public void setProperties(final OnScreenTextProperties properties) {
		this.properties = properties;
	}

	@Override
	public Priority priority() {
		return Priority.NORMAL;
	}

	@Override
	public String toString() {
		return String.format("FlickeringText (params: x0=%d, y0=%d, content=%s, properties=%s", this.x0, this.y0,
				this.content, this.properties);
	}
}
