package com.newbj004.froggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import javax.xml.soap.Text;

public class Frog {
    private Animation frogWalkAnimation;
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 2;
    private Texture frogSheet;
    private TextureRegion[] frogWalkFrames;
    public static final float FROG_MOVEMENT_SPEED = 128.0f;
    private float x, y;
    private boolean dead = false;

    public Frog(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.dead = false;
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
        TextureRegion[][] temp = TextureRegion.split(frogSheet, frogSheet.getWidth() / FRAME_COLS, frogSheet.getHeight() / FRAME_ROWS);
        Sprite s = new Sprite(temp[0][0]);
        return new Rectangle(this.getX() + 14, this.getY() + 14, s.getWidth() / 2.5f, s.getHeight() / 2.5f);
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

    public boolean isDead() {
        return this.dead;
    }

    public void killFrog() {
        this.dead = true;
    }

    public void dead(SpriteBatch batch) {
        frogSheet = new Texture(Gdx.files.internal("splat.png"));
        batch.begin();
        batch.draw(frogSheet, this.getX(), this.getY());
        batch.end();
    }


}
