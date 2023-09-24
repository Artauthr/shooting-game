package com.art.shooter.entities;

import com.art.shooter.chars.ACharacter;
import com.art.shooter.logic.API;
import com.art.shooter.logic.GameObject;
import com.art.shooter.utils.CollisionDetector;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.art.shooter.utils.screenUtils.GridCell;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

public class BulletEntity extends ASimpleEntity {
    private Sprite bulletSprite;
    @Setter
    private float rotation;
    @Getter @Setter
    private Vector2 pos;
    @Setter
    private Vector2 direction;
    private final float speed = 800f;
    private final float lifeTime = 4f;
    private float timer = 0f;
    private GridCell currCell;

    public BulletEntity () {
        Texture bullet = new Texture("bullet.png");
        bulletSprite  = new Sprite(bullet);
        bulletSprite.setSize(2, 13);

        final float originX = bulletSprite.getWidth() / 2.0f;
        final float originY = 0;
        bulletSprite.setOrigin(originX, originY);
    }


    @Override
    protected void create() {

    }

    @Override
    protected void draw (Batch batch) {
        bulletSprite.setPosition(pos.x, pos.y);
        bulletSprite.setRotation(rotation);
        bulletSprite.draw(batch);
    }

    @Override
    protected void update(float delta) {
        timer += delta;
        if (timer > lifeTime) {
            remove();
            return;
        }
        final Grid grid = API.get(Grid.class);
        grid.removeEntityFromCell(this);
        pos.x += direction.x * speed * delta;
        pos.y += direction.y * speed * delta;

        if (pos.x > Utils.camera.viewportWidth || pos.x < 0) {
            setFlaggedToRemove(true);
            return;
        }
        if (pos.y > Utils.camera.viewportHeight || pos.y < 0) {
            setFlaggedToRemove(true);
            return;
        }
        this.currCell = grid.addEntityToCell(this);
    }

    public void checkForCollision () {
        if (this.currCell != null) {
            Array<GameObject> gameObjects = currCell.getGameObjects();
            for (GameObject gameObject : gameObjects) {
                if (gameObject == this) continue;
                boolean hit = CollisionDetector.bulletToCharacter(this, (ACharacter) gameObject);
                if (hit) {
                    System.out.println("another one bites a dust");
                }
            }
        }
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
