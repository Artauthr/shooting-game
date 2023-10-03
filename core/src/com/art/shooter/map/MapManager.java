package com.art.shooter.map;

import com.art.shooter.entities.EntitySystem;
import com.art.shooter.logic.API;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
        final Sprite wallSprite = wall.getWallSprite();
        if (vertical) {
            wallSprite.setSize(7, Utils.cellSize-1);
        } else {
            wallSprite.setSize(Utils.cellSize-1,7);
        }
//        wall.setPos(new Vector2(vec.x - wallSprite.getWidth() / 2f, vec.y - wallSprite.getHeight() / 2f));
        wall.setPos(vec);
    }



    public void handleInput () {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {

        }
    }
}
