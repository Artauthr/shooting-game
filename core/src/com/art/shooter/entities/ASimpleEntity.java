package com.art.shooter.entities;

import com.art.shooter.logic.GameObject;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;
import lombok.Setter;

public abstract class ASimpleEntity extends GameObject implements Pool.Poolable {
    @Setter
    private boolean flaggedToRemove;
    protected abstract void update(float delta);
    protected abstract void remove();
    protected abstract void create();
    protected abstract void draw(Batch batch);
    protected abstract void dispose();

    public boolean isFlaggedToRemove () {
        return flaggedToRemove;
    }
}
