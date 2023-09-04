package com.art.shooter.chars;

import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.utils.CollisionDetector;
import com.art.shooter.utils.Ray2D;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;

public class MainCharacter extends ADrawablePerson {
    private float velX;
    private float velY;
    private final float SPEED = 120f;
    private final float DASH_DISTANCE = 800f;
    private Vector2 direction;
    private Batch batch;
    private Ray2D tmpRay;
    private final Vector2 muzzleOffset;

    private ShapeRenderer shapeRenderer;

    public MainCharacter() {
        pos = new Vector2();
        direction = new Vector2();
        muzzleOffset = new Vector2();

        velX = SPEED;
        velY = SPEED;

        Texture img = new Texture("shooter.png");
        characterSprite = new Sprite(img);
        characterSprite.setScale(3f);

        final float originX = characterSprite.getWidth() / 2.0f;
        final float originY = characterSprite.getHeight() / 2.0f - 4;
        characterSprite.setOrigin(originX, originY);

        shapeRenderer = new ShapeRenderer();
    }

    private float getAngle () {
        final int x = Gdx.input.getX();
        final int y = Gdx.input.getY();

        Vector3 vec3 = new Vector3(x, y, 0);

        // get the camera from the main viewport and unproject the coordinates
        Camera camera = Utils.mainViewport.getCamera();
        camera.unproject(vec3);

        float angle = (float) Math.atan2(vec3.y - pos.y, vec3.x - pos.x);

        return MathUtils.radiansToDegrees * angle - 90;
    }


    private Vector2 getDirection () {
        Vector3 cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Camera camera = Utils.mainViewport.getCamera();
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
        EntitySystem entitySystem = EntitySystem.getInstance();
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
    }

    public void update (float delta) {
        lookAtCursor();
        handleInput();
    }

    public void handleInput () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            pos.y += velY * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            pos.y -= velY * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            pos.x += velX * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            pos.x -= velX * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            shoot();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            dash(getDirection(), Gdx.graphics.getDeltaTime());
        }
    }

    private void dash (Vector2 direction, float delta) {
        pos.x += direction.x * DASH_DISTANCE * delta;
        pos.y += direction.y * DASH_DISTANCE * delta;

    }

    @Override
    public void remove() {
        isFlaggedToRemove = true;
    }

    public void draw (Batch batch, float delta) {
        if (this.batch == null) {
            this.batch = batch;
        }
        update(delta);
        characterSprite.setPosition(pos.x, pos.y);
        characterSprite.draw(batch);

        // Draw origin as a debug point
        batch.end(); // End the batch to switch to immediate mode rendering
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix()); // Set projection matrix
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        final float originX = pos.x + characterSprite.getOriginX();
        final float originY = pos.y + characterSprite.getOriginY();
        shapeRenderer.circle(originX, originY, 1); // Increase the circle size for better visibility
        shapeRenderer.end();
        batch.begin(); // Begin the batch again to continue rendering other objects


        // Draw origin as a debug point
        batch.end(); // End the batch to switch to immediate mode rendering
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix()); // Set projection matrix
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        muzzleOffset.set(4, 30);
        muzzleOffset.rotateDeg(getAngle());
        final float muzzleX = pos.x + characterSprite.getOriginX() + muzzleOffset.x;
        final float muzzleY = pos.y + characterSprite.getOriginY() + muzzleOffset.y;

        shapeRenderer.circle(muzzleX, muzzleY, 1); // Increase the circle size for better visibility
        shapeRenderer.end();
        batch.begin(); // Begin the batch again to continue rendering other objects
    }

    @Override
    public void dispose() {
        characterSprite.getTexture().dispose();
        shapeRenderer.dispose();
    }
}
