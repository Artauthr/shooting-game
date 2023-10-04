package com.art.shooter.logic;


import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

public abstract class GameObject {

    @Getter
    protected boolean debug;

    @Getter
    protected Polygon collider;

    @Getter @Setter
    protected Vector2 pos = new Vector2();

    public void onHit(GameObject obj2){};
}
