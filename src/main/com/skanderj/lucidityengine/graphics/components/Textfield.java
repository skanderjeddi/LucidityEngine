package com.skanderj.lucidityengine.graphics.components;

import java.util.ArrayList;
import java.util.List;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.input.Keyboard;
import com.skanderj.lucidityengine.input.Keyboard.KeyState;

/**
 * Represents an abstract textbox, basis for other textbox classes which can
 * implement their rendering the way they please.
 *
 * @author Skander Jeddi
 *
 */
public abstract class Textfield extends Component {
	// Global focus and local focus mixed in together because this is easy
	protected boolean hasFocus;
	// Collection of the previously typed maximumLines
	protected List<String> text;
	// Can we write over multiple maximumLines?
	protected boolean multiline;
	// The current working line
	protected String currentLine;
	// Accents handling
	protected boolean hatCarry, twoPointsCarry;
	// Have we hit the maximumLines limit yet?
	protected boolean canAddLines;
	// Cursor position in line
	protected int cursorPosition;

	// Basic constructor: position
	public Textfield(final Application application) {
		super(application);
		// No by default
		this.hasFocus = false;
		// Was a ^ typed last frame
		this.hatCarry = false;
		// Was a ¨ typed last frame
		this.twoPointsCarry = false;
		// Yes by default, we haven't typed anything yet
		this.canAddLines = true;
		// Default cursor position
		this.cursorPosition = 0;
		// Empty current line and lines collection
		this.currentLine = new String();
		this.text = new ArrayList<>();
	}

	@Override
	public synchronized void update() {
		// Check if the component has global focus
		if (this.hasFocus) {
			// Go through every keyboard key and retain those which are pressed at the
			// current frame
			for (final int keyCode : this.application.keyboard().getKeysByState(KeyState.DOWN_IN_CURRENT_FRAME)) {
				// Left key handling, moves cursor to the left once
				if (keyCode == Keyboard.KEY_LEFT) {
					this.cursorPosition -= 1;
					if (this.cursorPosition < 0) {
						this.cursorPosition = 0;
					}
					break;
				}
				// Right key handling, moves cursor to the right once
				if (keyCode == Keyboard.KEY_RIGHT) {
					this.cursorPosition += 1;
					if (this.cursorPosition >= this.currentLine.length()) {
						this.cursorPosition = this.currentLine.length();
					}
					break;
				}
				// Newline
				if (keyCode == Keyboard.KEY_ENTER) {
					if (this.multiline) {
						if (this.canAddLines) {
							// If we can still add lines, register the one we currently have to the list,
							// reset it and go to a newline visually (resetting the cursor)
							this.text.add(this.currentLine);
							this.currentLine = new String();
							this.cursorPosition = 0;
							break;
						}
					}
				}
				// Deleting (backspace)
				if (keyCode == Keyboard.KEY_BACK_SPACE) {
					if (!this.currentLine.isEmpty() && (this.cursorPosition != 0)) {
						// If we actually have something to delete, delete the previous character
						// adjacent to the cursor and move everything back
						final char[] newLine = new char[this.currentLine.length() - 1];
						boolean hasSkipped = false;
						for (int index = 0; index < (this.currentLine.length() - 1); index += 1) {
							if (index == (this.cursorPosition - 1)) {
								hasSkipped = true;
							}
							newLine[index] = this.currentLine.toCharArray()[hasSkipped ? index + 1 : index];
						}
						this.currentLine = new String(newLine);
						// Don't forget to move the cursor back once!
						this.cursorPosition -= 1;
						break;
					}
				}
				// Deleting (delete)
				if (keyCode == Keyboard.KEY_DELETE) {
					if (this.cursorPosition == this.currentLine.length()) {
						// We don't have anything to delete
						break;
					} else {
						// If we actually have something to delete, delete the next character
						// adjacent to the cursor and move everything back starting from the cursor
						final char[] newLine = new char[this.currentLine.length() - 1];
						boolean hasSkipped = false;
						for (int index = 0; index < (this.currentLine.length() - 1); index += 1) {
							if (index == this.cursorPosition) {
								hasSkipped = true;
							}
							newLine[index] = this.currentLine.toCharArray()[hasSkipped ? index + 1 : index];
						}
						this.currentLine = new String(newLine);
						break;
					}
				}
				// See Keyboard.getKeyRepresentation(), pretty self explanatory
				String key = this.application.keyboard().getKeyRepresentation(keyCode,
						this.application.keyboard().isShiftDown(), this.application.keyboard().isCapsLocked(),
						this.application.keyboard().isAltGrDown());
				{
					if (key.equals("^") && !this.hatCarry) {
						// A ^ was pressed to carry it to the next character
						this.hatCarry = true;
						break;
					}
					if (key.equals("¨") && !this.twoPointsCarry) {
						// A ¨ was pressed to carry it to the next character
						this.twoPointsCarry = true;
						break;
					}
					if (this.hatCarry) {
						// Handle accents
						if (key.equals("e")) {
							key = "ê";
						} else if (key.equals("o")) {
							key = "ô";
						} else if (key.equals("E")) {
							key = "Ê";
						} else if (key.equals("O")) {
							key = "Ô";
						} else if (key.equals("u")) {
							key = "û";
						} else if (key.equals("U")) {
							key = "Û";
						} else if (key.equals("a")) {
							key = "â";
						} else if (key.equals("A")) {
							key = "Â";
						}
						// Reset the carry
						this.hatCarry = false;
					}
					if (this.twoPointsCarry) {
						// Handle accents
						if (key.equals("e")) {
							key = "ë";
						} else if (key.equals("o")) {
							key = "ö";
						} else if (key.equals("E")) {
							key = "Ë";
						} else if (key.equals("O")) {
							key = "Ö";
						} else if (key.equals("u")) {
							key = "ü";
						} else if (key.equals("U")) {
							key = "Ü";
						}
						// Reset the carry
						this.twoPointsCarry = false;
					}
				}
				// new char to add
				char[] newLine = null;
				// Handles TAB in particular, adds any other key
				{
					if (key.equals("    ")) {
						// This is TAB so add 4 spaces
						newLine = new char[this.currentLine.toCharArray().length + 4];
						newLine[this.cursorPosition] = key.charAt(0);
						newLine[this.cursorPosition + 1] = key.charAt(0);
						newLine[this.cursorPosition + 2] = key.charAt(0);
						newLine[this.cursorPosition + 3] = key.charAt(0);
						// Copy the beginning of the string to the new string (represented as a char
						// array for easy handling)
						for (int index = 0; index < this.cursorPosition; index += 1) {
							newLine[index] = this.currentLine.toCharArray()[index];
						}
						// Copy the rest of the string after having inserted the 4 spaces
						for (int index = this.cursorPosition + 4; index < newLine.length; index += 1) {
							newLine[index] = this.currentLine.toCharArray()[index - 4];
						}
					} else {
						// If it's not TAB
						if (!key.isEmpty()) { // If it's a visual key and not ENTER, BACKSPACE, etc..
							// Create a char array of size length of current string + 1 (so we can insert a
							// character)
							newLine = new char[this.currentLine.toCharArray().length + 1];
							// Add the character at the cursor position and copy the rest around it in the 2
							// for loops
							newLine[this.cursorPosition] = key.charAt(0);
							for (int index = 0; index < this.cursorPosition; index += 1) {
								newLine[index] = this.currentLine.toCharArray()[index];
							}
							for (int index = this.cursorPosition + 1; index < newLine.length; index += 1) {
								newLine[index] = this.currentLine.toCharArray()[index - 1];
							}
						}
					}
				}
				// If we added a TAB increase the cursor position by 4 otherwise by only 1 if
				// the key was a "visual key"
				{
					if (newLine != null) {
						this.currentLine = new String(newLine);
						if (key.equals("    ")) {
							this.cursorPosition += 4;
						} else {
							this.cursorPosition += 1;
						}
					}
				}
			}
		}
	}

	/**
	 * Can alwaus move focus away from textbox.
	 */
	@Override
	public final boolean canChangeFocus() {
		return true;
	}

	/**
	 * Gives global focus.
	 */
	@Override
	public final void grantFocus() {
		this.hasFocus = true;
	}

	/**
	 * Removes global focus.
	 */
	@Override
	public final void revokeFocus() {
		this.hasFocus = false;
	}

	/**
	 * Self explanatory.
	 */
	public boolean isMultiline() {
		return this.multiline;
	}

	/**
	 * Self explanatory.
	 */
	public List<String> text() {
		return this.text;
	}

	/**
	 * Self explanatory.
	 */
	public String currentLine() {
		return this.currentLine;
	}

	/**
	 * Self explanatory.
	 */
	public int cursorPosition() {
		return this.cursorPosition;
	}

	/**
	 * Self explanatory.
	 */
	public void setCursorPosition(final int cursorPosition) {
		this.cursorPosition = cursorPosition;
	}
}