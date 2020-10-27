package com.skanderj.lucidityengine.graphics.components;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.core.ApplicationObject;
import com.skanderj.lucidityengine.core.Priority;

/**
 * Represents a custom graphic component. Placeholder interface for batch
 * updating and rendering.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Component extends ApplicationObject {
	public Component(final Application application) {
		super(application);
	}

	@Override
	public abstract Priority priority();

	// Focus related methods
	public abstract boolean canChangeFocus();

	public abstract void grantFocus();

	public abstract void revokeFocus();

	// Self explanatory, implementation is child-component dependent
	public abstract boolean containsMouse(int x, int y);

	// Self explanatory, implementation is child-component dependent
	public abstract double getX();

	// Self explanatory, implementation is child-component dependent
	public abstract double getY();

	// Self explanatory, implementation is child-component dependent
	public abstract void setX(double x);

	// Self explanatory, implementation is child-component dependent
	public abstract void setY(double y);

	// Self explanatory, implementation is child-component dependent
	public abstract int getWidth();

	// Self explanatory, implementation is child-component dependent
	public abstract int getHeight();

	// Self explanatory, implementation is child-component dependent
	public abstract void setWidth(int width);

	// Self explanatory, implementation is child-component dependent
	public abstract void setHeight(int height);
}