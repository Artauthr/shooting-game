package com.art.shooter.logic;


import com.art.shooter.chars.ADrawablePerson;
import com.art.shooter.chars.CommonShooterEnemy;
import com.art.shooter.chars.MainCharacter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;
import lombok.Getter;

public class CharacterManager implements Disposable {
    /*this class manages the main player and enemies/NPC-s
    and interactions between them
    */
    private static CharacterManager instance;
    @Getter
    private MainCharacter mainCharacter; //there should be only 1 mainChar
    @Getter
    private Array<ADrawablePerson> characters;

    private CharacterManager () {
        this.characters = new Array<>();
    }

    public static CharacterManager getInstance() {
        if (instance == null) {
            instance = new CharacterManager();
        }
        return instance;
    }

    public void updateCharacters (Batch batch, float delta) {
        Array.ArrayIterator<ADrawablePerson> iterator = characters.iterator();
        while (iterator.hasNext()) {
            ADrawablePerson next = iterator.next();
            if (next.isFlaggedToRemove()) {
                Pools.free(next);
                iterator.remove();
            }
        }
        for (ADrawablePerson character : characters) {
            character.draw(batch, delta);
        }
    }

    public <T extends ADrawablePerson> T createCharacter(Class<T> clazz) {
        T character = Pools.obtain(clazz);
        characters.add(character);
        if (character instanceof MainCharacter) {
            mainCharacter = (MainCharacter) character;
        }
        return character;
    }

    public void spawnEnemy (float x, float y) {
        CommonShooterEnemy enemy = createCharacter(CommonShooterEnemy.class);
        enemy.setPos(new Vector2(x, y));
        characters.add(enemy);
    }

    public void spawnEnemyAtRandom () {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int randomX = MathUtils.random(20, width);
        int randomY = MathUtils.random(20, height);

        spawnEnemy(randomX, randomY);
    }

    @Override
    public void dispose() {

    }
}
