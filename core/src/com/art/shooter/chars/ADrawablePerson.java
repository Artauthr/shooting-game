package com.art.shooter.chars;

import com.art.shooter.logic.GameObject;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

public abstract class ADrawablePerson implements GameObject {
    protected float hp;
    protected float damage;
    @Getter
    protected Sprite characterSprite;
    @Getter@Setter
    protected Vector2 pos;
    @Setter
    @Getter
    protected boolean isFlaggedToRemove;
    public abstract void update(float delta);
    public abstract void draw(Batch batch);
    public abstract void remove();
    public abstract void dispose();
}
