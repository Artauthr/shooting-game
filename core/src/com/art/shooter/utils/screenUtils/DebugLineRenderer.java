package com.art.shooter.utils.screenUtils;

import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.map.Obstacle;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
        Array<Obstacle> walls = entitySystem.getWalls();
        for (Obstacle wall : walls) {
            Rectangle boundingBox = wall.getBoundingBox();
            drawBoundingBoxAsRect(shapeRenderer, boundingBox);
        }

        shapeRenderer.end();
    }

    public void drawBoundingBoxAsRect (ShapeRenderer shapeRenderer, Rectangle boundingBox) {
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, Color.RED,Color.RED,Color.RED,Color.RED);
    }
}
