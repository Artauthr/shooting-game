package com.art.shooter.map;

import com.art.shooter.entities.ASimpleEntity;
import com.art.shooter.logic.API;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import lombok.Getter;

public class Wall extends ASimpleEntity {
    @Getter
    private Sprite wallSprite;
    private boolean placed;
    public Wall () {
        FileHandle handle = Gdx.files.internal("white-pixel.png");
        Texture wallTexture = new Texture(handle);
        wallSprite = new Sprite(wallTexture);
        wallSprite.setColor(Color.RED);
        wallSprite.setOriginCenter();
        int cellSize = Utils.cellSize;
        wallSprite.setSize(10, cellSize);

        collider = new Polygon(new float[]{0,0,wallSprite.getWidth(),0,wallSprite.getWidth(),wallSprite.getHeight(),0,wallSprite.getHeight()});
        collider.setOrigin(wallSprite.getWidth() / 2, wallSprite.getHeight() / 2);
    }

    public void addToCell () {
        Grid grid = API.get(Grid.class);
        grid.addEntityByRect(this);
    }

    @Override
    protected void update(float delta) {
        if (!placed) {  //no need to update much since wall is static
            wallSprite.setPosition(pos.x, pos.y);
            collider.setPosition(pos.x, pos.y);
            collider.setVertices(new float[]{0,0,wallSprite.getWidth(),0,wallSprite.getWidth(),wallSprite.getHeight(),0,wallSprite.getHeight()});
            addToCell();
            placed = true;
        }
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
