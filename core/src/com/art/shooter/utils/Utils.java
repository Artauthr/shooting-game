package com.art.shooter.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import lombok.Setter;

public class Utils {
    public static Viewport UIViewPort;
    public static ExtendViewport mainViewport;
    public static OrthographicCamera camera;
    public static int cellSize;
    public static float getDistanceBetweenTwoVectors (Vector2 v1, Vector2 v2) {
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Vectors must not be null");
        }
        double powX = Math.pow((v2.x - v1.x), 2);
        double powY = Math.pow((v2.y - v1.y), 2);
        return (float) Math.sqrt(powX + powY);
    }
    public static Vector2 reflect(Vector2 velocity, Vector2 normal) {
        // Reflects the velocity vector across the normal vector
        float dotProduct = velocity.dot(normal);
        return new Vector2(velocity.x - 2 * dotProduct * normal.x, velocity.y - 2 * dotProduct * normal.y);
    }


}
