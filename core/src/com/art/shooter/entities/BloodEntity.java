package com.art.shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import lombok.Setter;

public class BloodEntity extends ASimpleEntity {
    private Sprite bloodSprite;
    private final float lifeTime = 4f;
    private float timer = 0f;
    private float alphaModulation = 1f;

    @Setter
    private float rotation;

    public BloodEntity () {
        Texture bloodTexture = new Texture(Gdx.files.internal("blood_1.png"));
        bloodSprite = new Sprite(bloodTexture);
        bloodSprite.setSize(24,24);
    }

    @Override
    protected void update(float delta) {
        timer += delta;
        alphaModulation -= delta / lifeTime;
        if (timer > lifeTime) remove();
    }

    @Override
    protected void remove() {
        setFlaggedToRemove(true);
    }

    @Override
    protected void create() {

    }

    @Override
    protected void draw(Batch batch) {
        bloodSprite.setPosition(pos.x, pos.y);
        bloodSprite.setRotation(rotation);
        bloodSprite.draw(batch, alphaModulation);
    }

    @Override
    protected void dispose() {
        bloodSprite.getTexture().dispose();
    }

    @Override
    public void reset() {
        timer = 0;
        alphaModulation = 1f;
        setFlaggedToRemove(false);
    }
}
