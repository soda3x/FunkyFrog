package com.newbj004.froggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class WinScreen implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private TextButton backToMenuBtn;
    FrogGame game;
    private Label titleLabel;
    private Music winMusic;
    private Music winJingle;
    private ParticleEffect stars;
    private float particleX;
    private float particleY;

    // constructor to keep a reference to the main Game class
    public WinScreen(FrogGame game) {
        this.game = game;
    }

    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        stage = new Stage();
        winMusic = Gdx.audio.newMusic(Gdx.files.internal("win.wav"));
        winMusic.setLooping(true);
        winMusic.setVolume(0.5f);
        winJingle = Gdx.audio.newMusic(Gdx.files.internal("winjingle.wav"));
        winJingle.setLooping(false);
        winJingle.setVolume(0.5f);
        winJingle.play();

        stars = new ParticleEffect();
        stars.load(Gdx.files.internal("particles/star.p"), Gdx.files.internal("particles/"));


        Texture winTextTexture = new Texture(Gdx.files.internal("wintext.png"));
        Image winText = new Image(winTextTexture);

        Texture  winJokeTexture = new Texture(Gdx.files.internal("winjoke.png"));
        Image winJoke = new Image(winJokeTexture);

        titleLabel = new Label("Funky Frog", skin);
        titleLabel.setWidth(150f);
        titleLabel.setHeight(50f);
        titleLabel.setAlignment(Align.center);
        titleLabel.setAlignment(Align.top);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        backToMenuBtn = new TextButton("Menu", skin, "default");
        backToMenuBtn.setWidth(150f);
        backToMenuBtn.setHeight(50f);
        backToMenuBtn.setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight() / 2 - 75f);


        Table table = new Table();
        table.setFillParent(true);
        table.add(winText).expandX().padTop(50).width(300f);
        table.row();
        table.add(winJoke).expandX().width(300f);
        table.row();
        table.add(backToMenuBtn).expandX().padTop(50).width(150f);

        particleX = winText.getX();
        particleY = winText.getY();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float f) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        stage.draw();

        if (stars.isComplete()) stars.reset();
        stars.start();
        stars.setPosition(Gdx.graphics.getWidth(), 0f);
        stars.update(Gdx.graphics.getDeltaTime());
        stars.draw(batch, Gdx.graphics.getDeltaTime());


        batch.end();



        if (!winJingle.isPlaying()) {
            winMusic.play();
        }

        // Handle Button Commands
        if (backToMenuBtn.isPressed()) {
            game.setScreen(FrogGame.menuScreen);
            winMusic.dispose();
        }
    }

    @Override
    public void dispose() {
        winMusic.dispose();
        winJingle.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        create();
    }

    @Override
    public void hide() {
    }
}

