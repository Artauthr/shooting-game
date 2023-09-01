package com.art.shooter.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

public class BulletEntity extends ASimpleEntity {
    private Image image;
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
        image = new Image(bullet);
        image.setSize(10, 100);
    }


    @Override
    protected void create() {

    }

    @Override
    protected void draw (Batch batch, float delta) {
        update(delta);
        image.setPosition(pos.x, pos.y);
        image.setRotation(rotation);
        image.draw(batch, delta);
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
        flaggedToRemove = true;
    }

    @Override
    public void reset() {
        timer = 0f;
        flaggedToRemove = false;
    }
}
