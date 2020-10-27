package com.skanderj.lucidityengine.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A class representing a basic mouse.
 *
 * @author Skander Jeddi
 *
 */
public class Mouse extends MouseAdapter implements InputDevice {
	// 10 buttons because some mouses have a lot of buttons, can be adjusted
	private static final int BUTTON_COUNT = 10;

	// Shortcuts to the MouseEvent.BUTTONX fields
	public static final int BUTTON_LEFT = MouseEvent.BUTTON1;
	public static final int BUTTON_MIDDLE = MouseEvent.BUTTON2;
	public static final int BUTTON_RIGHT = MouseEvent.BUTTON3;

	// Previous buttons' states
	private final boolean[] cache;
	// Current buttons' states
	private final MouseState[] buttonsStates;

	// Previous and current mouse position
	private int x, y, currentX, currentY;

	/**
	 * Initializes everything with the default values.
	 */
	public Mouse() {
		this.cache = new boolean[Mouse.BUTTON_COUNT];
		this.buttonsStates = new MouseState[Mouse.BUTTON_COUNT];
		for (int index = 0; index < Mouse.BUTTON_COUNT; index++) {
			this.buttonsStates[index] = MouseState.UP;
		}
	}

	/**
	 * Logic happens here. Detects which buttons are down just this frame and
	 * beyond, which buttons have been down before the current frame and which
	 * buttons are not pressed.
	 */
	public synchronized void update() {
		this.x = this.currentX;
		this.y = this.currentY;
		for (int index = 0; index < Mouse.BUTTON_COUNT; index++) {
			if (this.cache[index] == true) {
				if (this.buttonsStates[index] == MouseState.UP) {
					this.buttonsStates[index] = MouseState.DOWN_IN_CURRENT_FRAME;
				} else {
					this.buttonsStates[index] = MouseState.DOWN;
				}
			} else {
				this.buttonsStates[index] = MouseState.UP;
			}
		}
	}

	/**
	 * Self explanatory.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Self explanatory.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Self explanatory.
	 */
	public boolean isButtonDownInCurrentFrame(final int button) {
		return this.buttonsStates[button - 1] == MouseState.DOWN_IN_CURRENT_FRAME;
	}

	/**
	 * Self explanatory.
	 */
	public boolean isButtonDown(final int button) {
		return (this.buttonsStates[button - 1] == MouseState.DOWN_IN_CURRENT_FRAME)
				|| (this.buttonsStates[button - 1] == MouseState.DOWN);
	}

	/**
	 * MouseListener methods, logic also happens here.
	 */
	@Override
	public synchronized void mousePressed(final MouseEvent mouseEvent) {
		this.cache[mouseEvent.getButton() - 1] = true;
	}

	/**
	 * MouseListener methods, logic also happens here.
	 */
	@Override
	public synchronized void mouseReleased(final MouseEvent mouseEvent) {
		this.cache[mouseEvent.getButton() - 1] = false;
	}

	/**
	 * MouseListener methods, logic also happens here.
	 */
	@Override
	public synchronized void mouseEntered(final MouseEvent mouseEvent) {
		this.mouseMoved(mouseEvent);
	}

	/**
	 * MouseListener methods, logic also happens here.
	 */
	@Override
	public synchronized void mouseExited(final MouseEvent mouseEvent) {
		this.mouseMoved(mouseEvent);
	}

	/**
	 * MouseListener methods, logic also happens here.
	 */
	@Override
	public synchronized void mouseDragged(final MouseEvent mouseEvent) {
		this.mouseMoved(mouseEvent);
	}

	/**
	 * MouseListener methods, logic also happens here.
	 */
	@Override
	public synchronized void mouseMoved(final MouseEvent mouseEvent) {
		this.currentX = mouseEvent.getX();
		this.currentY = mouseEvent.getY();
	}

	/**
	 * The 3 states a button can be in.
	 *
	 * @author Skander
	 *
	 */
	private enum MouseState {
		UP, DOWN, DOWN_IN_CURRENT_FRAME;
	}

	/**
	 * Self explanatory.
	 */
	@Override
	public InputDeviceType type() {
		return InputDeviceType.MOUSE;
	}
}