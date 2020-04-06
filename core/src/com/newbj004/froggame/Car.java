package com.newbj004.froggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

public class Car {
    private Texture carTexture;
    private float x, y;
    private Travelling direction;

    public Car(Travelling direction) {
        this.direction = direction;
        if (direction == Travelling.EAST) {
            carTexture = new Texture(Gdx.files.internal("car_right.png"));
        } else {
            carTexture = new Texture(Gdx.files.internal("car_left.png"));
        }
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(carTexture, x, y);
        batch.end();
    }

    public float getHeight() {
        return carTexture.getHeight();
    }

    public float getWidth() {
        return carTexture.getWidth();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float newX) {
        this.x = newX;
    }

    public void setY(float newY) {
        this.y = newY;
    }

    public Travelling getDirection() {
        return this.direction;
    }

    public Rectangle getBoundingRectangle() {
        Sprite s = new Sprite(carTexture);
        return s.getBoundingRectangle();
    }


}
