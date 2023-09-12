package com.art.shooter.utils.screenUtils;

import com.art.shooter.logic.GameObject;
import com.badlogic.gdx.utils.Array;
import lombok.Getter;
public class GridCell {

    @Getter
    private Array<GameObject> gameObjects;
    public GridCell () {
        gameObjects = new Array<>();
    }

    public void add (GameObject gameObject) {
        this.gameObjects.add(gameObject);
    }

    public void remove (GameObject gameObject) {
        this.gameObjects.removeValue(gameObject, false);
    }

}
