package com.newbj004.froggame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FrogGame extends Game implements ApplicationListener {
	SpriteBatch batch;
	Texture img;
	public static MenuScreen menuScreen;
	public static GameScreen gameScreen;
	
	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
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
