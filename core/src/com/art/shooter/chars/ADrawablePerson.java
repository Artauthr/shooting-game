package com.art.shooter.chars;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class ADrawablePerson {
    protected Vector2 pos;
    public abstract void update();
    public abstract void draw(Batch batch);
    public Vector2 getPos () {
        return this.pos;
    }
}
