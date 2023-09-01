package com.art.shooter.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Pool;

public abstract class ASimpleEntity implements Pool.Poolable {
    boolean flaggedToRemove;
    protected abstract void update(float delta);
    protected abstract void remove();
    protected abstract void create();
    protected abstract void draw(Batch batch, float delta);

    public boolean isFlaggedToRemove () {
        return flaggedToRemove;
    }
}
