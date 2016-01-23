package com.flappy.race;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.flappy.race.Assets.Assets;
import com.flappy.race.GameScreen.MenuScreen;
import com.flappy.race.android.IActivityRequestHandler;


public class MyGdxGame extends Game {
	private IActivityRequestHandler myRequestHandler;
	//MenuScreen.MyGameCallback myGameCallback;
	public MyGdxGame(IActivityRequestHandler iActivityRequestHandler) {
			myRequestHandler = iActivityRequestHandler;
		//this.myGameCallback = myGameCallback;

	}

	@Override
	public void create () {

		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		//actionResolver.showOrLoadInterstital();
		//setScreen(new MenuScreen(this,actionResolver)

		setScreen(new MenuScreen(this,myRequestHandler));
	}

	@Override
	public void dispose() {
		super.dispose();
		System.out.println("indoisp");
		Assets.instance.dispose();
	}
}