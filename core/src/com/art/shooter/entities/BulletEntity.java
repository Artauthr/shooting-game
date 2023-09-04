package com.art.shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import lombok.Setter;

public class BulletEntity extends ASimpleEntity {
    private Sprite bulletSprite;
    @Setter
    private float rotation;
    @Setter
    private Vector2 pos;
    @Setter
    private Vector2 direction;
    private final float speed = 800f;
    private final float lifeTime = 4f;
    private float timer = 0f;

    public BulletEntity () {
        Texture bullet = new Texture("bullet.png");
        bulletSprite  = new Sprite(bullet);
        bulletSprite.setSize(2, 7);

        final float originX = bulletSprite.getWidth() / 2.0f;
        final float originY = 0;
        bulletSprite.setOrigin(originX, originY);
    }


    @Override
    protected void create() {

    }

    @Override
    protected void draw (Batch batch, float delta) {
        update(delta);
        bulletSprite.setPosition(pos.x, pos.y);
        bulletSprite.setRotation(rotation);
        bulletSprite.draw(batch);
    }

    @Override
    protected void update(float delta) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        timer += deltaTime;
        if (timer > lifeTime) {
            remove();
        }
        pos.x += direction.x * speed * deltaTime;
        pos.y += direction.y * speed * deltaTime;
    }

    @Override
    protected void remove() {
        setFlaggedToRemove(true);
    }

    @Override
    public void reset() {
        timer = 0f;
        setFlaggedToRemove(false);
    }

    @Override
    protected void dispose() {
        bulletSprite.getTexture().dispose();
    }
}
