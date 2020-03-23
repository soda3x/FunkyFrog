package com.newbj004.froggame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameScreen implements Screen {
    FrogGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ParticleEffect particleEffect;

    // constructor to keep a reference to the main Game class
    public GameScreen(FrogGame game) {
        this.game = game;
    }
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("particles/star.p"), Gdx.files.internal("particles/"));
        Gdx.app.log("GameScreen: ","gameScreen create");
    }
    public void render(float f) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (Gdx.input.isTouched()) {
            if (particleEffect.isComplete()) {
                particleEffect.reset();
            }
            Vector3 world = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(world);
            particleEffect.setPosition(world.x, world.y);
        }

        particleEffect.update(Gdx.graphics.getDeltaTime());
        particleEffect.draw(batch, Gdx.graphics.getDeltaTime());

        batch.end();

        Gdx.app.log("GameScreen: ","gameScreen render");
        Gdx.app.log("GameScreen: ","About to call gameScreen");
//        game.setScreen(MyGdxGame.menuScreen);
        Gdx.app.log("GameScreen: ","menuScreen started");
    }
    @Override
    public void dispose() { }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void show() {
        Gdx.app.log("GameScreen: ","gameScreen show called");
        create();
    }
    @Override
    public void hide() {
        Gdx.app.log("GameScreen: ","gameScreen hide called");
    }
}
