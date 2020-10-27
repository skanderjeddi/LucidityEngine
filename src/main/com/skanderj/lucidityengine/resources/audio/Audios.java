package com.skanderj.lucidityengine.resources.audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.LogLevel;
import com.skanderj.lucidityengine.resources.Fonts;

/**
 * A class used for handling all audio purposes. Can't be instantiated, only
 * static methods. Can handle as much audio as you throw at it. Files must be
 * .WAV.
 *
 * @author Skander Jeddi
 *
 */
public final class Audios {
	// Reusable audio streams map - how we can play multiple sounds at once over and
	// over
	public static final Map<String, AudioInputStream> audioMap = new HashMap<>();
	// Clips map to handle pausing, playing, resuming...
	public static final Map<String, Clip> clipsMap = new HashMap<>();
	// Threads map, wrappers for each individual clip
	public static final Map<String, Thread> threadsMap = new HashMap<>();
	// Keep track of which clips are paused
	public static final Set<String> pausesMap = new HashSet<>();

	private Audios() {
		return;
	}

	/**
	 * Loads an audio from the provided path. File must be .WAV format. Returns true
	 * if the audio was successfully registered, false otherwise.
	 */
	public static boolean load(final String identifier, final String path) {
		final long startTime = System.currentTimeMillis();
		final File soundFile = new File(path);
		AudioInputStream reusableAudioInputStream;
		try {
			reusableAudioInputStream = Audios.createReusableAudioInputStream(soundFile);
			final long endTime = System.currentTimeMillis();
			Audios.audioMap.put(identifier, reusableAudioInputStream);
			Logger.log(Fonts.class, LogLevel.INFO, "Audio loaded: '%s' <- %s (%d ms)", identifier, soundFile.getPath(),
					endTime - startTime);
			return true;
		} catch (IOException | UnsupportedAudioFileException exception) {
			Logger.log(Audios.class, LogLevel.SEVERE, "An exception occurred while loading audio from %s: %s", path,
					exception.getMessage());
			return false;
		}
	}

	/**
	 * Loads all the audio files in the provided directory while adding "_0, _1, _2"
	 * to the identifier. Returns true if successful, false otherwise.
	 */
	public static boolean loadAll(final String identifier, final String path) {
		final File directory = new File(path);
		if (directory.isDirectory()) {
			int counter = 0;
			boolean success = true;
			for (final File file : directory.listFiles()) {
				if (!Audios.load(String.format(identifier, counter), file.getPath())) {
					success = false;
				}
				counter += 1;
			}
			return success;
		} else {
			Logger.log(Audios.class, Logger.LogLevel.SEVERE, "Provided path '%s' doesn't point to a directory", path);
			return false;
		}
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise. Plays audio at
	 * full volume.
	 */
	public static boolean play(final String identifier) {
		return Audios.play(identifier, 1.0F);
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise.
	 */
	public static boolean play(final String identifier, final float volume) {
		final AudioInputStream stream = Audios.audioMap.get(identifier);
		if (stream == null) {
			Logger.log(Audios.class, Logger.LogLevel.IGNORE_UNLESS_REPEATED,
					"Could not find audio with identifier '%s'", identifier);
			return false;
		} else {
			try {
				stream.reset();
				return Audios.play(identifier, stream, volume);
			} catch (final IOException exception) {
				Logger.log(Audios.class, LogLevel.SEVERE, "An exception occurred while trying to play audio '%s': %s",
						identifier, exception.getMessage());
				return false;
			}
		}
	}

	/**
	 * Self explanatory. Uses the stream to create a clip and then wrap the clip in
	 * a thread and start it. Registering everything in the corresponding maps.
	 * Returns true if successful, false otherwise.
	 */
	private static boolean play(final String identifier, final AudioInputStream audioInputStream, final float volume) {
		class AudioListener implements LineListener {
			private boolean isDone = false;

			@Override
			public synchronized void update(final LineEvent event) {
				final Type eventType = event.getType();
				if (eventType == Type.CLOSE) {
					this.isDone = true;
					this.notifyAll();
				}
			}

			public synchronized void waitUntilDone() throws InterruptedException {
				while (!this.isDone) {
					this.wait();
				}
			}
		}
		final AudioListener listener = new AudioListener();
		final Thread thread = new Thread(() -> {
			try {
				final Clip clip = AudioSystem.getClip();
				clip.addLineListener(listener);
				clip.open(audioInputStream);
				try {
					final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(20f * (float) Math.log10(volume));
					clip.start();
					Audios.clipsMap.put(identifier, clip);
					try {
						listener.waitUntilDone();
					} catch (final InterruptedException interruptedException) {
						Logger.log(Audios.class, LogLevel.IGNORE,
								"An exception occurred while waiting for audio '%s' to finish: %s", identifier,
								interruptedException.getMessage());
					}
				} finally {
					clip.close();
					Audios.clipsMap.remove(identifier);
				}
			} catch (LineUnavailableException | IOException exception) {
				Logger.log(Audios.class, LogLevel.SEVERE, "An exception occurred while trying to play audio '%s': %s",
						identifier, exception.getMessage());
			} finally {
				try {
					audioInputStream.close();
				} catch (final IOException ioException) {
					Logger.log(Audios.class, LogLevel.SEVERE,
							"An exception occurred while trying to close audio stream '%s': %s", identifier,
							ioException.getMessage());
				}
			}
		});
		thread.start();
		Audios.threadsMap.put(identifier, thread);
		return true;
	}

	/**
	 * Self explanatory.
	 */
	public static boolean isPaused(final String identifier) {
		if (Audios.pausesMap.contains(identifier)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Self explanatory.
	 */
	public static float getVolume(final String identifier) {
		final Clip clip = Audios.clipsMap.get(identifier);
		if (clip == null) {
			Logger.log(Audios.class, Logger.LogLevel.IGNORE_UNLESS_REPEATED,
					"Could not find audio with identifier '%s'", identifier);
			return -1;
		} else {
			final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			return (float) Math.pow(10f, gainControl.getValue() / 20f);
		}
	}

	/**
	 * Sets the volume for a running clip. Volume must be between 0f and 1f.
	 */
	public static void setVolume(final String identifier, final float volume) {
		final Clip clip = Audios.clipsMap.get(identifier);
		if (clip == null) {
			Logger.log(Audios.class, Logger.LogLevel.IGNORE_UNLESS_REPEATED,
					"Could not find audio with identifier '%s' (set-v)", identifier);
		} else {
			if ((volume < 0f) || (volume > 1f)) {
				throw new IllegalArgumentException("Volume not valid: " + volume);
			}
			final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(20f * (float) Math.log10(volume));
		}
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise.
	 */
	public static boolean pause(final String identifier) {
		final Clip clip = Audios.clipsMap.get(identifier);
		if (clip == null) {
			Logger.log(Audios.class, Logger.LogLevel.IGNORE_UNLESS_REPEATED,
					"Could not find audio with identifier '%s'", identifier);
			return false;
		} else {
			clip.stop();
			Audios.pausesMap.add(identifier);
			return true;
		}
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise.
	 */
	public static boolean resume(final String identifier) {
		final Clip clip = Audios.clipsMap.get(identifier);
		if (clip == null) {
			Logger.log(Audios.class, Logger.LogLevel.IGNORE_UNLESS_REPEATED,
					"Could not find audio with identifier '%s'", identifier);
			return false;
		} else {
			clip.start();
			Audios.pausesMap.remove(identifier);
			return true;
		}
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise.
	 */
	public static boolean stop(final String identifier) {
		final Clip clip = Audios.clipsMap.get(identifier);
		if (clip == null) {
			Logger.log(Audios.class, Logger.LogLevel.IGNORE_UNLESS_REPEATED,
					"Could not find audio with identifier '%s'", identifier);
			return false;
		} else {
			clip.stop();
			Audios.threadsMap.get(identifier).interrupt();
			return true;
		}
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise.
	 */
	public static boolean stopAll() {
		for (final AudioInputStream audioInputStream : Audios.audioMap.values()) {
			try {
				audioInputStream.reset();
				audioInputStream.close();
			} catch (final IOException ioException) {
				Logger.log(Audios.class, LogLevel.SEVERE,
						"An exception occurred while trying to close audio streams '%s': %s", ioException.getMessage());
				return false;
			}
		}
		for (final Clip clip : Audios.clipsMap.values()) {
			clip.stop();
		}
		for (final Thread thread : Audios.threadsMap.values()) {
			thread.interrupt();
		}
		return true;
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise. -1 to loop
	 * indefinitely. Plays audio at full volume.
	 */
	public static boolean loop(final String identifier, final int count) {
		return Audios.loop(identifier, count, 1.0F);
	}

	/**
	 * Self explanatory. Returns true if successful, false otherwise. -1 to loop
	 * indefinitely.
	 */
	public static boolean loop(final String identifier, final int count, final double volume) {
		final AudioInputStream stream = Audios.audioMap.get(identifier);
		if (stream == null) {
			Logger.log(Audios.class, Logger.LogLevel.SEVERE, "Could not find audio stream with identifier '%s'",
					identifier);
			return false;
		} else {
			try {
				stream.reset();
			} catch (final IOException ioException) {
				Logger.log(Audios.class, LogLevel.SEVERE, "An exception occurred while trying to play audio '%s': %s",
						identifier, ioException.getMessage());
				return false;
			}
			return Audios.loop(identifier, count, stream, volume);
		}
	}

	/**
	 * Self explanatory. Uses the stream to create a clip and then wrap the clip in
	 * a thread and loop it. Registering everything in the corresponding maps.
	 * Returns true if successful, false otherwise.
	 */
	private static boolean loop(final String identifier, final int count, final AudioInputStream audioInputStream,
			final double volume) {
		class AudioListener implements LineListener {
			private boolean isDone = false;

			@Override
			public synchronized void update(final LineEvent event) {
				final Type eventType = event.getType();
				if (eventType == Type.CLOSE) {
					this.isDone = true;
					this.notifyAll();
				}
			}

			public synchronized void waitUntilDone() throws InterruptedException {
				while (!this.isDone) {
					this.wait();
				}
			}
		}
		final AudioListener listener = new AudioListener();
		final Thread thread = new Thread(() -> {
			try {
				final Clip clip = AudioSystem.getClip();
				clip.addLineListener(listener);
				clip.open(audioInputStream);
				try {
					final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(20f * (float) Math.log10(volume));
					clip.loop(count == -1 ? -1 : count - 1);
					Audios.clipsMap.put(identifier, clip);
					listener.waitUntilDone();
				} catch (final InterruptedException interruptedException) {
					Logger.log(Audios.class, LogLevel.IGNORE,
							"An exception occurred while waiting for audio '%s' to finish: %s", identifier,
							interruptedException.getMessage());
				} finally {
					clip.close();
					Audios.clipsMap.remove(identifier);
				}
			} catch (final LineUnavailableException | IOException exception) {
				Logger.log(Audios.class, LogLevel.SEVERE, "An exception occurred while trying to loop audio '%s': %s",
						identifier, exception.getMessage());
			} finally {
				try {
					audioInputStream.close();
				} catch (final IOException ioException) {
					Logger.log(Audios.class, LogLevel.SEVERE,
							"An exception occurred while trying to close audio stream '%s': %s", identifier,
							ioException.getMessage());
				}
			}
		});
		thread.start();
		Audios.threadsMap.put(identifier, thread);
		return true;
	}

	/**
	 * This is where the magic - and the reusable audio streams creation happens.
	 */
	private static AudioInputStream createReusableAudioInputStream(final File file)
			throws IOException, UnsupportedAudioFileException {
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(file);
			final byte[] buffer = new byte[1024 * 32];
			int read = 0;
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(buffer.length);
			while ((read = audioInputStream.read(buffer, 0, buffer.length)) != -1) {
				outputStream.write(buffer, 0, read);
			}
			final AudioInputStream reusableAudioInputStream = new AudioInputStream(
					new ByteArrayInputStream(outputStream.toByteArray()), audioInputStream.getFormat(),
					AudioSystem.NOT_SPECIFIED);
			return reusableAudioInputStream;
		} finally {
			if (audioInputStream != null) {
				audioInputStream.close();
			}
		}
	}
}