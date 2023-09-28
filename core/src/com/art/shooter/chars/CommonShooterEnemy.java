package com.art.shooter.chars;

import com.art.shooter.entities.BloodEntity;
import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CommonShooterEnemy extends AEnemy {
    private float shootCooldownTimer;
    private final float speed = 100f;
    private final float optimalDistance = 220f; //desired distance from player
    private final float bBoxMul = 1.2f;

    public CommonShooterEnemy() {
        Texture img = new Texture("enemy.png");
        characterSprite = new Sprite(img);
        characterSprite.setScale(3f);

        final float originX = characterSprite.getWidth() / 2.0f;
        final float originY = characterSprite.getHeight() / 2.0f;
        characterSprite.setOrigin(originX, originY);

        hp = 100;
        damage = 10;

        boundingBox.setSize(20, 22);
    }

    private void simulate (float delta) {
        lookAtMainCharacter();
        move(delta);
//        shootAtMainCharacter(delta);
    }

    private void move (float delta) {
        CharacterManager characterManager = API.get(CharacterManager.class);
        MainCharacter mainCharacter = characterManager.getMainCharacter();
        float distance = Utils.getDistanceBetweenTwoVectors(mainCharacter.getPos(), this.pos);
        Vector2 direction = getDirection();

        Grid grid = API.get(Grid.class);

        if (distance > optimalDistance) {
            //get up close and personal
            pos.x += direction.x * speed * delta;
            pos.y += direction.y * speed * delta;

        } else {
            //create some distance
            pos.x -= direction.x * speed * delta;
            pos.y -= direction.y * speed * delta;
        }
        grid.removeEntityByRect(this);
        boundingBox.setPosition(pos.x + 1, pos.y - 3);
        grid.addEntityByRect(this);
    }

    private void shootAtMainCharacter (float delta) {
        final Vector2 direction = getDirection();
        EntitySystem entitySystem = API.get(EntitySystem.class);

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
        CharacterManager charManager = API.get(CharacterManager.class);
        MainCharacter mainCharacter = charManager.getMainCharacter();

        final float x = mainCharacter.getPos().x;
        final float y = mainCharacter.getPos().y;
        float angle = (float) Math.atan2(y - pos.y, x - pos.x);
        return MathUtils.radiansToDegrees * angle - 90;
    }

    private Vector2 getDirection () {
        CharacterManager charManager = API.get(CharacterManager.class);
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
    public void draw(Batch batch) {
        characterSprite.setPosition(pos.x, pos.y);
        characterSprite.draw(batch);
    }

    @Override
    public void onHit(Vector2 direction, float damage) {
        hp -= damage;
        if (hp < 0) {
            remove();
            return;
        }
        BloodEntity bloodEntity = API.get(EntitySystem.class).createEntity(BloodEntity.class);
        Vector2 randomisedPos = this.pos.cpy();
        randomisedPos.add(0, MathUtils.random(-10, 10));
        bloodEntity.setPos(randomisedPos);
        final float knockBackSpeed = 800f;
        API.get(Grid.class).removeEntityByRect(this);
        pos.x += direction.x * knockBackSpeed * Gdx.graphics.getDeltaTime();
        pos.y += direction.y * knockBackSpeed * Gdx.graphics.getDeltaTime();
        API.get(Grid.class).addEntityByRect(this);
    }

    @Override
    public void remove() {
        setFlaggedToRemove(true);
        API.get(Grid.class).removeEntityByRect(this);
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
