package com.art.shooter.utils.screenUtils;

import com.art.shooter.logic.API;
import com.art.shooter.logic.GameObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;

public class CollisionDetector {
    public CollisionDetector () {

    }

    public void tick (float delta) { // one of the loops ever
        final Grid grid = API.get(Grid.class);
        final GridCell[][] cells = grid.getCells();
        for (GridCell[] cell : cells) {
            for (GridCell gridCell : cell) {
                final Array<GameObject> gameObjects = gridCell.getGameObjects();
                if (gameObjects.size < 2) continue;
                for (int i = 0; i < gameObjects.size; i++) {
                    for (int j = i + 1; j < gameObjects.size; j++) {
                        GameObject obj1 = gameObjects.get(i);
                        GameObject obj2 = gameObjects.get(j);
                        if (Intersector.overlapConvexPolygons(obj1.getCollider(), obj2.getCollider())) {
                            // Handle the collision
                            obj1.onHit(obj2);
                            System.out.println("two things hit each other bro");
                        }
                    }
                }

            }
        }
    }
}
