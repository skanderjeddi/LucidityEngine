package com.skanderj.lucidityengine.resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;
import com.skanderj.lucidityengine.util.Utilities;

/**
 * A class used for handling all images purposes. Can't be instantiated, only
 * static methods. No specific format is required.
 *
 * @author Skander Jeddi
 *
 */
public final class Images {
	private Images() {
		return;
	}

	// Images map
	private static final Map<String, BufferedImage> imagesMap = new HashMap<>();

	/**
	 * Loads an image from the provided path. Returns true if the font was
	 * successfully registered, false otherwise.
	 */
	public static boolean load(final String identifier, final String path) {
		final long startTime = System.currentTimeMillis();
		BufferedImage image;
		try {
			final File file = new File(path);
			image = ImageIO.read(file);
			final long endTime = System.currentTimeMillis();
			Images.imagesMap.put(identifier, image);
			Logger.log(Images.class, LogLevel.INFO, "Image loaded: '%s' <- %s (%d ms)", identifier, file.getPath(),
					endTime - startTime);
			return true;
		} catch (final IOException exception) {
			Logger.log(Images.class, LogLevel.SEVERE, "An exception occurred while loading image from %s: %s", path,
					exception.getMessage());
			return false;
		}
	}

	/**
	 * Loads all the image files randomly in the provided directory, formatting the
	 * identifier. Returns true if successful, false otherwise.
	 */
	public static boolean loadAll(final String identifier, final String path) {
		final File directory = new File(path);
		if (directory.isDirectory()) {
			int counter = 0;
			boolean success = true;
			for (final File file : directory.listFiles()) {
				if (!Images.load(String.format(identifier, counter), file.getPath())) {
					success = false;
				}
				counter += 1;
			}
			return success;
		} else {
			Logger.log(Images.class, Logger.LogLevel.SEVERE, "Provided path %s doesn't point to a directory", path);
			return false;
		}
	}

	/**
	 * Loads all the image files in order in the provided directory following the
	 * fileFormat format string. The identifier is also formatted. Returns true if
	 * successful, false otherwise.
	 */
	public static boolean loadAll(final String identifier, final String path, final String fileFormat) {
		final File directory = new File(path);
		if (directory.isDirectory()) {
			int counter = 1;
			boolean success = true;
			while (true) {
				final String filename = String.format(fileFormat, counter);
				if (Utilities.fileExistsInDirectory(path, filename)) {
					if (!Images.load(String.format(identifier, counter), path + "/" + filename)) {
						success = false;
					}
				} else {
					break;
				}
				counter++;
			}
			return success;
		} else {
			Logger.log(Images.class, Logger.LogLevel.SEVERE, "Provided path %s doesn't point to a directory", path);
			return false;
		}
	}

	/**
	 * Self explanatory.
	 */
	public static BufferedImage get(final String identifier) {
		final BufferedImage image = Images.imagesMap.get(identifier);
		if (image == null) {
			Logger.log(Fonts.class, Logger.LogLevel.SEVERE, "Could not find image with identifier '%s'", identifier);
			return null;
		}
		return image;
	}

	/**
	 * Self explanatory.
	 */
	public static BufferedImage[] getCollectionByID(final String identifier) {
		final List<BufferedImage> images = new ArrayList<>();
		for (final String id : Images.imagesMap.keySet()) {
			if (id.contains(identifier)) {
				images.add(Images.imagesMap.get(id));
			}
		}
		return images.toArray(new BufferedImage[images.size()]);
	}
}