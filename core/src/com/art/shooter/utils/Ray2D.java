package com.art.shooter.utils;

import com.badlogic.gdx.math.Vector2;

public class Ray2D {
    public Vector2 origin;
    public Vector2 direction;

    public Ray2D (Vector2 origin, Vector2 direction) {
        this.origin = origin;
        this.direction = direction.nor();
    }
}
