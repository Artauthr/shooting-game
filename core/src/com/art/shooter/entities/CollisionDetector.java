package com.art.shooter.entities;

import com.art.shooter.chars.ADrawablePerson;
import com.art.shooter.logic.CharacterManager;
import com.badlogic.gdx.utils.Array;

public class CollisionDetector {

    public CollisionDetector () {

    }

    public void run () {
        EntitySystem entitySystem = EntitySystem.getInstance();
        Array<ASimpleEntity> entities = entitySystem.getEntities();

        CharacterManager characterManager = CharacterManager.getInstance();
        Array<ADrawablePerson> characters = characterManager.getCharacters();

        for (ASimpleEntity entity : entities) {
            for (ADrawablePerson character : characters) {

            }
        }


    }
}
