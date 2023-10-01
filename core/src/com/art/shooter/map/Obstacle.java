package com.art.shooter.map;

import com.art.shooter.entities.ASimpleEntity;
import com.art.shooter.logic.API;
import com.art.shooter.logic.GameObject;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Obstacle extends ASimpleEntity {
    private Sprite wallSprite;
    private boolean addedToCell;
    public Obstacle () {
        FileHandle handle = Gdx.files.internal("wall.png");
        Texture wallTexture = new Texture(handle);
        wallSprite = new Sprite(wallTexture);
        int cellSize = Utils.cellSize;
        wallSprite.setSize(2, cellSize);
        boundingBox.setSize(2, cellSize);
    }

    public void addToCell () {
        Grid grid = API.get(Grid.class);
        grid.addEntityByRect(this);
    }

    @Override
    protected void update(float delta) {
        wallSprite.setPosition(pos.x, pos.y);
        boundingBox.setPosition(pos.x, pos.y);
        if (!addedToCell) {
            addToCell();
            addedToCell = true;
        }
    }

    public void debug (ShapeRenderer shapeRenderer) {
        shapeRenderer.begin();
        shapeRenderer.rect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
        shapeRenderer.end();
    }

    @Override
    protected void remove() {
        setFlaggedToRemove(true);
        Grid grid = API.get(Grid.class);
        grid.removeEntityByRect(this);
    }

    @Override
    protected void create() {

    }

    @Override
    protected void draw(Batch batch) {
        wallSprite.draw(batch);
    }

    @Override
    protected void dispose() {
        wallSprite.getTexture().dispose();
    }

    @Override
    public void reset() {

    }
}
