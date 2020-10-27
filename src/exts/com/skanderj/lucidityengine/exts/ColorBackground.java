package com.skanderj.lucidityengine.exts;

import java.awt.Color;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.core.Priority;
import com.skanderj.lucidityengine.graphics.components.Background;

/**
 * Solid color background. Very basic.
 *
 * @author Skander Jeddi
 *
 */
public class ColorBackground extends Background {
	private double x, y;
	private int width, height;
	private Color color;

	public ColorBackground(final Application application, final double x, final double y, final int width,
			final int height, final Color color) {
		super(application);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	/**
	 * No need for logic.
	 */
	@Override
	public synchronized void update() {
		return;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public synchronized void render() {
		this.application.screen().drawRectangle((int) this.x, (int) this.y, this.width, this.height, this.color, 0, 0,
				true);
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public boolean containsMouse(final int x, final int y) {
		return false;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public double getX() {
		return this.x;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public double getY() {
		return this.y;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void setY(final double y) {
		this.y = y;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public int getWidth() {
		return this.width;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public int getHeight() {
		return this.height;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * Self explanatory.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Self explanatory.
	 */
	public void setColor(final Color color) {
		this.color = color;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public Priority priority() {
		return Priority.HIGH;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}