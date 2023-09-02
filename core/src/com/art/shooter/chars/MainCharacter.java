package com.art.shooter.chars;

import com.art.shooter.entities.ASimpleEntity;
import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;

public class MainCharacter extends ADrawablePerson {
    private float velX;
    private float velY;
    private final float SPEED = 120f;
    private Vector2 direction;
    private Batch batch;

    public MainCharacter() {
        pos = new Vector2();
        direction = new Vector2();
        velX = SPEED;
        velY = SPEED;

        Texture img = new Texture("shooter.png");
        characterSprite = new Sprite(img);
        characterSprite.setScale(3f);
        characterSprite.setOriginCenter();
    }

    private float getAngle () {
        final int x = Gdx.input.getX();
        final int y = Gdx.graphics.getHeight() - Gdx.input.getY();
        float angle = (float) Math.atan2(y - pos.y, x - pos.x);
        return MathUtils.radiansToDegrees * angle - 90;
    }

    private Vector2 getDirection () {
        Vector2 cursorPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        Vector2 direction = new Vector2(cursorPos.x - pos.x, cursorPos.y - pos.y);
        direction.nor();  // Normalize the vector
        return direction;
    }

    private void lookAtCursor () {
        characterSprite.setRotation(getAngle());
    }

    private void shoot () {
        final Vector2 direction = getDirection();
        EntitySystem entitySystem = EntitySystem.getInstance();
        BulletEntity entity = entitySystem.createEntity(BulletEntity.class);
        Vector2 vec2 = new Vector2(pos);
        entity.setPos(vec2);
        entity.setDirection(direction);
        entity.setRotation(getAngle());
    }

    public void update (float delta) {
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

    @Override
    public void remove() {
        isFlaggedToRemove = true;
    }

    public void draw (Batch batch, float delta) {
        if (this.batch == null) {
            this.batch = batch;
        }
        update(delta);
        characterSprite.setPosition(pos.x, pos.y);
        characterSprite.draw(batch);
    }

}
