package com.art.shooter.logic;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;
import lombok.Setter;

public abstract class GameObject {

    @Getter @Setter
    protected Circle colliderCircle = new Circle();

    @Getter @Setter
    protected Vector2 pos = new Vector2();
}
