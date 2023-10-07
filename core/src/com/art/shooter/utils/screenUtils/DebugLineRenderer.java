package com.art.shooter.utils.screenUtils;

import com.art.shooter.chars.ACharacter;
import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.ASimpleEntity;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.logic.CharacterManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class DebugLineRenderer {

    private boolean debugEntities = true;
    private boolean debugCharacters = false;
    private boolean debugCharacterRects = false;


    public DebugLineRenderer () {
    }

    public void draw (ShapeRenderer shapeRenderer) {
        shapeRenderer.begin();

        // entities
        final EntitySystem entitySystem = API.get(EntitySystem.class);
        final Array<ASimpleEntity> entities = entitySystem.getEntities();

        //characters
        CharacterManager characterManager = API.get(CharacterManager.class);
        final Array<ACharacter> characters = characterManager.getCharacters();
        MainCharacter mainCharacter = characterManager.getMainCharacter();


        if (debugEntities) {
            for (ASimpleEntity entity : entities) {
                shapeRenderer.polygon(entity.getCollider().getTransformedVertices());
            }
        }

        if (debugCharacters) {
            for (ACharacter character : characters) {
                shapeRenderer.polygon(character.getCollider().getTransformedVertices());
                if (debugCharacterRects) {
                    final Rectangle box = character.getCollider().getBoundingRectangle();
                    shapeRenderer.rect(box.x, box.y, box.width, box.height, Color.RED, Color.RED, Color.RED, Color.RED);
                }
            }
        }

        shapeRenderer.end();
    }

    public void drawBoundingBoxAsRect (ShapeRenderer shapeRenderer, Rectangle boundingBox) {
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, Color.RED,Color.RED,Color.RED,Color.RED);
    }
}
