package com.art.shooter.chars;

import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;

import com.art.shooter.logic.API;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.art.shooter.utils.screenUtils.GridCell;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedSet;

public class MainCharacter extends ACharacter {
    private float speed = 240f;
    private Vector2 direction;
    private final Vector2 muzzleOffset;

    public MainCharacter() {
        pos = new Vector2();
        direction = new Vector2();
        muzzleOffset = new Vector2();

        Texture img = new Texture("shooter.png");
        characterSprite = new Sprite(img);
        characterSprite.setScale(3f);

        float spriteWidth = characterSprite.getWidth();
        final float originX = spriteWidth / 2.0f + 0.5f;
        float spriteHeight = characterSprite.getHeight();
        final float originY = spriteHeight / 2.0f - 3;
        characterSprite.setOrigin(originX, originY);
        float colliderMul = 1.25f;

        collider = new Polygon(new float[]{0,0, spriteWidth * colliderMul,0, spriteWidth * colliderMul, spriteHeight * colliderMul, 0, spriteHeight * colliderMul});
        collider.setOrigin(originX, originY);
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

    private void lookAtCursor () {
        float angle = getAngle();
        characterSprite.setRotation(angle);
        API.get(Grid.class).removeEntityByRect(this);
        collider.setRotation(angle);
        API.get(Grid.class).addEntityByRect(this);
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

        //move back from recoil
//        Vector2 backward = direction.cpy();
//        backward.scl(-1); //reverse the direction
//        moveWithCellUpdate(backward.x, backward.y, 3);
    }

    public void update (float delta) {
        lookAtCursor();
        handleInput(delta);
    }

    public void handleInput (float delta) {
        float deltaX = 0;
        float deltaY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            deltaY += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            deltaY -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            deltaX += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            deltaX -= speed * delta;
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            shoot();
        }
        moveWithCellUpdate(deltaX, deltaY);
    }

    private void moveWithCellUpdate (float deltaX, float deltaY) {
        moveWithCellUpdate(deltaX, deltaY, 1);
    }

    private void moveWithCellUpdate (float deltaX, float deltaY, float multiplier) {
        final Grid grid = API.get(Grid.class);

        if (pos.x + deltaX < 0) deltaX = 0;
        if (pos.x + deltaX > Utils.camera.viewportWidth) deltaX = 0;
        if (pos.y + deltaY < 0) deltaY = 0;
        if (pos.y + deltaY > Utils.camera.viewportHeight) deltaY = 0;

        grid.removeEntityByRect(this);
        pos.add(deltaX * multiplier, deltaY * multiplier);
        characterSprite.setPosition(pos.x, pos.y);
        collider.setPosition(pos.x, pos.y);
        grid.addEntityByRect(this);
    }

    @Override
    public void remove() {
        OrderedSet<GridCell> cellsAtRect = API.get(Grid.class).getCellsAt(this);
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
