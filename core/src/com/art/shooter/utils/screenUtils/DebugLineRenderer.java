package com.art.shooter.utils.screenUtils;

import com.art.shooter.chars.MainCharacter;
import com.art.shooter.entities.ASimpleEntity;
import com.art.shooter.entities.BulletEntity;
import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.map.Wall;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class DebugLineRenderer {

    public DebugLineRenderer () {
    }

    public void draw (ShapeRenderer shapeRenderer) {
        final OrthographicCamera camera = Utils.camera;
        final float viewportWidth = camera.viewportWidth;
        final float viewportHeight = camera.viewportHeight;

        shapeRenderer.begin();
        //bounds
        shapeRenderer.line(1, 0, 1, viewportHeight);
        shapeRenderer.line(1, viewportHeight - 2, viewportWidth, viewportHeight - 2);
        shapeRenderer.line(1, 0, viewportWidth, 0);
        shapeRenderer.line(viewportWidth, 0, viewportWidth, viewportHeight);
        // end bounds

        final EntitySystem entitySystem = API.get(EntitySystem.class);
        final Array<ASimpleEntity> entities = entitySystem.getEntities();
//        for (ASimpleEntity entity : entities) {
//            final Circle colliderCircle = entity.getColliderCircle();
//            shapeRenderer.circle(colliderCircle.x, colliderCircle.y, colliderCircle.radius);
//        }
        CharacterManager characterManager = API.get(CharacterManager.class);
        MainCharacter mainCharacter = characterManager.getMainCharacter();
        shapeRenderer.polygon(mainCharacter.getCollider().getTransformedVertices());
        Rectangle boundingRectangle = mainCharacter.getCollider().getBoundingRectangle();

        drawBoundingBoxAsRect(shapeRenderer,boundingRectangle);

//        for (ASimpleEntity entity : entities) {
//            if (entity instanceof BulletEntity) {
//                shapeRenderer.polygon(entity.getCollider().getTransformedVertices());
//            }
//        }

        shapeRenderer.end();
    }

    public void drawBoundingBoxAsRect (ShapeRenderer shapeRenderer, Rectangle boundingBox) {
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, Color.RED,Color.RED,Color.RED,Color.RED);
    }
}
