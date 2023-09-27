package com.art.shooter.utils.screenUtils;

import com.art.shooter.chars.MainCharacter;
import com.art.shooter.logic.API;
import com.art.shooter.logic.CharacterManager;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

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

        final MainCharacter mainCharacter = API.get(CharacterManager.class).getMainCharacter();

//        circle
//        final Circle colliderCircle = mainCharacter.getColliderCircle();
//        shapeRenderer.circle(colliderCircle.x, colliderCircle.y, colliderCircle.radius);

//        final Rectangle colliderBox = mainCharacter.getBoundingBox();
//        shapeRenderer.rect(colliderBox.getX(), colliderBox.getY(), colliderBox.getWidth(), colliderBox.getHeight());


        shapeRenderer.end();
    }
}
