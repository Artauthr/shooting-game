package com.art.shooter.entities;

import com.art.shooter.map.Wall;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pools;
import lombok.Getter;

public class EntitySystem implements Disposable {

    @Getter
    private Array<ASimpleEntity> entities = new Array<>();
    private Array<ASimpleEntity> toDispose = new Array<>();
    @Getter
    private Array<Wall> walls = new Array<>();

    public EntitySystem() {

    }

    public <T extends ASimpleEntity> T createEntity(Class<T> clazz) {
        T entity = Pools.obtain(clazz);
        entity.create();
        entities.add(entity);
        if (clazz.isAssignableFrom(Wall.class)) {
            walls.add((Wall) entity);
        }
        toDispose.add(entity);
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

    @Override
    public void dispose() {
        for (ASimpleEntity entity : toDispose) {
            entity.dispose();
        }
    }
}
