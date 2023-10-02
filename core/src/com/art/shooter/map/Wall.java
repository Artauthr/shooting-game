package com.art.shooter.map;

import com.art.shooter.entities.ASimpleEntity;
import com.art.shooter.logic.API;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import lombok.Getter;

public class Wall extends ASimpleEntity {
    @Getter
    private Sprite wallSprite;
    private boolean configured;
    public Wall () {
        FileHandle handle = Gdx.files.internal("wall.png");
        Texture wallTexture = new Texture(handle);
        wallSprite = new Sprite(wallTexture);
        wallSprite.setOriginCenter();
        int cellSize = Utils.cellSize;
        wallSprite.setSize(10, cellSize);
        boundingBox.setSize(10, cellSize);
    }

    public void addToCell () {
        Grid grid = API.get(Grid.class);
        grid.addEntityByRect(this);
    }

    @Override
    protected void update(float delta) {
        wallSprite.setPosition(pos.x, pos.y);
        boundingBox.setPosition(pos.x, pos.y);
        boundingBox.setSize(wallSprite.getWidth(), wallSprite.getHeight());
        if (!configured) {
            addToCell();
            configured = true;
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
