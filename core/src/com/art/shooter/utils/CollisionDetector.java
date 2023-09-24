package com.art.shooter.utils;

import com.art.shooter.chars.ACharacter;
import com.art.shooter.entities.ASimpleEntity;
import com.art.shooter.logic.GameObject;
import com.art.shooter.utils.screenUtils.GridCell;
import com.art.shooter.utils.screenUtils.Ray2D;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;

public class CollisionDetector {
    public CollisionDetector () {

    }


    public static boolean rayIntersectsRectangle (Ray2D ray, Rectangle boundingBox) {
        Vector2 center = Pools.obtain(Vector2.class);
        boundingBox.getCenter(center);

        Vector2 dimensions = Pools.obtain(Vector2.class);
        boundingBox.getSize(dimensions);

        final float divX = 1f / ray.direction.x;
        final float divY = 1f / ray.direction.y;

        float minx = ((center.x - dimensions.x * 0.5f) - ray.origin.x) * divX;
        float maxx = ((center.x + dimensions.x * 0.5f) - ray.origin.x) * divX;
        if (minx > maxx) {
            final float t = minx;
            minx = maxx;
            maxx = t;
        }

        float miny = ((center.y - dimensions.y * 0.5f) - ray.origin.y) * divY;
        float maxy = ((center.y + dimensions.y * 0.5f) - ray.origin.y) * divY;
        if (miny > maxy) {
            final float t = miny;
            miny = maxy;
            maxy = t;
        }

        float min = Math.max(minx, miny);
        float max = Math.min(maxx, maxy);

        boolean result = max >= 0 && max >= min;

        Pools.free(center);
        Pools.free(dimensions);
        return result;
    }

    public static boolean bulletToCharacter (ASimpleEntity entity, ACharacter character) {
        Vector2 entityPos = entity.getPos();
        Vector2 characterPos = character.getPos();
        return entityPos.x == characterPos.x || entityPos.y == characterPos.y;
    }

//    public static boolean bulletToEnemy (GridCell cell) {
//        Array<GameObject> gameObjects = cell.getGameObjects();
//        for (GameObject gameObject : gameObjects) {
//
//        }
//    }





}
