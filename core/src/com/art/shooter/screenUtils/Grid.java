package com.art.shooter.screenUtils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Grid {
    private int width;
    private int initialWidth;
    private int height;
    private int initialHeight;
    private int cellSize;
    private int initialCellSize;
    private Cell[][] cells;
    private static Grid instance;

    private Grid (int width, int height, int cellSize) {
        this.width = width;
        this.initialWidth = width;
        this.height = height;
        this.initialHeight = height;
        this.cellSize = cellSize;
        this.initialCellSize = cellSize;
        cells = new Cell[width/cellSize][height/cellSize];

        for (int i = 0; i < width/cellSize; i++) {
            for (int j = 0; j < height/cellSize; j++) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 45);
        }
        return instance;
    }

    public void onCameraZoomChanged (float zoomLevel) {
        this.cellSize = (int) (initialCellSize * zoomLevel);
        this.width = (int) (initialWidth * zoomLevel);
        this.height = (int) (initialHeight * zoomLevel);

        // Recreate the cells array with new dimensions
        cells = new Cell[width/cellSize][height/cellSize];

        for (int i = 0; i < width/cellSize; i++) {
            for (int j = 0; j < height/cellSize; j++) {
                this.cells[i][j] = new Cell();
            }
        }
    }

    public void draw (ShapeRenderer renderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(1, 1, 1, 0.1f);

        for (int i = 0; i <= width / cellSize; i++) {
            renderer.line(i * cellSize, 0, i * cellSize, height);
        }

        // Draw the horizontal lines
        for (int i = 0; i <= height / cellSize; i++) {
            renderer.line(0, i * cellSize, width, i * cellSize);
        }

        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


}