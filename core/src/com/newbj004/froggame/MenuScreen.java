package com.newbj004.froggame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

import javax.swing.GroupLayout;

public class MenuScreen implements Screen {
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private TextButton playBtn;
    private TextButton exitBtn;
    FrogGame game;
    private Label titleLabel;

    // constructor to keep a reference to the main Game class
    public MenuScreen(FrogGame game) {
        this.game = game;
    }

    public void create() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        stage = new Stage();

        Texture titleTexture = new Texture(Gdx.files.internal("title.png"));
        Image titleImage = new Image(titleTexture);

        titleLabel = new Label("Frog Dash", skin);
        titleLabel.setWidth(150f);
        titleLabel.setHeight(50f);
        titleLabel.setAlignment(Align.center);
        titleLabel.setAlignment(Align.top);
        titleLabel.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
//        stage.addActor(titleLabel);

        playBtn = new TextButton("Play", skin, "default");
        playBtn.setWidth(150f);
        playBtn.setHeight(50f);
        playBtn.setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight() / 2 - 75f);
//        stage.addActor(playBtn);

        exitBtn = new TextButton("Exit", skin, "default");
        exitBtn.setWidth(150f);
        exitBtn.setHeight(50f);
        exitBtn.setPosition(Gdx.graphics.getWidth() / 2 - 75f , Gdx.graphics.getHeight() / 2 - 150f);
//        stage.addActor(exitBtn);

        Table table = new Table();
        table.setFillParent(true);
        table.add(titleImage).expandX().padTop(50).width(300f);
        table.row();
        table.add(playBtn).expandX().padTop(50).width(150f);
        table.row();
        table.add(exitBtn).expandX().padTop(50).width(150f);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    public void render(float f) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        stage.draw();
        batch.end();

        // Handle Button Commands
        if (playBtn.isPressed()) game.setScreen(FrogGame.gameScreen);
        if (exitBtn.isPressed()) Gdx.app.exit();
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
        create();
    }
    @Override
    public void hide() {
    }
}