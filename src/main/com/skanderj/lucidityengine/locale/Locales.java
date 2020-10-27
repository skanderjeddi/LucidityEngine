package com.skanderj.lucidityengine.locale;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;

/**
 * A class used to load and retrieve translations from .lang files - a more
 * basic copy of the Minecraft translation system.
 *
 * @author Skander Jeddi
 *
 */
public final class Locales {
	// Default language will always be ENGLISH because ENGLISH >>>>
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

	// Translations map, only 1 language at a time (#TODO maybe change that?)
	private static final Map<String, String> translationsMap = new HashMap<>();

	private Locales() {
		return;
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise. #TODO
	 * customize loading path.
	 */
	public static boolean loadLanguage(final Locale language) {
		final File languageFile = new File("res/" + language.identifier + ".lang");
		if (languageFile.exists()) {
			try {
				final BufferedReader bufferedReader = new BufferedReader(new FileReader(languageFile));
				String line = new String();
				while ((line = bufferedReader.readLine()) != null) {
					if (line.startsWith("#") || line.isBlank()) {
						continue;
					}
					Locales.translationsMap.put(line.split("=")[0], line.split("=")[1]);
				}
				bufferedReader.close();
				Logger.log(Locales.class, LogLevel.INFO, "Successfully loaded translations for language '%s'...",
						language.identifier);
				return true;
			} catch (final IOException exception) {
				Logger.log(Locales.class, LogLevel.SEVERE,
						"An exception occurred while loading translations from file for language '%s': %s",
						language.identifier, exception.getMessage());
				return false;
			}
		} else {
			Logger.log(Locales.class, LogLevel.SEVERE, "Could not find translations file for language id '%s'!",
					language.identifier);
			return false;
		}
	}

	/**
	 * Gets called if a translation is pulled before any language is properly
	 * loaded.
	 */
	private static void loadDefaultLanguage() {
		Logger.log(Locales.class, LogLevel.INFO, "Loading default translations for default language (%s)...",
				Locales.DEFAULT_LOCALE.identifier);
		Locales.loadLanguage(Locales.DEFAULT_LOCALE);
	}

	/**
	 * Self explanatory.
	 */
	public static final String get(final String key, final Object... args) {
		if (Locales.translationsMap.isEmpty()) {
			Locales.loadDefaultLanguage();
		}
		return Locales.translationsMap.get(key) == null ? "(null)"
				: String.format(Locales.translationsMap.get(key), args);
	}

	/**
	 * Language represenation with id for reference.
	 *
	 * @author Skander
	 *
	 */
	public enum Locale {
		FRENCH("fr"), ENGLISH("en");

		private String identifier;

		private Locale(final String identifier) {
			this.identifier = identifier;
		}

		public String getIdentifier() {
			return this.identifier;
		}
	}
}