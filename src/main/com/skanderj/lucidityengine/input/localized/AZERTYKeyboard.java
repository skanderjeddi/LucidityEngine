package com.skanderj.lucidityengine.input.localized;

import com.skanderj.lucidityengine.input.Keyboard;
import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;

/**
 *
 * @author Skander // TODO DOC
 *
 */
public class AZERTYKeyboard extends Keyboard {
	public AZERTYKeyboard() {
		return;
	}

	/**
	 * Don't ask me why, I wouldn't know, Java is weird.
	 */
	@Override
	public synchronized boolean isAltGrDown() {
		return this.isKeyDown(17) && this.isKeyDown(18);
	}

	/**
	 * BASED ON MY AZERTY KEYBOARD, (the ^ operator is the XOR operator in Java).
	 */
	@Override
	public final String getKeyRepresentation(final int keycode, final boolean shiftDown, final boolean capsLocked,
			final boolean altGrDown) {
		Logger.log(Keyboard.class, LogLevel.DEVELOPMENT, "Key %d has been pressed", keycode);
		switch (keycode) {
		case Keyboard.KEY_0:
			if (altGrDown) {
				return "@";
			} else if (shiftDown) {
				return "0";
			} else if (capsLocked) {
				return "À";
			} else {
				return "à";
			}
		case Keyboard.KEY_1:
			if (shiftDown ^ capsLocked) {
				return "1";
			} else {
				return "&";
			}
		case 131:
			return "~";
		case Keyboard.KEY_2:
			if (altGrDown) {
				return this.getKeyRepresentation(131, shiftDown, capsLocked, altGrDown);
			} else if (shiftDown) {
				return "2";
			} else if (capsLocked) {
				return "É";
			} else {
				return "é";
			}
		case Keyboard.KEY_3:
			if (altGrDown) {
				return "#";
			} else if (shiftDown ^ capsLocked) {
				return "3";
			} else {
				return "\"";
			}
		case Keyboard.KEY_4:
			if (altGrDown) {
				return "{";
			} else if (shiftDown ^ capsLocked) {
				return "4";
			} else {
				return "'";
			}
		case Keyboard.KEY_5:
			if (altGrDown) {
				return "[";
			} else if (shiftDown ^ capsLocked) {
				return "5";
			} else {
				return "(";
			}
		case Keyboard.KEY_6:
			if (altGrDown) {
				return "|";
			} else if (shiftDown ^ capsLocked) {
				return "6";
			} else {
				return "-";
			}
		case 128:
			return "`";
		case Keyboard.KEY_7:
			if (altGrDown) {
				return this.getKeyRepresentation(128, shiftDown, capsLocked, altGrDown);
			} else if (shiftDown) {
				return "7";
			} else if (capsLocked) {
				return "È";
			} else {
				return "è";
			}
		case Keyboard.KEY_8:
			if (altGrDown) {
				return "\\";
			} else if (shiftDown ^ capsLocked) {
				return "8";
			} else {
				return "_";
			}
		case Keyboard.KEY_9:
			if (altGrDown) {
				return "^";
			} else if (shiftDown) {
				return "9";
			} else if (capsLocked) {
				return "Ç";
			} else {
				return "ç";
			}
		case Keyboard.KEY_RIGHT_PARENTHESIS:
			if (altGrDown) {
				return "]";
			} else if (shiftDown ^ capsLocked) {
				return "°";
			} else {
				return ")";
			}
		case Keyboard.KEY_EQUALS:
			if (altGrDown) {
				return "}";
			} else if (shiftDown ^ capsLocked) {
				return "+";
			} else {
				return "=";
			}
		case Keyboard.KEY_EXCLAMATION_MARK:
			if (shiftDown ^ capsLocked) {
				return "§";
			} else {
				return "!";
			}
		case 153:
			if (shiftDown ^ capsLocked) {
				return ">";
			} else {
				return "<";
			}
		case Keyboard.KEY_COLON:
			if (shiftDown ^ capsLocked) {
				return "/";
			} else {
				return ":";
			}
		case Keyboard.KEY_SEMICOLON:
			if (shiftDown ^ capsLocked) {
				return ".";
			} else {
				return ";";
			}
		case Keyboard.KEY_COMMA:
			if (shiftDown ^ capsLocked) {
				return "?";
			} else {
				return ",";
			}
		case Keyboard.KEY_SPACE:
			return " ";
		case Keyboard.KEY_SHIFT:
		case Keyboard.KEY_ENTER:
		case Keyboard.KEY_BACK_SPACE:
		case Keyboard.KEY_ESCAPE:
		case Keyboard.KEY_CAPS_LOCK:
		case Keyboard.KEY_CONTROL:
		case Keyboard.KEY_ALT:
		case Keyboard.KEY_ALT_GRAPH:
		case Keyboard.KEY_UP:
		case Keyboard.KEY_DOWN:
		case Keyboard.KEY_LEFT:
		case Keyboard.KEY_RIGHT:
		case Keyboard.KEY_DELETE:
			return "";
		case Keyboard.KEY_A:
			if (shiftDown ^ capsLocked) {
				return "A";
			} else {
				return "a";
			}
		case Keyboard.KEY_Z:
			if (shiftDown ^ capsLocked) {
				return "Z";
			} else {
				return "z";
			}
		case Keyboard.KEY_E:
			if (shiftDown ^ capsLocked) {
				return "E";
			} else {
				return "e";
			}
		case Keyboard.KEY_R:
			if (shiftDown ^ capsLocked) {
				return "R";
			} else {
				return "r";
			}
		case Keyboard.KEY_T:
			if (shiftDown ^ capsLocked) {
				return "T";
			} else {
				return "t";
			}
		case Keyboard.KEY_Y:
			if (shiftDown ^ capsLocked) {
				return "Y";
			} else {
				return "y";
			}
		case Keyboard.KEY_U:
			if (shiftDown ^ capsLocked) {
				return "U";
			} else {
				return "u";
			}
		case Keyboard.KEY_I:
			if (shiftDown ^ capsLocked) {
				return "I";
			} else {
				return "i";
			}
		case Keyboard.KEY_O:
			if (shiftDown ^ capsLocked) {
				return "O";
			} else {
				return "o";
			}
		case Keyboard.KEY_P:
			if (shiftDown ^ capsLocked) {
				return "P";
			} else {
				return "p";
			}
		case Keyboard.KEY_Q:
			if (shiftDown ^ capsLocked) {
				return "Q";
			} else {
				return "q";
			}
		case Keyboard.KEY_S:
			if (shiftDown ^ capsLocked) {
				return "S";
			} else {
				return "s";
			}
		case Keyboard.KEY_D:
			if (shiftDown ^ capsLocked) {
				return "D";
			} else {
				return "d";
			}
		case Keyboard.KEY_F:
			if (shiftDown ^ capsLocked) {
				return "F";
			} else {
				return "f";
			}
		case Keyboard.KEY_G:
			if (shiftDown ^ capsLocked) {
				return "G";
			} else {
				return "g";
			}
		case Keyboard.KEY_H:
			if (shiftDown ^ capsLocked) {
				return "H";
			} else {
				return "h";
			}
		case Keyboard.KEY_J:
			if (shiftDown ^ capsLocked) {
				return "J";
			} else {
				return "j";
			}
		case Keyboard.KEY_K:
			if (shiftDown ^ capsLocked) {
				return "K";
			} else {
				return "k";
			}
		case Keyboard.KEY_L:
			if (shiftDown ^ capsLocked) {
				return "L";
			} else {
				return "l";
			}
		case Keyboard.KEY_M:
			if (shiftDown ^ capsLocked) {
				return "M";
			} else {
				return "m";
			}
		case Keyboard.KEY_W:
			if (shiftDown ^ capsLocked) {
				return "W";
			} else {
				return "w";
			}
		case Keyboard.KEY_X:
			if (shiftDown ^ capsLocked) {
				return "X";
			} else {
				return "x";
			}
		case Keyboard.KEY_C:
			if (shiftDown ^ capsLocked) {
				return "C";
			} else {
				return "c";
			}
		case Keyboard.KEY_V:
			if (shiftDown ^ capsLocked) {
				return "V";
			} else {
				return "v";
			}
		case Keyboard.KEY_B:
			if (shiftDown ^ capsLocked) {
				return "B";
			} else {
				return "b";
			}
		case Keyboard.KEY_N:
			if (shiftDown ^ capsLocked) {
				return "N";
			} else {
				return "n";
			}
		case 0:
			if (shiftDown ^ capsLocked) {
				return "%";
			} else {
				return "ù";
			}
		case Keyboard.KEY_DOLLAR:
			if (shiftDown ^ capsLocked) {
				return "£";
			} else {
				return "$";
			}
		case Keyboard.KEY_ASTERISK:
			if (shiftDown ^ capsLocked) {
				return "µ";
			} else {
				return "*";
			}
		case 130:
			if (shiftDown ^ capsLocked) {
				return "¨";
			} else {
				return "^";
			}
		case Keyboard.KEY_TAB:
			return "    ";
		default:
			return "(" + keycode + ")";
		}
	}
}