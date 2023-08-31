package com.art.shooter.chars;

import com.art.shooter.entities.BulletEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MainCharacter extends ADrawablePerson {
    private float velX;
    private float velY;
    private Sprite characterSprite;
    private final float SPEED = 120f;

    private enum State {
        IDLE,
        MOVING,
        SHOOTING;
    }

    public MainCharacter() {
        pos = new Vector2();
        velX = SPEED;
        velY = SPEED;
        Texture img = new Texture("shooter.png");
        characterSprite = new Sprite(img);
        characterSprite.setScale(3f);
        characterSprite.setOriginCenter();
    }

    private void lookAtCursor () {
        final int x = Gdx.input.getX();
        final int y = Gdx.graphics.getHeight() - Gdx.input.getY();
        float angle = (float) Math.atan2(y - pos.y, x - pos.x);
        float angleDegrees = MathUtils.radiansToDegrees * angle - 90;
        characterSprite.setRotation(angleDegrees);
    }

    private void shoot () {

    }

    public void update () {
        lookAtCursor();
        handleInput();
    }

    public void handleInput () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            pos.y += velY * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            pos.y -= velY * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += velX * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= velX * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            shoot();
        }
    }


    public void draw (Batch batch) {
        update();
        characterSprite.setPosition(pos.x, pos.y);
        characterSprite.draw(batch);
    }

    public Vector2 getPosition () {
        return this.pos;
    }

}
