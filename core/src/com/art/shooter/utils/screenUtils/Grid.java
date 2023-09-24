package com.art.shooter.utils.screenUtils;

import com.art.shooter.logic.GameObject;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.OrderedSet;

// the griddy
public class Grid {
    private int cellSize;
    private int rowAmount;
    private int colAmount;
    private GridCell[][] cells;

    public Grid() {
        construct(Utils.camera.viewportWidth, Utils.camera.viewportHeight);
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

    public GridCell getCellAt (float x, float y) {
        final int u = (int) (x / cellSize);
        final int v = (int) (y / cellSize);


        int j = Math.min(u, colAmount - 1);
        int i = Math.min(v,rowAmount - 1);

        return cells[i][j];
    }

    public GridCell getCellAt (Vector2 vec2) {
        return getCellAt(vec2.x, vec2.y);
    }

    public OrderedSet<GridCell> getCellsAt (Circle colliderCircle) {
        OrderedSet<GridCell> cells = new OrderedSet<>();
        GridCell centerCell = getCellAt(colliderCircle.x, colliderCircle.y);

        float xPlus = colliderCircle.x + colliderCircle.radius;
        float xMinus = colliderCircle.x - colliderCircle.radius;
        GridCell cell1 = getCellAt(xPlus, colliderCircle.y);
        GridCell cell2 = getCellAt(xMinus, colliderCircle.y);
        GridCell cell3 = getCellAt(colliderCircle.x, colliderCircle.y + colliderCircle.radius);
        GridCell cell4 = getCellAt(colliderCircle.x, colliderCircle.y - colliderCircle.radius);

        cells.add(centerCell);
        cells.add(cell1);
        cells.add(cell2);
        cells.add(cell3);
        cells.add(cell4);

        return cells;
    }

    public void debug (ShapeRenderer shapeRenderer) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(Utils.camera.combined);
        shapeRenderer.setColor(1, 1, 1, 0.5f);  // Set color to transparent white (50% transparency)

//        for (int i = 0; i < rowAmount; i++) {
//            shapeRenderer.line(0, i * 80, Utils.camera.viewportWidth, i * 80);
//        }
//
//        for (int i = 0; i < colAmount; i++) {
//            shapeRenderer.line(i * 80, Utils.camera.viewportHeight, i * 80, 0);
//        }

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                float x = i * cellSize;
                float y = j * cellSize;
                shapeRenderer.rect(x, y, cellSize, cellSize);
            }
        }


        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void printNumOfOccupiedCells () {
        int count = 0;
        for (GridCell[] cell : cells) {
            for (GridCell gridCell : cell) {
                if (gridCell.getGameObjects().size > 0) {
                    count++;
                }
            }
        }
        System.out.println("Occupied Cells " + count);
    }


    public GridCell addEntityToCell (GameObject gameObject) {
        GridCell cell = getCellAt(gameObject.getPos());
        cell.getGameObjects().add(gameObject);
        return cell;
    }

    public void addEntityToCellV2 (GameObject gameObject) {
        Circle colliderCircle = gameObject.getColliderCircle();
        OrderedSet<GridCell> cellsToAdd = getCellsAt(colliderCircle);
        for (GridCell gridCell : cellsToAdd) {
            gridCell.add(gameObject);
        }
    }

    public void removeEntityFromCellV2 (GameObject gameObject) {
        OrderedSet<GridCell> cellsToRemoveFrom = getCellsAt(gameObject.getColliderCircle());
        for (GridCell gridCell : cellsToRemoveFrom) {
            gridCell.remove(gameObject);
        }
    }

    public void removeEntityFromCell (GameObject gameObject) {
        getCellAt(gameObject.getPos()).getGameObjects().removeValue(gameObject, false);
    }


}
