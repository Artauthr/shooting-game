package com.art.shooter.entities;

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;
import lombok.Getter;

public class EntitySystem implements Disposable {

    @Getter
    private Array<ASimpleEntity> entities = new Array<>();
    private Array<ASimpleEntity> toDispose = new Array<>();

    public EntitySystem() {

    }

    public <T extends ASimpleEntity> T createEntity(Class<T> clazz) {
        T entity = Pools.obtain(clazz);
        entity.create();
        entities.add(entity);
        toDispose.add(entity);
        System.out.println("created " + clazz.getSimpleName());
        return entity;
    }

    private float timer = 0f;

    public void updateEntities (float delta) {
        timer += delta;
        Array.ArrayIterator<ASimpleEntity> iterator = entities.iterator();
        while (iterator.hasNext()) {
            ASimpleEntity next = iterator.next();
            if (next.isFlaggedToRemove()) {
                Pools.free(next);
                iterator.remove();
                System.out.println("Removed - " + next.getClass().getSimpleName());
            }
        }

        for (ASimpleEntity entity : entities) {
            entity.update(delta);
        }
        if (timer > 5f) {
            System.out.println("Alive entities = " + entities.size);
            timer = 0f;
        }
    }

    public void drawEntities (PolygonSpriteBatch batch) {
        for (ASimpleEntity entity : entities) {
            entity.draw(batch);
        }
    }

    private void clearAll () {
        for (ASimpleEntity entity : entities) {
            entity.remove();
        }
        entities.clear();
    }

    public void forceRemove (ASimpleEntity entity) {
        entities.removeValue(entity, false);
    }

    @Override
    public void dispose() {
        for (ASimpleEntity entity : toDispose) {
            entity.dispose();
        }
    }
}
