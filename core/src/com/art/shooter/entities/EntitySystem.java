package com.art.shooter.entities;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;

public class EntitySystem implements Disposable {
    private static EntitySystem instance;
    private Array<ASimpleEntity> entities = new Array<>();
    private Array<ASimpleEntity> toDispose = new Array<>();

    private EntitySystem() {

    }

    public static EntitySystem getInstance () {
        if (instance == null) {
            instance = new EntitySystem();
        }
        return instance;
    }

    public <T extends ASimpleEntity> T createEntity(Class<T> clazz) {
        T entity = Pools.obtain(clazz);
        entity.create();
        entities.add(entity);
        toDispose.add(entity);
        return entity;
    }

    public void updateEntities (PolygonSpriteBatch batch, float delta) {
        Array.ArrayIterator<ASimpleEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            ASimpleEntity next = iterator.next();
            if (next.isFlaggedToRemove()) {
                Pools.free(next);
                iterator.remove();
            }
        }
        for (ASimpleEntity entity : entities) {
            entity.draw(batch, delta);
        }
    }

    private void clearAll () {
        for (ASimpleEntity entity : entities) {
            entity.remove();
        }
        entities.clear();
    }

    @Override
    public void dispose() {
        for (ASimpleEntity entity : toDispose) {
            entity.dispose();
        }
    }
}
