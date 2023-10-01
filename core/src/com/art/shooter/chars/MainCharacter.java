package com.art.shooter.chars;

import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;

import com.art.shooter.logic.API;
import com.art.shooter.utils.Resources;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.art.shooter.utils.screenUtils.GridCell;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class MainCharacter extends ACharacter {
    private float velX;
    private float velY;
    private final float SPEED = 240f;
    private final float DASH_DISTANCE = 800f;
    private Vector2 direction;
    private final Vector2 muzzleOffset;
    private GridCell currCell;

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
        characterSprite.setOriginBasedPosition(originX, originY);
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

        //move back from recoil
        Vector2 backward = direction.cpy();
        backward.scl(-1); //reverse the direction
        moveWithCellUpdate(backward.x, backward.y, 3);
    }

    public void update (float delta) {
        lookAtCursor();
        handleInput(delta);
    }

    public void handleInput (float delta) {
        float deltaX = 0;
        float deltaY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            deltaY += velY * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            deltaY -= velY * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            deltaX += velX * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            deltaX -= velX * delta;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
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
        boundingBox.setPosition(pos.x + 1, pos.y - 3);
        grid.addEntityByRect(this);
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
