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

}
