package com.art.shooter.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BulletEntity {
    private Sprite bulletSprite;
    private float velocity;

    public BulletEntity() {
        Texture bullet = new Texture("bullet.png");
        bulletSprite = new Sprite(bullet);
    }

    public void draw (Batch batch) {
        bulletSprite.draw(batch);
    }
}
