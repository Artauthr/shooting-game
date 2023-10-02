package com.art.shooter.map;

import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    public MapManager () {

    }

    public void placeWall (Vector2 vec) {
        placeWall(vec, true);
    }

    public void placeWall (Vector2 vec, boolean vertical) {
        EntitySystem entitySystem = API.get(EntitySystem.class);
        Wall wall = entitySystem.createEntity(Wall.class);
        wall.setPos(vec);
        if (vertical) {
            wall.getWallSprite().setSize(7, Utils.cellSize);
        } else {
            wall.getWallSprite().setSize(Utils.cellSize,7);
        }
    }

    public void getClosestPlaceForWall (float x, float y) {

    }

    public void handleInput () {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {

        }
    }
}
