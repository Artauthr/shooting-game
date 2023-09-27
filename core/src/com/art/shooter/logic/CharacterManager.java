package com.art.shooter.logic;


import com.art.shooter.chars.ACharacter;
import com.art.shooter.chars.AEnemy;
import com.art.shooter.chars.CommonShooterEnemy;
import com.art.shooter.chars.MainCharacter;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;
import lombok.Getter;

public class CharacterManager implements Disposable {
    /*
    this class manages the main player and enemies/NPC-s
    */
    @Getter
    private MainCharacter mainCharacter; //there should be only 1 mainChar
    @Getter
    private Array<ACharacter> characters;

    public CharacterManager () {
        this.characters = new Array<>();
    }

    public void updateCharacters (float delta) {
        Array.ArrayIterator<ACharacter> iterator = characters.iterator();
        while (iterator.hasNext()) {
            ACharacter next = iterator.next();
            if (next.isFlaggedToRemove()) {
                Pools.free(next);
                iterator.remove();
            }
        }
        for (ACharacter character : characters) {
            character.update(delta);
        }
    }

    public void drawCharacters (PolygonSpriteBatch batch) {
        for (ACharacter character : characters) {
            final Sprite sprite = character.getCharacterSprite();
            final Vector2 pos = character.getPos();
            sprite.setPosition(pos.x, pos.y);
            sprite.draw(batch);
        }
    }

    public <T extends ACharacter> T createCharacter(Class<T> clazz) {
        T character = Pools.obtain(clazz);
        characters.add(character);
        if (character instanceof MainCharacter) {
            mainCharacter = (MainCharacter) character;
        }
        return character;
    }

    public <T extends AEnemy> T spawnEnemy (Class<T> enemyClass, float x, float y) {
        AEnemy enemy = createCharacter(enemyClass);
        enemy.getPos().x = x;
        enemy.getPos().y = y;
        characters.add(enemy);
        return (T) enemy;
    }

    public <T extends AEnemy> T spawnEnemy (Class<T> enemyClass, float x, float y, float hp) {
        T enemy = spawnEnemy(enemyClass, x, y);
        enemy.setHp(hp);
        return enemy;
    }

    public void spawnEnemyAtRandom () {
        final float viewportWidth = Utils.camera.viewportWidth;
        final float viewportHeight = Utils.camera.viewportHeight;

        int randomX = (int) MathUtils.random(20, viewportWidth);
        int randomY = (int) MathUtils.random(20, viewportHeight);

        spawnEnemy(CommonShooterEnemy.class, randomX, randomY);
    }



    @Override
    public void dispose() {
        for (ACharacter character : characters) {
            character.dispose();
        }
    }
}
