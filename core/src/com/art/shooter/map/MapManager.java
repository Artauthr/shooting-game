package com.art.shooter.map;

import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    public MapManager () {

    }

    public void placeWall (Vector2 vec) {
        placeWall(vec, 0);
    }

    public void placeWall (Vector2 vec, float rotation) {
        EntitySystem entitySystem = API.get(EntitySystem.class);
        Wall wall = entitySystem.createEntity(Wall.class);
        wall.setPos(vec);
        wall.getWallSprite().setRotation(rotation);
    }

    public void getClosestPlaceForWall (float x, float y) {

    }

    public void handleInput () {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {

        }
    }
}
