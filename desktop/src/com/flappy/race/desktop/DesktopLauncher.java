package com.flappy.race.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.flappy.race.MyGdxGame;
import com.flappy.race.android.IActivityRequestHandler;

public class DesktopLauncher implements IActivityRequestHandler{
	private static DesktopLauncher application;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height=480;
		config.width=854;
		if (application == null) {
			application = new DesktopLauncher();
		}
		new LwjglApplication(new MyGdxGame(application), config);
	}

	@Override
	public void showAds(boolean show) {

	}

	@Override
	public void showInt(boolean show) {

	}

	@Override
	public void share() {

	}

	@Override
	public void onStartSomeActivity(long someParameter, long someOtherParameter) {

	}

	@Override
	public void showCredits(boolean ss ) {

	}

	@Override
	public void fbLike() {

	}
}
