package com.newbj004.froggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Frog {
    private Animation frogWalkAnimation;
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 2;
    private Texture frogSheet;
    private TextureRegion[] frogWalkFrames;
    public static final float FROG_MOVEMENT_SPEED = 128.0f;
    private float x, y;

    public Frog(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        frogSheet = new Texture(Gdx.files.internal("froggy.png"));

        TextureRegion[][] temp = TextureRegion.split(frogSheet, frogSheet.getWidth() / FRAME_COLS, frogSheet.getHeight() / FRAME_ROWS);
        frogWalkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; ++i) {
            for (int j = 0; j < FRAME_COLS; ++j) {
                frogWalkFrames[index++] = temp[i][j];
            }
        }

        frogWalkAnimation = new Animation<TextureRegion>(0.033f, frogWalkFrames);
    }

    public TextureRegion render(float stateTime) {
        return (TextureRegion) frogWalkAnimation.getKeyFrame(stateTime, true);
    }

    public Rectangle getBoundingRectangle() {
        Sprite s = new Sprite();
        s.setRegion(frogWalkFrames[0]);
        return s.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch, float stateTime) {
        batch.begin();
        batch.draw(render(stateTime), this.getX(), this.getY());
        batch.end();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void setX(float newX) {
        this.x = newX;
    }

    public void setY(float newY) {
        this.y = newY;
    }


}
