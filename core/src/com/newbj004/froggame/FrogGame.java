package com.newbj004.froggame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationListener;

public class FrogGame extends Game implements ApplicationListener {
	public static MenuScreen menuScreen;
	public static GameScreen gameScreen;
	public static WinScreen winScreen;
	public static final float MUSIC_VOLUME = 0.5f;
	
	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		winScreen = new WinScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void pause() {
	super.pause();
	}

	@Override
	public void resume() {
	super.resume();
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
