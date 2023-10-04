package com.art.shooter.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Utils {
    public static Viewport UIViewPort;
    public static ExtendViewport mainViewport;
    public static OrthographicCamera camera;
    public static int cellSize;

    public static Vector2 reflect(Vector2 velocity, Vector2 normal) {
        // Reflects the velocity vector across the normal vector
        float dotProduct = velocity.dot(normal);
        return new Vector2(velocity.x - 2 * dotProduct * normal.x, velocity.y - 2 * dotProduct * normal.y);
    }

    public static Vector2 findWallNormal (Vector2 positionalVector) {
        if (Math.abs(positionalVector.x) > Math.abs(positionalVector.y)) {
            // vertical wall
            return new Vector2(1, 0);
        } else {
            // horizontal wall
            return new Vector2(0, 1);
        }
    }

}
