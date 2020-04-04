package com.newbj004.froggame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

public class GameScreen implements Screen {
    FrogGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TextButton pauseBtn;
    private Skin skin;
    private Stage stage;

    private Texture frogSheet;
    private TextureRegion[] frogWalkFrames;
    private Animation frogWalkAnimation;
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 2;
    private float stateTime;
    private TextureRegion frogCurrentFrame;
    private int frogFrameIndex;


    // constructor to keep a reference to the main Game class
    public GameScreen(FrogGame game) {
        this.game = game;

    }
    public void create() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        frogSheet = new Texture(Gdx.files.internal("froggy.png"));

        TextureRegion[][] temp = TextureRegion.split(frogSheet, frogSheet.getWidth() / FRAME_COLS, frogSheet.getHeight() / FRAME_ROWS);
        frogWalkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; ++i) {
            for (int j = 0; j < FRAME_COLS; ++j) {
                frogWalkFrames[index++] = temp[i][j];
            }
        }

        frogWalkAnimation = new Animation(0.033f, frogWalkFrames);
        stateTime = 0.0f;

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        stage = new Stage();

        pauseBtn = new TextButton("| |", skin, "default");
        pauseBtn.setWidth(40f);
        pauseBtn.setHeight(40f);
        pauseBtn.setPosition(Gdx.graphics.getWidth() / 1.0f - 30, Gdx.graphics.getHeight() / 1 - 50, Align.center);
//        pauseBtn.setPosition(Gdx.graphics.getWidth() / 2 - 75f, Gdx.graphics.getHeight() / 2 - 75f);
        stage.addActor(pauseBtn);

        Gdx.input.setInputProcessor(stage);
    }
    public void render(float f) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        frogCurrentFrame = (TextureRegion) frogWalkAnimation.getKeyFrame(stateTime, true);
        frogFrameIndex = frogWalkAnimation.getKeyFrameIndex(stateTime);
        batch.begin();
        stage.draw();
        batch.draw(frogCurrentFrame, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        batch.end();
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
