package com.art.shooter.utils.screenUtils;

import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MovementGrid {
    private int cellSize;
    private int rowAmount;
    private int colAmount;
    private MovementGridCell[][] cells;

    public MovementGrid () {
        construct(Utils.camera.viewportWidth, Utils.camera.viewportHeight);
    }


    private void construct (float width, float height) {
        this.cellSize = 40;
        this.colAmount = (int) (width / cellSize);
        this.rowAmount = (int) (height / cellSize);

        cells = new MovementGridCell[rowAmount][colAmount];

        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < colAmount; j++) {
                cells[i][j] = new MovementGridCell();
            }
        }

    }

    public void renderDebug (ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(Utils.camera.combined);
        shapeRenderer.setColor(1, 1, 1, 0.15f);

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                final float cellCenterX = j * cellSize;
                final float cellCenterY = i * cellSize;
                shapeRenderer.rect(cellCenterX, cellCenterY, cellSize, cellSize);
            }
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
