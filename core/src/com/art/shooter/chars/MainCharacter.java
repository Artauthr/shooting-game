package com.art.shooter.chars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class MainCharacter extends ADrawablePerson {
    private float velX;
    private float velY;
    private float posX;
    private float posY;
    private Sprite characterSprite;
    private final float SPEED = 120f;

    private enum State {
        IDLE,
        MOVING,
        SHOOTING;
    }

    public MainCharacter() {
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
        float angle = (float) Math.atan2(y - posY, x - posX);
        float angleDegrees = MathUtils.radiansToDegrees * angle - 90;
        characterSprite.setRotation(angleDegrees);
    }

    private void shoot () {

    }

    public void update () {
        lookAtCursor();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            posY += velY * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            posY -= velY * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            posX += velX * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            posX -= velX * Gdx.graphics.getDeltaTime();
        }

    }

    public void draw (Batch batch) {
        update();
        characterSprite.setPosition(posX, posY);
        characterSprite.draw(batch);
    }

}
