package com.art.shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class BulletEntity implements Pool.Poolable {
    private Sprite bulletSprite;
    private float speed = 200f;
    private Vector2 targetPos;
    private Vector2 direction;
    private Vector2 pos;
    private boolean flaggedToRemove = false;

    public BulletEntity(Vector2 sourcePos, Vector2 targetPos) {
        this.pos = new Vector2(sourcePos);
        this.direction = new Vector2(targetPos).sub(sourcePos).nor();

        Texture bullet = new Texture("bullet.png");
        this.bulletSprite = new Sprite(bullet);

    }

    public void setPos (float x, float y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    private void update () {
        float deltaTime = Gdx.graphics.getDeltaTime();
        Vector2 velocity = new Vector2(direction).scl(speed * deltaTime);
        pos.add(velocity);
    }

    public void draw (Batch batch) {
        update();
        bulletSprite.setPosition(pos.x, pos.y);
        bulletSprite.draw(batch);
    }

    public boolean checkOutOfBounds () {
        if (this.pos.x > Gdx.graphics.getWidth() || this.pos.x < 0) {
            flaggedToRemove = true;
        }
        // TODO: 8/31/2023 later
        return false;
    }

    @Override
    public void reset() {
        pos.setZero();
        targetPos.setZero();
    }
}
