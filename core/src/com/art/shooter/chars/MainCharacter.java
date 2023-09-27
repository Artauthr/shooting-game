package com.art.shooter.chars;

import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;

import com.art.shooter.logic.API;
import com.art.shooter.ui.GameUI;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.art.shooter.utils.screenUtils.GridCell;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MainCharacter extends ACharacter {
    private float velX;
    private float velY;
    private final float SPEED = 240f;
    private final float DASH_DISTANCE = 800f;
    private Vector2 direction;
    private Batch batch;
    private final Vector2 muzzleOffset;

    public MainCharacter() {
        pos = new Vector2();
        direction = new Vector2();
        muzzleOffset = new Vector2();

        velX = SPEED;
        velY = SPEED;

        Texture img = new Texture("shooter.png");
        characterSprite = new Sprite(img);
        characterSprite.setScale(3f);

        final float originX = characterSprite.getWidth() / 2.0f + 0.5f;
        final float originY = characterSprite.getHeight() / 2.0f - 2;
        characterSprite.setOrigin(originX, originY);
        boundingBox.setSize(20, 22);
    }

    private float getAngle () {
        final int x = Gdx.input.getX();
        final int y = Gdx.input.getY();

        Vector3 vec3 = new Vector3(x, y, 0);

        // get the camera from the main viewport and unproject the coordinates
        Camera camera = Utils.camera;
        camera.unproject(vec3);

        float angle = (float) Math.atan2(vec3.y - pos.y, vec3.x - pos.x);

        return MathUtils.radiansToDegrees * angle - 90;
    }


    private Vector2 getDirection () {
        Vector3 cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Camera camera = Utils.camera;
        camera.unproject(cursorPos);

        Vector2 direction = new Vector2(cursorPos.x - pos.x, cursorPos.y - pos.y);

        direction.nor();  // normalize the vector
        return direction;
    }

    private void lookAtCursor (float delta) {
        characterSprite.setRotation(getAngle());
    }

    private void shoot() {
        final Vector2 direction = getDirection();
        EntitySystem entitySystem = API.get(EntitySystem.class);
        BulletEntity entity = entitySystem.createEntity(BulletEntity.class);

        final float angle = getAngle();

        muzzleOffset.set(4, 30);
        muzzleOffset.rotateDeg(angle);
        final float muzzleX = pos.x + characterSprite.getOriginX() + muzzleOffset.x;
        final float muzzleY = pos.y + characterSprite.getOriginY() + muzzleOffset.y;

        final Vector2 bulletPos = new Vector2(muzzleX, muzzleY);
        entity.setPos(bulletPos);
        entity.setDirection(direction);
        entity.setRotation(angle);
        entity.setDamage(1);
    }

    public void update (float delta) {
        lookAtCursor(delta);
        handleInput(delta);
    }

    public void handleInput (float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (pos.y + velY * delta < Utils.camera.viewportHeight) {
                final Grid grid = API.get(Grid.class);
//                grid.removeEntityFromCell(this);
                pos.y += velY * delta;
//                grid.addEntityToCell(this);
            } else {
                pos.y = Utils.camera.viewportHeight;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (pos.y - velY * delta >= 0) {
                final Grid grid = API.get(Grid.class);
//                grid.removeEntityFromCell(this);
                pos.y -= velY * delta;
//                grid.addEntityToCell(this);
            } else {
                pos.y = 0;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (pos.x + velX * delta < Utils.camera.viewportWidth) {
                final Grid grid = API.get(Grid.class);
//                grid.removeEntityFromCell(this);
                pos.x += velX * delta;
//                grid.addEntityToCell(this);
            } else {
                pos.x = Utils.camera.viewportWidth;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (pos.x - velX * delta >= 0) {
                final Grid grid = API.get(Grid.class);
//                grid.removeEntityFromCell(this);
                pos.x -= velX * delta;
//                grid.addEntityToCell(this);
            } else {
                pos.x = 0;
            }
        }
        final Grid grid = API.get(Grid.class);
        grid.removeEntityFromCellV2(this);
        boundingBox.setPosition(pos.x + 1, pos.y - 3);
        grid.addEntityToCellV2(this);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            shoot();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            dash(getDirection(), delta);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            System.out.println("pos.toString() = " + pos.toString());
            System.out.println("colliderBOX X= " + colliderCircle.x);
            System.out.println("colliderBOX Y= " + colliderCircle.y);
        }
    }

    private void dash (Vector2 direction, float delta) {
        pos.x += direction.x * DASH_DISTANCE * delta;
        pos.y += direction.y * DASH_DISTANCE * delta;

    }

    @Override
    public void remove() {
        Array<GridCell> cellsAtRect = API.get(Grid.class).getCellsAtRect(boundingBox);
        for (GridCell gridCell : cellsAtRect) {
            gridCell.remove(this);
        }
        isFlaggedToRemove = true;
    }

    public void draw (Batch batch) {
        characterSprite.setPosition(pos.x, pos.y);
        characterSprite.draw(batch);
    }

    @Override
    public void dispose() {
        characterSprite.getTexture().dispose();
    }
}
