package com.newbj004.froggame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

public class GameScreen implements Screen {

    public enum GameState { PLAYING, WIN, LOSE };

    FrogGame game;

    private OrthographicCamera camera;

    private SpriteBatch batch;
    private TextButton pauseBtn;
    private Skin skin;
    private Stage stage;

    private float stateTime;

    private GameState gameState;
    private Music bgMusic;

    private TiledMap streetMap;
    private TiledMapRenderer tiledMapRenderer;

    private Frog frog;

    // constructor to keep a reference to the main Game class
    public GameScreen(FrogGame game) {
        this.game = game;
        this.frog = new Frog();

    }
    public void create() {

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("level1.wav"));
        bgMusic.setLooping(true);
        bgMusic.play();
        bgMusic.setVolume(0.5f);

        streetMap = new TmxMapLoader().load("street.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(streetMap);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        frog.create();

        stateTime = 0.0f;

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        stage = new Stage();

        pauseBtn = new TextButton("| |", skin, "default");
        pauseBtn.setWidth(40f);
        pauseBtn.setHeight(40f);
        pauseBtn.setPosition(Gdx.graphics.getWidth() / 1.0f - 30, Gdx.graphics.getHeight() / 1 - 50, Align.center);
        stage.addActor(pauseBtn);

        Gdx.input.setInputProcessor(stage);

        this.newGame();
    }
    public void render(float f) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();

        camera.update();

        this.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);

        stage.draw();
        batch.begin();

        batch.draw(frog.render(stateTime), 270, 0);
        batch.end();
    }
    @Override
    public void dispose() {
        streetMap.dispose();
        bgMusic.dispose();
    }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void show() {
        create();
    }
    @Override
    public void hide() {

    }

    private int getRandomIntBetween(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void update() {
        boolean checkTouch = Gdx.input.isTouched();
        int touchX = Gdx.input.getX();
        int touchY = Gdx.input.getY();

        switch (gameState) {
            case PLAYING:
                int moveX = 0;
                int moveY = 0;
                // Check if screen is touched and then we can get where and move Funkmaster Frog to that x,y
                if (checkTouch) {
//                    game.setScreen(game.winScreen);
//                    bgMusic.dispose();
                }

        }
    }


    private void newGame() {
        gameState = GameState.PLAYING;
    }
}
