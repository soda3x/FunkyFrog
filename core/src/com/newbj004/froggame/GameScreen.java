package com.newbj004.froggame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class GameScreen implements Screen {

    public enum GameState { PLAYING, WIN, LOSE };

    FrogGame game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private TextButton pauseBtn;
    private Skin skin;
    private Stage stage;
    private TextButton backToMenuBtn;

    private float stateTime;
    private GameState gameState;

    private Music bgMusic;
    private Music loseMusicPart1;
    private Music loseMusicPart2;

    private TiledMap streetMap;
    private TiledMapRenderer tiledMapRenderer;
    private Frog frog;
    private Car[][] cars;

    private static final int CARS_PER_LANE = 3;
    private static final int NUMBER_OF_LANES = 8;
    private static final int CAR_MIN_SPEED = 96;
    private static final int CAR_SPEED_MOD = 12;
    private boolean showHitboxes = false;
    private boolean gameOver = false;
    private Rectangle winBounds;
    private boolean isPaused;

    // constructor to keep a reference to the main Game class
    public GameScreen(FrogGame game) {
        this.game = game;
    }

    /**
     * Called when Game Screen is constructed. Initialise all variables and put game into a new game state.
     */
    public void create() {
        /** Load music files and configure BGM */
        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("level1.wav"));
        loseMusicPart1 = Gdx.audio.newMusic(Gdx.files.internal("lose1.wav"));
        bgMusic.setLooping(true);
        bgMusic.play();
        bgMusic.setVolume(0.5f);

        /** Set Win Area */
        winBounds = new Rectangle();
        winBounds.setX(0);
        winBounds.setY(Gdx.graphics.getHeight());
        winBounds.setWidth(Gdx.graphics.getWidth());
        winBounds.setHeight(-30);

        /** Configure tile map */
        streetMap = new TmxMapLoader().load("street.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(streetMap);

        /** Configure Camera */
        camera = new OrthographicCamera(800, 480);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        /** Create Frog */
        this.frog = new Frog(280, 0);

        /** Create Cars */
        this.cars = new Car[NUMBER_OF_LANES][CARS_PER_LANE];
        for (int i = 0; i < NUMBER_OF_LANES; ++i) {
            for (int j = 0; j < CARS_PER_LANE; ++j) {
                // Check to see if i is odd or even and if its even, make car travel in an easterly direction
                if (i % 2 == 0) {
                    cars[i][j] = new Car(Travelling.EAST);
                } else {
                    cars[i][j] = new Car(Travelling.WEST);
                }
            }
        }

        stateTime = 0.0f;

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        skin = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
        stage = new Stage();

        /** Draw pause button */
        pauseBtn = new TextButton("| |", skin, "default");
        pauseBtn.setWidth(40f);
        pauseBtn.setHeight(40f);
        pauseBtn.setPosition(Gdx.graphics.getWidth() / 1.0f - 30, Gdx.graphics.getHeight() / 1 - 50, Align.center);
        stage.addActor(pauseBtn);

        Gdx.input.setInputProcessor(stage);
        stage.draw();
        backToMenuBtn = new TextButton("Menu", skin, "default");
        isPaused = false;
    }

    /**
     * Render logic. Draw the frog with its animations and check the game state to see if any change from playing -> win / lose is necessary.
     * Update is called here.
     */
    public void render(float f) {

        if (!isPaused) {
            this.update();
        }

        // Pause game logic
        if(pauseBtn.isPressed()) {
            if (isPaused) {
                isPaused = false;
                this.update();
            } else {
                isPaused = true;
            }
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += Gdx.graphics.getDeltaTime();
        camera.update();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);

        stage.draw();

        // draw frog differently if it has died
        if (!frog.isDead()) {
            frog.draw(batch, stateTime);
        } else {
            frog.dead(batch);
        }

        for (int i = 0; i < NUMBER_OF_LANES; ++i) {
            for (int j = 0; j < CARS_PER_LANE; ++j) {
                cars[i][j].draw(batch);
            }
        }

//        ShapeRenderer wr = new ShapeRenderer();
//        wr.begin(ShapeRenderer.ShapeType.Line);
//        wr.setColor(Color.RED);
//        wr.setAutoShapeType(true);
//        wr.rect(winBounds.getX(), winBounds.getY(), winBounds.getWidth(), winBounds.getHeight());
//        wr.end();

        /**
         * Debugging Feature: If the 'H' key is pressed during gameplay, hitboxes are drawn.
         * This is used for fine-tuning of hitboxes as the frog is an odd shape
         */
        if (showHitboxes) {
            ShapeRenderer sr = new ShapeRenderer();
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.RED);
            sr.setAutoShapeType(true);
            for (int i = 0; i < NUMBER_OF_LANES; ++i) {
                for (int j = 0; j < CARS_PER_LANE; ++j) {
                    sr.rect(cars[i][j].getBoundingRectangle().getX(), cars[i][j].getBoundingRectangle().getY(), cars[i][j].getBoundingRectangle().getWidth(), cars[i][j].getBoundingRectangle().getHeight());
                }
            }
            sr.rect(this.frog.getBoundingRectangle().getX(), this.frog.getBoundingRectangle().getY(), this.frog.getBoundingRectangle().getWidth(), this.frog.getBoundingRectangle().getHeight());
            sr.end();
        }

        /**
         * If Game Over, draw game over screen where player can press anywhere on the screen to return to the main menu
         * The lose game music should play and when the final chord is played this screen should be drawn
         */
        if (gameOver) {
            BitmapFont font = new BitmapFont(Gdx.files.internal("good_neighbors.fnt"),
                    Gdx.files.internal("good_neighbors.png"), false);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            Texture grave = new Texture(Gdx.files.internal("grave.png"));
            Image graveImage = new Image(grave);
            graveImage.setWidth(50f);
            graveImage.setHeight(50f);
            graveImage.setX(Gdx.graphics.getWidth() / 2);
            graveImage.setY((Gdx.graphics.getHeight() / 2) - 10);
            graveImage.draw(batch, 1);

            font.setColor(Color.WHITE);
            font.draw(batch, "Game Over", (Gdx.graphics.getWidth() / 2) - 40, Gdx.graphics.getHeight() / 2);
            backToMenuBtn.setWidth(200f);
            backToMenuBtn.setHeight(35f);
            backToMenuBtn.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 200f);
            backToMenuBtn.draw(batch, 1);
            if (Gdx.input.isTouched()) {
                game.setScreen(game.menuScreen);
                this.dispose();
            }
            batch.end();
        }
    }
    @Override
    public void dispose() {

        streetMap.dispose();
        bgMusic.dispose();
        loseMusicPart1.dispose();
        loseMusicPart2.dispose();
    }

    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }
    @Override
    public void resume() { }
    @Override
    public void show() {
        create();
    }
    @Override
    public void hide() {

    }

    /**
     * Called every frame from render method. This is where game logic is carried out and checked
     */
    private void update() {

        // DEBUG: Toggle hitboxes if H is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            if (showHitboxes) {
                showHitboxes = false;
            } else {
                showHitboxes = true;
            }
        }

        switch (gameState) {
            /**
             * Loop occurs while game is in the 'playing' state. This contains the code for the frog and cars moving
             */
            case PLAYING:
                // Move frog
                if (Gdx.input.isTouched()) {
                    // y is reversed so have to getHeight - y touch
                    float maxDistanceThisFrame = this.frog.FROG_MOVEMENT_SPEED * Gdx.graphics.getDeltaTime();
                    Vector2 playerToDestination = new Vector2(Gdx.input.getX(), (Gdx.graphics.getHeight() - Gdx.input.getY())).sub(this.frog.getX(), this.frog.getY());

                    if (playerToDestination.len() <= maxDistanceThisFrame) {
                        this.frog.setX(Gdx.input.getX());
                        this.frog.setY(Gdx.input.getY());
                    } else {
                        playerToDestination.nor().scl(maxDistanceThisFrame);
                        this.frog.setX(this.frog.getX() + playerToDestination.x);
                        this.frog.setY(this.frog.getY() + playerToDestination.y);
                    }
                }

                // Move cars
                for (int i = 0; i < NUMBER_OF_LANES; ++i) {
                    for (int j = 0; j < CARS_PER_LANE; ++j) {
                        Car car = cars[i][j];
                        float carX = car.getX();
                        if (car.getDirection() == Travelling.EAST) {
                            carX = carX + (CAR_MIN_SPEED + i * CAR_SPEED_MOD) * Gdx.graphics.getDeltaTime(); // delta time will keep cars moving at a steady rate instead of ridiculously quickly
                            // If car moves off screen, wrap around
                            if (carX >= Gdx.graphics.getWidth()) {
                                carX = -car.getWidth();
                            }

                        } else {
                            carX = carX - (CAR_MIN_SPEED + i * CAR_SPEED_MOD) * Gdx.graphics.getDeltaTime();
                            if (carX <= -car.getWidth()) {
                                carX = Gdx.graphics.getWidth();
                            }
                        }
                        car.setX(carX);

                        /**
                         * Check to see if the player has lost the game by checking if the frogs hitbox has collided with a cars
                         * If it has, change the frogs sprite to it's death sprite, set the game state to 'lose' and play the lose music
                         */
                        // lose game if car touches frog
                        if (this.frog.getBoundingRectangle().overlaps(car.getBoundingRectangle())) {
                            this.frog.killFrog();
                            gameState = GameState.LOSE;
                            bgMusic.stop();
                            bgMusic.dispose();
                            loseMusicPart1.setVolume(0.5f);
                            loseMusicPart1.setLooping(false);
                            loseMusicPart1.play();
                            if (loseMusicPart1.isPlaying()) {
                                loseMusicPart2 = Gdx.audio.newMusic(Gdx.files.internal("lose2.wav"));
                                loseMusicPart2.setVolume(0.5f);
                                loseMusicPart2.setLooping(false);
                            }
                            loseMusicPart1.setOnCompletionListener(new Music.OnCompletionListener() {
                                                                       @Override
                                                                       public void onCompletion(Music music) {
                                                                           loseMusicPart2.play();
                                                                           gameOver = true;
                                                                       }
                                                                   }
                            );
                        }
                        /**
                         * If the player wins, switch to the win screen
                         */
                        // win game if frog touches win bounds
                        if (this.frog.getBoundingRectangle().overlaps(winBounds)) {
                            Gdx.app.log("GameState: ", "WIN");
                            gameState = GameState.WIN;
                            bgMusic.stop();
                            bgMusic.dispose();
                        }
                    }

                }
                break;
            /**
             * This loop contains the code for the lose condition. The cars should continue moving.
             */
            case LOSE:
//                Gdx.app.log("GameState: ", "LOSE");
                for (int i = 0; i < NUMBER_OF_LANES; ++i) {
                    for (int j = 0; j < CARS_PER_LANE; ++j) {
                        Car car = cars[i][j];
                        float carX = car.getX();
                        if (car.getDirection() == Travelling.EAST) {
                            carX = carX + (CAR_MIN_SPEED + i * CAR_SPEED_MOD) * Gdx.graphics.getDeltaTime();
                            // If car moves off screen, wrap around
                            if (carX >= Gdx.graphics.getWidth()) {
                                carX = -car.getWidth();
                            }

                        } else {
                            carX = carX - (CAR_MIN_SPEED + i * CAR_SPEED_MOD) * Gdx.graphics.getDeltaTime();
                            if (carX <= -car.getWidth()) {
                                carX = Gdx.graphics.getWidth();
                            }
                        }
                        car.setX(carX);
                    }
                }
                break;
            /**
             * This loop contains the code for the win condition.
             */
            case WIN:
                bgMusic.dispose();
                game.setScreen(game.winScreen);
                this.dispose();
                break;
        }
    }

    /**
     * Initialise the game state for a new game, set the camera to the center and organise car and frog initial positions
     */
    public void newGame() {
        gameOver = false;
        camera.position.x = Gdx.graphics.getWidth() / 2;
        camera.position.y = Gdx.graphics.getHeight() / 2;
        gameState = GameState.PLAYING;
        // to get lane width we divide the screen into equal sections, there are 8 lanes and a start and finish lane
        // so we need to add 2 to factor those in
        float laneWidth = Gdx.graphics.getHeight() / (NUMBER_OF_LANES + 2);
        for (int i = 0; i < NUMBER_OF_LANES; ++i) {
            for (int j = 0; j < CARS_PER_LANE; ++j) {
                // Put car in center of lane, -8 pixel offset so that car is centered
                cars[i][j].setY((i + 1) * laneWidth + (laneWidth - cars[i][j].getHeight()) - 8);
                if (cars[i][j].getDirection() == Travelling.EAST) {
                    // Space for 3 cars per lane
                    cars[i][j].setX(j * 3 * cars[i][j].getWidth());
                } else {
                    // Wrap around if car moves off screen
                    cars[i][j].setX(Gdx.graphics.getWidth() - j * 3 * cars[i][j].getWidth());
                }
            }
        }
    }
}
