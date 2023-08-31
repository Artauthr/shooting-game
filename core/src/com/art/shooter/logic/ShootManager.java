package com.art.shooter.logic;

import com.art.shooter.entities.BulletEntity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;

public class ShootManager {
    private ShootManager(){

    }
    public static void shoot (Vector2 sourcePos, Vector2 targetPos, float randomFactor) {
        final BulletEntity bullet = Pools.obtain(BulletEntity.class);
        bullet.setPos(sourcePos.x, sourcePos.y);
    }

}
