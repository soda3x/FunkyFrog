package com.newbj004.froggame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Car class encapsulating calls unique to the frog to increase readability
 */
public class Car {
    private Texture carTexture;
    private float x, y;
    private Travelling direction;

    /**
     * Construct Car, configure direction it is travelling in
     * @param direction
     */
    public Car(Travelling direction) {
        this.direction = direction;
        if (direction == Travelling.EAST) {
            carTexture = new Texture(Gdx.files.internal("car_right.png"));
        } else {
            carTexture = new Texture(Gdx.files.internal("car_left.png"));
        }
    }

    /**
     * The Car's 'Draw' method is a helper method to easily draw with the sprite batch used in game screen
     */
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

    /**
     * getBoundingRectangle() returns the Car's hitbox so collision logic can be calculated
     * @return Car's Hitbox
     */
    public Rectangle getBoundingRectangle() {
        Sprite s = new Sprite(carTexture);
        return new Rectangle(this.getX(), this.getY(), s.getWidth(), s.getHeight());
    }


}
