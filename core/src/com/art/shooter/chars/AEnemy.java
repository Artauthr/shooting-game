package com.art.shooter.chars;

import com.badlogic.gdx.utils.Pool;

public abstract class AEnemy extends ACharacter implements Pool.Poolable {
    public AEnemy () {
        hp = 100;
    }
}
