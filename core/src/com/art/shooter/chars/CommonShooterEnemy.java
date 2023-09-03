package com.art.shooter.chars;

import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

public class CommonShooterEnemy extends AEnemy {
    private float shootCooldownTimer;
    @Getter
    private Rectangle boundingBox;
    private final float speed = 100f;
    private final float optimalDistance = 120f; //desired distance from player
    private final float bBoxMul = 1.2f;

    public CommonShooterEnemy() {
        Texture img = new Texture("enemy.png");
        characterSprite = new Sprite(img);
        characterSprite.setScale(3f);
        characterSprite.setOriginCenter();

        hp = 100;
        damage = 10;
        this.boundingBox = new Rectangle();
    }

    private void simulate (float delta) {
        lookAtMainCharacter();
        move(delta);
        shootAtMainCharacter(delta);

    }

    private void move (float delta) {
        MainCharacter mainCharacter = CharacterManager.getInstance().getMainCharacter();
        float distance = Utils.getDistanceBetweenTwoVectors(mainCharacter.getPos(), this.pos);
        Vector2 direction = getDirection();

        if (distance > optimalDistance) {
            //get up close and personal
            pos.x += direction.x * speed * delta;
            pos.y += direction.y * speed * delta;
        } else {
            //create some distance
            pos.x -= direction.x * speed * delta;
            pos.y -= direction.y * speed * delta;
        }
    }

    private void shootAtMainCharacter (float delta) {
        final Vector2 direction = getDirection();
        EntitySystem entitySystem = EntitySystem.getInstance();

        shootCooldownTimer += delta;

        if (shootCooldownTimer > 3f) {
            BulletEntity entity = entitySystem.createEntity(BulletEntity.class);
            Vector2 vec2 = new Vector2(pos);
            entity.setPos(vec2);
            entity.setDirection(direction);
            entity.setRotation(getMainCharAngle());
            shootCooldownTimer = 0f;
        }

    }

    private void lookAtMainCharacter () {
        float v = getMainCharAngle();
        characterSprite.setRotation(v);
    }

    private float getMainCharAngle () {
        CharacterManager charManager = CharacterManager.getInstance();
        MainCharacter mainCharacter = charManager.getMainCharacter();

        final float x = mainCharacter.getPos().x;
        final float y = mainCharacter.getPos().y;
        float angle = (float) Math.atan2(y - pos.y, x - pos.x);
        return MathUtils.radiansToDegrees * angle - 90;
    }

    private Vector2 getDirection () {
        CharacterManager charManager = CharacterManager.getInstance();
        MainCharacter mainCharacter = charManager.getMainCharacter();
        Vector2 mainCharPos = new Vector2(mainCharacter.getPos().x, mainCharacter.getPos().y);
        Vector2 direction = new Vector2(mainCharPos.x - pos.x, mainCharPos.y - pos.y);
        direction.nor();  // Normalize the vector
        return direction;
    }

    @Override
    public void update(float delta) {
        simulate(delta);
    }

    @Override
    public void draw(Batch batch, float delta) {
        characterSprite.setPosition(pos.x, pos.y);
        float width = characterSprite.getWidth();
        float height = characterSprite.getHeight();
        float x = characterSprite.getX();
        float y = characterSprite.getY();

        boundingBox.set(x, y, width * bBoxMul, height * bBoxMul);
        characterSprite.draw(batch);
        simulate(delta);
    }


    @Override
    public void remove() {
        setFlaggedToRemove(true);
    }

    @Override
    public void reset() {
        hp = 100f;
        characterSprite.setRotation(0);
        shootCooldownTimer = 0;
    }

    @Override
    public void dispose() {
        characterSprite.getTexture().dispose();
    }
}
