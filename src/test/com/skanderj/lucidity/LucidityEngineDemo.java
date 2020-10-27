package com.skanderj.lucidity;

import java.awt.Color;

import com.skanderj.lucidityengine.Application;
import com.skanderj.lucidityengine.ThreadWrapper.ThreadWrapperType;
import com.skanderj.lucidityengine.core.Engine;
import com.skanderj.lucidityengine.core.Scene;
import com.skanderj.lucidityengine.exts.FlickeringLabel;
import com.skanderj.lucidityengine.exts.ImageBackground;
import com.skanderj.lucidityengine.exts.transitions.FadeInTransition;
import com.skanderj.lucidityengine.exts.transitions.FadeOutTransition;
import com.skanderj.lucidityengine.graphics.OnScreenTextProperties;
import com.skanderj.lucidityengine.graphics.OnScreenTextProperties.OnScreenTextPosition;
import com.skanderj.lucidityengine.graphics.WindowConfiguration;
import com.skanderj.lucidityengine.input.Keyboard;
import com.skanderj.lucidityengine.input.binds.Binds;
import com.skanderj.lucidityengine.logging.Logger;
import com.skanderj.lucidityengine.logging.Logger.DebuggingType;
import com.skanderj.lucidityengine.resources.Fonts;
import com.skanderj.lucidityengine.resources.Images;
import com.skanderj.lucidityengine.resources.audio.Audios;
import com.skanderj.lucidityengine.util.Utilities;

/**
 *
 * @author Skander Jeddi
 *
 */
public final class LucidityEngineDemo extends Application {
	public static final String DEMO_IDENTIFIER = "lucidity-demo";
	public static final String WINDOW_TITLE = "Lucidity Engine Demo";
	public static final int WINDOW_WIDTH = 1200, WINDOW_HEIGHT = (int) ((LucidityEngineDemo.WINDOW_WIDTH / 16.0) * 9),
			BUFFERS = 2;
	public static final double TARGET_REFRESH_RATE = 144.0;
	public static final boolean FULLSCREEN = false;

	public LucidityEngineDemo() {
		super(LucidityEngineDemo.DEMO_IDENTIFIER, LucidityEngineDemo.TARGET_REFRESH_RATE,
				LucidityEngineDemo.TARGET_REFRESH_RATE, Keyboard.AZERTY);
	}

	@Override
	public String firstSceneIdentifier() {
		return "main-menu";
	}

	@Override
	protected void loadResources() {
		// TODO Auto-generated method stub
		Images.load("space-bg", "res/space_bg.jpg");
		Fonts.load("never", "res/gomarice_no_continue.ttf");
		Audios.load("bg-music", "res/space-station-no9.wav");
	}

	@Override
	protected void registerApplicationObjects() {
		Engine.setObject("space-bg", new ImageBackground(this, 0, 0, this.windowConfiguration().getWidth(),
				this.windowConfiguration().getHeight(), Images.get("space-bg")));
		Engine.setObject("title-label-flicker",
				new FlickeringLabel(this, 0, 0, this.windowConfiguration().getWidth(),
						this.windowConfiguration().getHeight() / 4, "Lucidity Engine",
						new OnScreenTextProperties(Fonts.get("never", 72),
								Utilities.buildAgainst(Color.MAGENTA.darker(), 35), Color.GRAY,
								OnScreenTextPosition.CENTERED)));
		Engine.setObject("subtitle-label-flicker",
				new FlickeringLabel(this, 0, 0, this.windowConfiguration().getWidth(),
						this.windowConfiguration().getHeight() / 2, "[DEMO]",
						new OnScreenTextProperties(Fonts.get("never", 48),
								Utilities.buildAgainst(Color.PINK.darker(), 35), Color.GRAY,
								OnScreenTextPosition.CENTERED)));
	}

	@Override
	protected void registerScenes() {
		Engine.registerScene("main-menu", new Scene(this) {
			@Override
			public void enter() {
				return;
			}

			@Override
			public void exit() {
				return;
			}
		}.addObjects(new String[] { "space-bg", "title-label-flicker", "subtitle-label-flicker" }));
		((Scene) Engine.getObject("main-menu")).setInTransition(new FadeInTransition(this, 144 * 3, Color.BLACK));
		((Scene) Engine.getObject("main-menu")).setOutTransation(new FadeOutTransition(this, 144 * 3, Color.BLACK));
		Engine.registerScene("main-menu-2", new Scene(this) {
			@Override
			public void enter() {
				return;
			}

			@Override
			public void exit() {
				return;
			}
		}.addObjects(new String[] { "title-label-flicker", "subtitle-label-flicker" }));
		((Scene) Engine.getObject("main-menu-2")).setInTransition(new FadeInTransition(this, 144 * 3, Color.WHITE));
	}

	@Override
	protected void registerBinds() {
		Binds.registerBind("main-menu", Utilities.createArray(Keyboard.KEY_SPACE),
				Utilities.createArray(Keyboard.KeyState.DOWN_IN_CURRENT_FRAME),
				(object) -> Engine.switchToScene("main-menu-2"));
		Binds.registerBind("main-menu-2", Utilities.createArray(Keyboard.KEY_ESCAPE),
				Utilities.createArray(Keyboard.KeyState.DOWN_IN_CURRENT_FRAME),
				(object) -> Engine.switchToScene("main-menu"));
	}

	@Override
	protected void createThread(final ThreadWrapperType type) {
		switch (type) {
		case RENDER:
			this.window.show();
			Audios.loop("bg-music", -1, 0.1f);
		case UPDATE:
			return;
		}
	}

	@Override
	protected void destroyThread(final ThreadWrapperType type) {
		switch (type) {
		case RENDER:
			Audios.stopAll();
			this.window.hide(true);
			return;
		case UPDATE:
			return;
		}
	}

	@Override
	protected void update(final double delta) {
		super.update(delta);
	}

	@Override
	protected void render() {
		this.screen().focusOnQuality();
		super.render();
	}

	public static void main(final String[] args) {
		Logger.toggleDebugging(DebuggingType.CLASSIC, true);
		Logger.toggleDebugging(DebuggingType.DEVELOPMENT, false);
		new LucidityEngineDemo().start();
	}

	@Override
	protected WindowConfiguration windowConfiguration() {
		return new WindowConfiguration(LucidityEngineDemo.WINDOW_TITLE, LucidityEngineDemo.WINDOW_WIDTH,
				LucidityEngineDemo.WINDOW_HEIGHT, LucidityEngineDemo.FULLSCREEN, LucidityEngineDemo.BUFFERS);
	}
}
