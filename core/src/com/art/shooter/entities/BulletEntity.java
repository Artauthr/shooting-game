package com.art.shooter.entities;

import com.art.shooter.chars.ACharacter;
import com.art.shooter.logic.API;
import com.art.shooter.logic.GameObject;
import com.art.shooter.map.Wall;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.art.shooter.utils.screenUtils.GridCell;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
import lombok.Setter;

public class BulletEntity extends ASimpleEntity {
    private Sprite bulletSprite;

    @Setter
    private float rotation;

    @Setter
    private Vector2 direction;
    private final float speed = 300f;
    private final float lifeTime = 4f;
    private float timer = 0f;
    private GridCell currCell;

    @Setter
    private float damage;

    @Getter
    private Polygon collider;

    public BulletEntity () {
        Texture bullet = new Texture("bullet.png");
        bulletSprite  = new Sprite(bullet);
        bulletSprite.setSize(2, 13);

        collider = new Polygon(new float[]{0,0,bulletSprite.getWidth(),0,bulletSprite.getWidth(),bulletSprite.getHeight(),0,bulletSprite.getHeight()});
        collider.setOrigin(bulletSprite.getWidth() / 2, bulletSprite.getHeight() / 2);

    }


    @Override
    protected void create() {

    }

    @Override
    protected void draw (Batch batch) {
        bulletSprite.draw(batch);
    }

    @Override
    protected void update(float delta) {
        collider.setPosition(pos.x, pos.y);
        collider.setRotation(rotation);
        bulletSprite.setPosition(pos.x, pos.y);
        bulletSprite.setRotation(rotation);
        timer += delta;
        if (timer > lifeTime) {
            remove();
            return;
        }
        final Grid grid = API.get(Grid.class);
        grid.removeEntityByPosition(this);
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
        this.currCell = grid.addEntityByPosition(this);
        checkForCollision(delta);
    }

    public void checkForCollision (float delta) {
        if (this.currCell != null) {
            Array<GameObject> gameObjects = currCell.getGameObjects();
            for (GameObject gameObject : gameObjects) {
                boolean hit = Intersector.overlapConvexPolygons(collider, gameObject.getCollider());
                if (hit) {
                    if (gameObject instanceof ACharacter) {
                        this.remove();
                        ((ACharacter) gameObject).onHit(delta, direction, this.damage);
                        return;
                    } else if (gameObject instanceof Wall) {
                        System.out.println("BOUNCE BACK AAA I HIT THE WALL AA YEE WW");
                        return;
                    }
                }
            }
        }
    }

//    private void bounceBack (Polygon bulletCollider, Rectangle wallRect) {
//            if (Math.abs(bulletCollider.x - wallRect.x) < bulletCollider.radius ||
//                    Math.abs(bulletCollider.x - (wallRect.x + wallRect.width)) < bulletCollider.radius) {
//                direction.x = -direction.x;
//                rotation = -rotation;
//            }
//            // Closer to horizontal walls
//            if (Math.abs(bulletCollider.y - wallRect.y) < bulletCollider.radius ||
//                    Math.abs(bulletCollider.y - (wallRect.y + wallRect.height)) < bulletCollider.radius) {
//                direction.y = -direction.y;
//                rotation = -rotation;
//            }
//    }
    @Override
    protected void remove() {
        API.get(Grid.class).getCellAt(pos).remove(this);
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
