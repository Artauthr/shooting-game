package com.art.shooter.chars;

import com.art.shooter.logic.GameObject;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import lombok.Getter;
import lombok.Setter;

public abstract class ACharacter extends GameObject {
    protected float hp;
    protected float damage;

    @Getter
    protected Sprite characterSprite;

    @Setter @Getter
    protected boolean isFlaggedToRemove;

    public abstract void update(float delta);
    public abstract void draw(Batch batch);
    public abstract void remove();
    public abstract void dispose();
}
