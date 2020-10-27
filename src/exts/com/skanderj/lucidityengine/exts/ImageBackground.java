package com.skanderj.lucidityengine.exts;

import java.awt.image.BufferedImage;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.core.Priority;
import com.skanderj.lucidityengine.graphics.components.Background;

/**
 * Represents a basic image background.
 *
 * @author Skander Jeddi
 *
 */
public class ImageBackground extends Background {
	private double x, y;
	private int width, height;
	private BufferedImage image;

	public ImageBackground(final Application application, final double x, final double y, final int width,
			final int height, final BufferedImage image) {
		super(application);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.image = image;
	}

	/**
	 * No need for logic.
	 */
	@Override
	public void update() {
		return;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public void render() {
		this.application.screen().drawImage(this.image, (int) this.x, (int) this.y, this.width, this.height);
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
	public BufferedImage image() {
		return this.image;
	}

	/**
	 * Self explanatory.
	 */
	public void setImage(final BufferedImage image) {
		this.image = image;
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