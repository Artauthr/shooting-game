package com.art.shooter.utils.screenUtils;

import com.art.shooter.chars.ACharacter;
import com.art.shooter.entities.BulletEntity;
import com.art.shooter.logic.API;
import com.art.shooter.logic.GameObject;
import com.art.shooter.map.Wall;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class CollisionManager {
    public CollisionManager() {
    }


    public void tick(float delta) {
        Grid grid = API.get(Grid.class);
        GridCell[][] cells = grid.getCells();

        for (GridCell[] row : cells) {
            for (GridCell cell : row) {
                handleCollisionsInCell(cell);
            }
        }
    }

    private void handleCollisionsInCell(GridCell cell) {
        Array<GameObject> gameObjects = cell.getGameObjects();
        int size = gameObjects.size;
        if (gameObjects.size < 2) return;

        for (int i = 0; i < size; i++) {
            GameObject obj1 = gameObjects.get(i);
            for (int j = i + 1; j < size; j++) {
                GameObject obj2 = gameObjects.get(j);
                if (Intersector.overlapConvexPolygons(obj1.getCollider(), obj2.getCollider())) {
                    obj2.onCollision(obj1);
                    obj1.onCollision(obj2);
                }
//                System.out.println("obj1.getClass().getSimpleName() = " + obj1.getClass().getSimpleName());
//                System.out.println("obj2.getClass().getSimpleName() = " + obj2.getClass().getSimpleName());
//                return;
            }
        }
    }

}