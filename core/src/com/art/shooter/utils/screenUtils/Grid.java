package com.art.shooter.utils.screenUtils;

import com.art.shooter.logic.GameObject;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

// the griddy
public class Grid {
    private int cellSize;
    private int rowAmount;
    private int colAmount;

    private static Grid instance;

    private GridCell[][] cells;

    private Grid() {
        construct(Utils.camera.viewportWidth, Utils.camera.viewportHeight);
    }


    public static Grid getInstance() {
        if (instance == null) {
            instance = new Grid();
        }
        return instance;
    }

    private void construct (float width, float height) {
        this.cellSize = 80;
        //these better be divisible or we gonna have problems kid
        this.colAmount = (int) (width / 80);
        this.rowAmount = (int) (height / 80);

        cells = new GridCell[rowAmount][colAmount];


        for (int i = 0; i < rowAmount; i++) {
            for (int j = 0; j < colAmount; j++) {
                cells[i][j] = new GridCell();
            }
        }

    }

    public Vector2 getCellPos (float x, float y) {
        final int u = (int) (x / cellSize);
        final int v = (int) (y / cellSize);


        int j = Math.min(u, colAmount - 1);
        int i = Math.min(v,rowAmount - 1);

        return new Vector2(i, j);
    }

    public GridCell getCellAt (float x, float y) {
        final Vector2 cellPos = getCellPos(x, y);
        return cells[(int) cellPos.x][(int) cellPos.y];
    }

    public GridCell getCellAt (Vector2 vec2) {
        return getCellAt(vec2.x, vec2.y);
    }

    public void debug (ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 0.5f);  // Set color to transparent white (50% transparency)

        for (int i = 0; i < rowAmount; i++) {
            shapeRenderer.line(0, i * 80, Utils.camera.viewportWidth, i * 80);
        }

        for (int i = 0; i < colAmount; i++) {
            shapeRenderer.line(i * 80, Utils.camera.viewportHeight, i * 80, 0);
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    public void setEntityToCell (GameObject gameObject) {
        final Grid grid = Grid.getInstance();
        grid.getCellAt(gameObject.getPos()).getGameObjects().add(gameObject);
    }

    public void removeEntityFromCell (GameObject gameObject) {
        getCellAt(gameObject.getPos()).getGameObjects().removeValue(gameObject, false);
    }


}
