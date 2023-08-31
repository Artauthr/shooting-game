package com.art.shooter.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class BulletEntity implements Pool.Poolable {
    private Sprite bulletSprite;
    private float velocity;
    private Vector2 targetPos;
    private Vector2 pos;

    public BulletEntity() {
        pos = new Vector2();
        Texture bullet = new Texture("bullet.png");
        bulletSprite = new Sprite(bullet);
    }

    public void setPos (float x, float y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    private void update () {
        
    }

    public void draw (Batch batch) {
        update();
        bulletSprite.draw(batch);
    }

    @Override
    public void reset() {
        pos.setZero();
        targetPos.setZero();
    }
}
