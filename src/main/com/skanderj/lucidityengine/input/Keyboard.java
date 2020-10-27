package com.skanderj.lucidityengine.input;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.skanderj.lucidityengine.input.localized.AZERTYKeyboard;

/**
 * A class representing an AZERTY (!!!) keyboard, although it can be used for
 * any type of keyboards if you exclude the
 * {@link Keyboard#getKeyRepresentation(int, boolean, boolean, boolean)}
 * function.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Keyboard extends KeyAdapter implements InputDevice {
	// AZERTY keyboard constant
	public static final Class<AZERTYKeyboard> AZERTY = AZERTYKeyboard.class;

	// Trying to hit absolutely every key - otherwise things crash
	private static final int KEY_COUNT = 65536;

	// Shortcuts to the KeyEvent.VK_XXX fields
	public static final int KEY_ENTER = KeyEvent.VK_ENTER;
	public static final int KEY_BACK_SPACE = KeyEvent.VK_BACK_SPACE;
	public static final int KEY_TAB = KeyEvent.VK_TAB;
	public static final int KEY_CANCEL = KeyEvent.VK_CANCEL;
	public static final int KEY_CLEAR = KeyEvent.VK_CLEAR;
	public static final int KEY_SHIFT = KeyEvent.VK_SHIFT;
	public static final int KEY_CONTROL = KeyEvent.VK_CONTROL;
	public static final int KEY_ALT = KeyEvent.VK_ALT;
	public static final int KEY_PAUSE = KeyEvent.VK_PAUSE;
	public static final int KEY_CAPS_LOCK = KeyEvent.VK_CAPS_LOCK;
	public static final int KEY_ESCAPE = KeyEvent.VK_ESCAPE;
	public static final int KEY_SPACE = KeyEvent.VK_SPACE;
	public static final int KEY_PAGE_UP = KeyEvent.VK_PAGE_UP;
	public static final int KEY_PAGE_DOWN = KeyEvent.VK_PAGE_DOWN;
	public static final int KEY_END = KeyEvent.VK_END;
	public static final int KEY_HOME = KeyEvent.VK_HOME;
	public static final int KEY_LEFT = KeyEvent.VK_LEFT;
	public static final int KEY_UP = KeyEvent.VK_UP;
	public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
	public static final int KEY_DOWN = KeyEvent.VK_DOWN;
	public static final int KEY_COMMA = KeyEvent.VK_COMMA;
	public static final int KEY_MINUS = KeyEvent.VK_MINUS;
	public static final int KEY_PERIOD = KeyEvent.VK_PERIOD;
	public static final int KEY_SLASH = KeyEvent.VK_SLASH;
	public static final int KEY_0 = KeyEvent.VK_0;
	public static final int KEY_1 = KeyEvent.VK_1;
	public static final int KEY_2 = KeyEvent.VK_2;
	public static final int KEY_3 = KeyEvent.VK_3;
	public static final int KEY_4 = KeyEvent.VK_4;
	public static final int KEY_5 = KeyEvent.VK_5;
	public static final int KEY_6 = KeyEvent.VK_6;
	public static final int KEY_7 = KeyEvent.VK_7;
	public static final int KEY_8 = KeyEvent.VK_8;
	public static final int KEY_9 = KeyEvent.VK_9;
	public static final int KEY_SEMICOLON = KeyEvent.VK_SEMICOLON;
	public static final int KEY_EQUALS = KeyEvent.VK_EQUALS;
	public static final int KEY_A = KeyEvent.VK_A;
	public static final int KEY_B = KeyEvent.VK_B;
	public static final int KEY_C = KeyEvent.VK_C;
	public static final int KEY_D = KeyEvent.VK_D;
	public static final int KEY_E = KeyEvent.VK_E;
	public static final int KEY_F = KeyEvent.VK_F;
	public static final int KEY_G = KeyEvent.VK_G;
	public static final int KEY_H = KeyEvent.VK_H;
	public static final int KEY_I = KeyEvent.VK_I;
	public static final int KEY_J = KeyEvent.VK_J;
	public static final int KEY_K = KeyEvent.VK_K;
	public static final int KEY_L = KeyEvent.VK_L;
	public static final int KEY_M = KeyEvent.VK_M;
	public static final int KEY_N = KeyEvent.VK_N;
	public static final int KEY_O = KeyEvent.VK_O;
	public static final int KEY_P = KeyEvent.VK_P;
	public static final int KEY_Q = KeyEvent.VK_Q;
	public static final int KEY_R = KeyEvent.VK_R;
	public static final int KEY_S = KeyEvent.VK_S;
	public static final int KEY_T = KeyEvent.VK_T;
	public static final int KEY_U = KeyEvent.VK_U;
	public static final int KEY_V = KeyEvent.VK_V;
	public static final int KEY_W = KeyEvent.VK_W;
	public static final int KEY_X = KeyEvent.VK_X;
	public static final int KEY_Y = KeyEvent.VK_Y;
	public static final int KEY_Z = KeyEvent.VK_Z;
	public static final int KEY_OPEN_BRACKET = KeyEvent.VK_OPEN_BRACKET;
	public static final int KEY_BACK_SLASH = KeyEvent.VK_BACK_SLASH;
	public static final int KEY_CLOSE_BRACKET = KeyEvent.VK_CLOSE_BRACKET;
	public static final int KEY_NUMPAD0 = KeyEvent.VK_NUMPAD0;
	public static final int KEY_NUMPAD1 = KeyEvent.VK_NUMPAD1;
	public static final int KEY_NUMPAD2 = KeyEvent.VK_NUMPAD2;
	public static final int KEY_NUMPAD3 = KeyEvent.VK_NUMPAD3;
	public static final int KEY_NUMPAD4 = KeyEvent.VK_NUMPAD4;
	public static final int KEY_NUMPAD5 = KeyEvent.VK_NUMPAD5;
	public static final int KEY_NUMPAD6 = KeyEvent.VK_NUMPAD6;
	public static final int KEY_NUMPAD7 = KeyEvent.VK_NUMPAD7;
	public static final int KEY_NUMPAD8 = KeyEvent.VK_NUMPAD8;
	public static final int KEY_NUMPAD9 = KeyEvent.VK_NUMPAD9;
	public static final int KEY_MULTIPLY = KeyEvent.VK_MULTIPLY;
	public static final int KEY_ADD = KeyEvent.VK_ADD;
	public static final int KEY_SEPARATER = KeyEvent.VK_SEPARATER;
	public static final int KEY_SEPARATOR = KeyEvent.VK_SEPARATOR;
	public static final int KEY_SUBTRACT = KeyEvent.VK_SUBTRACT;
	public static final int KEY_DECIMAL = KeyEvent.VK_DECIMAL;
	public static final int KEY_DIVIDE = KeyEvent.VK_DIVIDE;
	public static final int KEY_DELETE = KeyEvent.VK_DELETE;
	public static final int KEY_NUM_LOCK = KeyEvent.VK_NUM_LOCK;
	public static final int KEY_SCROLL_LOCK = KeyEvent.VK_SCROLL_LOCK;
	public static final int KEY_F1 = KeyEvent.VK_F1;
	public static final int KEY_F2 = KeyEvent.VK_F2;
	public static final int KEY_F3 = KeyEvent.VK_F3;
	public static final int KEY_F4 = KeyEvent.VK_F4;
	public static final int KEY_F5 = KeyEvent.VK_F5;
	public static final int KEY_F6 = KeyEvent.VK_F6;
	public static final int KEY_F7 = KeyEvent.VK_F7;
	public static final int KEY_F8 = KeyEvent.VK_F8;
	public static final int KEY_F9 = KeyEvent.VK_F9;
	public static final int KEY_F10 = KeyEvent.VK_F10;
	public static final int KEY_F11 = KeyEvent.VK_F11;
	public static final int KEY_F12 = KeyEvent.VK_F12;
	public static final int KEY_F13 = KeyEvent.VK_F13;
	public static final int KEY_F14 = KeyEvent.VK_F14;
	public static final int KEY_F15 = KeyEvent.VK_F15;
	public static final int KEY_F16 = KeyEvent.VK_F16;
	public static final int KEY_F17 = KeyEvent.VK_F17;
	public static final int KEY_F18 = KeyEvent.VK_F18;
	public static final int KEY_F19 = KeyEvent.VK_F19;
	public static final int KEY_F20 = KeyEvent.VK_F20;
	public static final int KEY_F21 = KeyEvent.VK_F21;
	public static final int KEY_F22 = KeyEvent.VK_F22;
	public static final int KEY_F23 = KeyEvent.VK_F23;
	public static final int KEY_F24 = KeyEvent.VK_F24;
	public static final int KEY_PRINTSCREEN = KeyEvent.VK_PRINTSCREEN;
	public static final int KEY_INSERT = KeyEvent.VK_INSERT;
	public static final int KEY_HELP = KeyEvent.VK_HELP;
	public static final int KEY_META = KeyEvent.VK_META;
	public static final int KEY_BACK_QUOTE = KeyEvent.VK_BACK_QUOTE;
	public static final int KEY_QUOTE = KeyEvent.VK_QUOTE;
	public static final int KEY_KP_UP = KeyEvent.VK_KP_UP;
	public static final int KEY_KP_DOWN = KeyEvent.VK_KP_DOWN;
	public static final int KEY_KP_LEFT = KeyEvent.VK_KP_LEFT;
	public static final int KEY_KP_RIGHT = KeyEvent.VK_KP_RIGHT;
	public static final int KEY_DEAD_GRAVE = KeyEvent.VK_DEAD_GRAVE;
	public static final int KEY_DEAD_ACUTE = KeyEvent.VK_DEAD_ACUTE;
	public static final int KEY_DEAD_CIRCUMFLEX = KeyEvent.VK_DEAD_CIRCUMFLEX;
	public static final int KEY_DEAD_TILDE = KeyEvent.VK_DEAD_TILDE;
	public static final int KEY_DEAD_MACRON = KeyEvent.VK_DEAD_MACRON;
	public static final int KEY_DEAD_BREVE = KeyEvent.VK_DEAD_BREVE;
	public static final int KEY_DEAD_ABOVEDOT = KeyEvent.VK_DEAD_ABOVEDOT;
	public static final int KEY_DEAD_DIAERESIS = KeyEvent.VK_DEAD_DIAERESIS;
	public static final int KEY_DEAD_ABOVERING = KeyEvent.VK_DEAD_ABOVERING;
	public static final int KEY_DEAD_DOUBLEACUTE = KeyEvent.VK_DEAD_DOUBLEACUTE;
	public static final int KEY_DEAD_CARON = KeyEvent.VK_DEAD_CARON;
	public static final int KEY_DEAD_CEDILLA = KeyEvent.VK_DEAD_CEDILLA;
	public static final int KEY_DEAD_OGONEK = KeyEvent.VK_DEAD_OGONEK;
	public static final int KEY_DEAD_IOTA = KeyEvent.VK_DEAD_IOTA;
	public static final int KEY_DEAD_VOICED_SOUND = KeyEvent.VK_DEAD_VOICED_SOUND;
	public static final int KEY_DEAD_SEMIVOICED_SOUND = KeyEvent.VK_DEAD_SEMIVOICED_SOUND;
	public static final int KEY_AMPERSAND = KeyEvent.VK_AMPERSAND;
	public static final int KEY_ASTERISK = KeyEvent.VK_ASTERISK;
	public static final int KEY_QUOTEDBL = KeyEvent.VK_QUOTEDBL;
	public static final int KEY_LESS = KeyEvent.VK_LESS;
	public static final int KEY_GREATER = KeyEvent.VK_GREATER;
	public static final int KEY_BRACELEFT = KeyEvent.VK_BRACELEFT;
	public static final int KEY_BRACERIGHT = KeyEvent.VK_BRACERIGHT;
	public static final int KEY_AT = KeyEvent.VK_AT;
	public static final int KEY_COLON = KeyEvent.VK_COLON;
	public static final int KEY_CIRCUMFLEX = KeyEvent.VK_CIRCUMFLEX;
	public static final int KEY_DOLLAR = KeyEvent.VK_DOLLAR;
	public static final int KEY_EURO_SIGN = KeyEvent.VK_EURO_SIGN;
	public static final int KEY_EXCLAMATION_MARK = KeyEvent.VK_EXCLAMATION_MARK;
	public static final int KEY_INVERTED_EXCLAMATION_MARK = KeyEvent.VK_INVERTED_EXCLAMATION_MARK;
	public static final int KEY_LEFT_PARENTHESIS = KeyEvent.VK_LEFT_PARENTHESIS;
	public static final int KEY_NUMBER_SIGN = KeyEvent.VK_NUMBER_SIGN;
	public static final int KEY_PLUS = KeyEvent.VK_PLUS;
	public static final int KEY_RIGHT_PARENTHESIS = KeyEvent.VK_RIGHT_PARENTHESIS;
	public static final int KEY_UNDERSCORE = KeyEvent.VK_UNDERSCORE;
	public static final int KEY_WINDOWS = KeyEvent.VK_WINDOWS;
	public static final int KEY_CONTEXT_MENU = KeyEvent.VK_CONTEXT_MENU;
	public static final int KEY_FINAL = KeyEvent.VK_FINAL;
	public static final int KEY_CONVERT = KeyEvent.VK_CONVERT;
	public static final int KEY_NONCONVERT = KeyEvent.VK_NONCONVERT;
	public static final int KEY_ACCEPT = KeyEvent.VK_ACCEPT;
	public static final int KEY_MODECHANGE = KeyEvent.VK_MODECHANGE;
	public static final int KEY_KANA = KeyEvent.VK_KANA;
	public static final int KEY_KANJI = KeyEvent.VK_KANJI;
	public static final int KEY_ALPHANUMERIC = KeyEvent.VK_ALPHANUMERIC;
	public static final int KEY_KATAKANA = KeyEvent.VK_KATAKANA;
	public static final int KEY_HIRAGANA = KeyEvent.VK_HIRAGANA;
	public static final int KEY_FULL_WIDTH = KeyEvent.VK_FULL_WIDTH;
	public static final int KEY_HALF_WIDTH = KeyEvent.VK_HALF_WIDTH;
	public static final int KEY_ROMAN_CHARACTERS = KeyEvent.VK_ROMAN_CHARACTERS;
	public static final int KEY_ALL_CANDIDATES = KeyEvent.VK_ALL_CANDIDATES;
	public static final int KEY_PREVIOUS_CANDIDATE = KeyEvent.VK_PREVIOUS_CANDIDATE;
	public static final int KEY_CODE_INPUT = KeyEvent.VK_CODE_INPUT;
	public static final int KEY_JAPANESE_KATAKANA = KeyEvent.VK_JAPANESE_KATAKANA;
	public static final int KEY_JAPANESE_HIRAGANA = KeyEvent.VK_JAPANESE_HIRAGANA;
	public static final int KEY_JAPANESE_ROMAN = KeyEvent.VK_JAPANESE_ROMAN;
	public static final int KEY_KANA_LOCK = KeyEvent.VK_KANA_LOCK;
	public static final int KEY_INPUT_METHOD_ON_OFF = KeyEvent.VK_INPUT_METHOD_ON_OFF;
	public static final int KEY_CUT = KeyEvent.VK_CUT;
	public static final int KEY_COPY = KeyEvent.VK_COPY;
	public static final int KEY_PASTE = KeyEvent.VK_PASTE;
	public static final int KEY_UNDO = KeyEvent.VK_UNDO;
	public static final int KEY_AGAIN = KeyEvent.VK_AGAIN;
	public static final int KEY_FIND = KeyEvent.VK_FIND;
	public static final int KEY_PROPS = KeyEvent.VK_PROPS;
	public static final int KEY_STOP = KeyEvent.VK_STOP;
	public static final int KEY_COMPOSE = KeyEvent.VK_COMPOSE;
	public static final int KEY_ALT_GRAPH = KeyEvent.VK_ALT_GRAPH;
	public static final int KEY_BEGIN = KeyEvent.VK_BEGIN;
	public static final int KEY_UNDEFINED = KeyEvent.VK_UNDEFINED;

	// Previous keys' states
	private final boolean[] cache;
	// Current keys' states
	private final KeyState[] keysStates;

	/**
	 * Initializes everything with the default values.
	 */
	public Keyboard() {
		this.cache = new boolean[Keyboard.KEY_COUNT];
		this.keysStates = new KeyState[Keyboard.KEY_COUNT];
		for (int index = 0; index < Keyboard.KEY_COUNT; index++) {
			this.keysStates[index] = KeyState.UP;
		}
	}

	/**
	 * Logic happens here. Detects which keys are down just this frame and beyond,
	 * which keys have been down before the current frame and which keys are not
	 * pressed.
	 */
	public synchronized void update() {
		for (int index = 0; index < Keyboard.KEY_COUNT; index++) {
			if (this.cache[index] == true) {
				if (this.keysStates[index] == KeyState.UP) {
					this.keysStates[index] = KeyState.DOWN_IN_CURRENT_FRAME;
				} else {
					this.keysStates[index] = KeyState.DOWN;
				}
			} else {
				this.keysStates[index] = KeyState.UP;
			}
		}
	}

	/**
	 * Self explanatory.
	 */
	public boolean isKeyDown(final int keyCode) {
		return (this.keysStates[keyCode] == KeyState.DOWN_IN_CURRENT_FRAME)
				|| (this.keysStates[keyCode] == KeyState.DOWN);
	}

	/**
	 * Self explanatory.
	 */
	public boolean isKeyDownInCurrentFrame(final int keyCode) {
		return this.keysStates[keyCode] == KeyState.DOWN_IN_CURRENT_FRAME;
	}

	/**
	 * KeyListener methods, logic also happens here.
	 */
	@Override
	public synchronized void keyPressed(final KeyEvent keyEvent) {
		final int keyCode = keyEvent.getKeyCode();
		if ((keyCode >= 0) && (keyCode < Keyboard.KEY_COUNT)) {
			this.cache[keyCode] = true;
		}
	}

	/**
	 * KeyListener methods, logic also happens here.
	 */
	@Override
	public synchronized void keyReleased(final KeyEvent keyEvent) {
		final int keyCode = keyEvent.getKeyCode();
		if ((keyCode >= 0) && (keyCode < Keyboard.KEY_COUNT)) {
			this.cache[keyCode] = false;
		}
	}

	/**
	 * Self explanatory, used for keystates.
	 */
	public synchronized boolean isShiftDown() {
		return this.isKeyDown(Keyboard.KEY_SHIFT);
	}

	/**
	 * PEOPLE SAY THIS DOESN'T WORK, it obviously does.
	 */
	public synchronized boolean isCapsLocked() {
		return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
	}

	public abstract boolean isAltGrDown();

	/**
	 * The 3 states a key can be in.
	 *
	 * @author Skander
	 *
	 */
	public enum KeyState {
		UP, DOWN, DOWN_IN_CURRENT_FRAME;
	}

	/**
	 * Self explanatory.
	 */
	public synchronized final Integer[] getKeysByState(final KeyState state) {
		final List<Integer> keys = new ArrayList<>();
		switch (state) {
		case DOWN:
			for (int keycode = 0; keycode < Keyboard.KEY_COUNT; keycode += 1) {
				if (this.isKeyDown(keycode)) {
					keys.add(keycode);
				}
			}
			return keys.toArray(new Integer[keys.size()]);
		case DOWN_IN_CURRENT_FRAME:
			for (int keycode = 0; keycode < Keyboard.KEY_COUNT; keycode += 1) {
				if (this.isKeyDownInCurrentFrame(keycode)) {
					keys.add(keycode);
				}
			}
			return keys.toArray(new Integer[keys.size()]);
		case UP:
			for (int keycode = 0; keycode < Keyboard.KEY_COUNT; keycode += 1) {
				if (!this.isKeyDown(keycode) && !this.isKeyDownInCurrentFrame(keycode)) {
					keys.add(keycode);
				}
			}
			return keys.toArray(new Integer[keys.size()]);
		}
		return new Integer[0];
	}

	/**
	 * Quick method to get the key name out of a keycode. Doesn't take modifiers
	 * into account. You're better using
	 * {@link Keyboard#getKeyRepresentation(int, boolean, boolean, boolean)} for a
	 * more accurate response. Deprecated but can still be used, although
	 * incomplete.
	 */
	@Deprecated
	public static final String getKeyName(final int keycode) {
		switch (keycode) {
		case Keyboard.KEY_0:
			return "0";
		case Keyboard.KEY_1:
			return "1";
		case Keyboard.KEY_2:
			return "2";
		case Keyboard.KEY_3:
			return "3";
		case Keyboard.KEY_4:
			return "4";
		case Keyboard.KEY_5:
			return "5";
		case Keyboard.KEY_6:
			return "6";
		case Keyboard.KEY_7:
			return "7";
		case Keyboard.KEY_8:
			return "8";
		case Keyboard.KEY_9:
			return "9";
		case Keyboard.KEY_SPACE:
			return "[SPACE]";
		case Keyboard.KEY_ENTER:
			return "[ENTER]";
		case Keyboard.KEY_BACK_SPACE:
			return "[BACKSPACE]";
		case Keyboard.KEY_ESCAPE:
			return "[ESCAPE]";
		case Keyboard.KEY_A:
			return "A";
		case Keyboard.KEY_Z:
			return "Z";
		case Keyboard.KEY_E:
			return "E";
		case Keyboard.KEY_R:
			return "R";
		case Keyboard.KEY_T:
			return "T";
		case Keyboard.KEY_Y:
			return "Y";
		case Keyboard.KEY_U:
			return "U";
		case Keyboard.KEY_I:
			return "I";
		case Keyboard.KEY_O:
			return "O";
		case Keyboard.KEY_P:
			return "P";
		case Keyboard.KEY_Q:
			return "Q";
		case Keyboard.KEY_S:
			return "S";
		case Keyboard.KEY_D:
			return "D";
		case Keyboard.KEY_F:
			return "F";
		case Keyboard.KEY_G:
			return "G";
		case Keyboard.KEY_H:
			return "H";
		case Keyboard.KEY_J:
			return "J";
		case Keyboard.KEY_K:
			return "K";
		case Keyboard.KEY_L:
			return "L";
		case Keyboard.KEY_M:
			return "M";
		case Keyboard.KEY_W:
			return "W";
		case Keyboard.KEY_X:
			return "X";
		case Keyboard.KEY_C:
			return "C";
		case Keyboard.KEY_V:
			return "V";
		case Keyboard.KEY_B:
			return "B";
		case Keyboard.KEY_N:
			return "N";
		case Keyboard.KEY_COLON:
			return ":";
		case Keyboard.KEY_SEMICOLON:
			return ";";
		case Keyboard.KEY_COMMA:
			return ",";
		case Keyboard.KEY_EXCLAMATION_MARK:
			return "!";
		case Keyboard.KEY_SHIFT:
			return "[SHIFT]";
		default:
			return "?";
		}
	}

	/**
	 * The meat of the keyboard class. Returns the representation associated with
	 * the key code while taking the modifiers into account. Very long to code but
	 * pretty complete.
	 */
	public abstract String getKeyRepresentation(final int keycode, final boolean shiftDown, final boolean capsLocked,
			final boolean altGrDown);

	/**
	 * Self explanatory.
	 */
	@Override
	public InputDeviceType type() {
		return InputDeviceType.KEYBOARD;
	}
}