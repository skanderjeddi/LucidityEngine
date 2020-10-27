package com.skanderj.lucidityengine.util;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Helper class for basically anything that doesn't fit somewhere else.
 *
 * @author Skander Jeddi
 *
 */
public final class Utilities {
	public static final String NULL = null, EMPTY = new StringBuilder("").toString(),
			SPACE = new StringBuilder(" ").toString();

	private static final Random random = new Random();

	private Utilities() {
		return;
	}

	public static OperatingSystem getOperatingSystem() {
		final String osName = System.getProperty("os.name");
		if (osName.toLowerCase().contains("win")) {
			return OperatingSystem.WINDOWS;
		} else if (osName.toLowerCase().contains("unix")) {
			return OperatingSystem.UNIX;
		} else if (osName.toLowerCase().contains("linux")) {
			return OperatingSystem.LINUX;
		} else if (osName.toLowerCase().contains("mac")) {
			return OperatingSystem.MACOS;
		} else {
			return OperatingSystem.OTHER;
		}
	}

	public static void disableHiDPI() {
		System.setProperty("sun.java2d.uiScale", "1.0");
	}

	public static int framesToMS(final int frames, final double refreshRate) {
		return (int) ((1000 * frames) / refreshRate);
	}

	/**
	 * Self explanatory.
	 */
	@SafeVarargs
	public static final <T> T[] createArray(final T... ts) {
		return ts;
	}

	/**
	 * Self explanatory.
	 */
	public static boolean fileExistsInDirectory(final String directoryPath, final String fileName) {
		final File directory = new File(directoryPath);
		if (directory.exists() && directory.isDirectory()) {
			for (final File fileIn : directory.listFiles()) {
				if (fileIn.isFile()) {
					if (fileIn.getName().equals(fileName)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Self explanatory.
	 */
	public static String fileNameCompatibleDateString() {
		return new SimpleDateFormat("MM-dd-YYYY_hh-mm-ss").format(new Date());
	}

	/**
	 * Returns a random integer between a and b - included. Why doesn't Java have
	 * this????
	 */
	public static int randomInteger(final int a, final int b) {
		final int min = Math.min(a, b);
		final int max = Math.max(a, b);
		return Utilities.random.nextInt(Math.abs(min < 0 ? 2 * max : max) + 1) + ((min < 0 ? 1 : -1) * min);
	}

	/**
	 * Self explanatory.
	 */
	public static double randomDouble(final double a, final double b) {
		final double d = Utilities.random.nextDouble();
		return a + ((b - a) * d);
	}

	/**
	 * Self explanatory.
	 */
	public static Color buildAgainst(final Color color, final int alpha) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
	}

	/**
	 * Returns a random color. If useAlpha, color will have a random transparency.
	 */
	public static Color randomColor(final boolean useAlpha) {
		return new Color(Utilities.random.nextInt(255), Utilities.random.nextInt(255), Utilities.random.nextInt(255),
				useAlpha ? Utilities.random.nextInt(255) : 255);
	}

	/**
	 * I invite you to read the p5js documentation for this beautiful function.
	 */
	public static double map(final double value, final double valueMin, final double valueMax, final double targetMin,
			final double targetMax, final boolean withinBounds) {
		final double newval = (((value - valueMin) / (valueMax - valueMin)) * (targetMax - targetMin)) + targetMin;
		if (!withinBounds) {
			return newval;
		}
		if (targetMin < targetMax) {
			return Utilities.constraint(newval, targetMin, targetMax);
		} else {
			return Utilities.constraint(newval, targetMax, targetMin);
		}
	}

	/**
	 * Used for {@link Utilities}{@link #map(float, float, float, float, float,
	 * boolean))}
	 */
	private static double constraint(final double value, final double minimum, final double maximum) {
		return Math.max(Math.min(value, maximum), minimum);
	}
}