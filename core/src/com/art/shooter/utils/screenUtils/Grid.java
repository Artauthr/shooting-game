package com.art.shooter.utils.screenUtils;

import com.art.shooter.logic.GameObject;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

// the griddy
public class Grid {
    private Array<GridCell> tmp = new Array<>();
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

    public Array<GridCell> getCellsAtRect (Rectangle rectangle) {
        final float x0 = rectangle.x;
        final float y0 = rectangle.y;
        final float x1 = rectangle.x + rectangle.width;
        final float y1 = rectangle.y + rectangle.height;
        tmp.clear();
        tmp.add(getCellAt(x0, y0));
        tmp.add(getCellAt(x1, y0));
        tmp.add(getCellAt(x0, y1));
        tmp.add(getCellAt(x1, y1));
        return tmp;
    }

    @Deprecated
    public Array<GridCell> getCellsAt (Circle colliderCircle) {
        tmp.clear();
        GridCell centerCell = getCellAt(colliderCircle.x, colliderCircle.y);

        float xPlus = colliderCircle.x + colliderCircle.radius;
        float xMinus = colliderCircle.x - colliderCircle.radius;
        GridCell cell1 = getCellAt(xPlus, colliderCircle.y);
        GridCell cell2 = getCellAt(xMinus, colliderCircle.y);
        GridCell cell3 = getCellAt(colliderCircle.x, colliderCircle.y + colliderCircle.radius);
        GridCell cell4 = getCellAt(colliderCircle.x, colliderCircle.y - colliderCircle.radius);

        tmp.add(centerCell);
        tmp.add(cell1);
        tmp.add(cell2);
        tmp.add(cell3);
        tmp.add(cell4);

        return tmp;
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
                final GridCell gridCell = cells[i][j];
                if (gridCell.getGameObjects().size > 0) {
                    Color greenTransparent = Color.GREEN;
                    greenTransparent.a = 0.57f;
                    shapeRenderer.rect(cellCenterX, cellCenterY, cellSize, cellSize, greenTransparent, greenTransparent, greenTransparent, greenTransparent);
                } else {
                    shapeRenderer.rect(cellCenterX, cellCenterY, cellSize, cellSize);
                }
            }
        }

        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public GridCell addEntityByPosition (GameObject gameObject) {
        GridCell cell = getCellAt(gameObject.getPos());
        cell.getGameObjects().add(gameObject);
        return cell;
    }

    public void removeEntityByPosition (GameObject gameObject) {
        getCellAt(gameObject.getPos()).getGameObjects().removeValue(gameObject, false);
    }


    public void addEntityByRect (GameObject gameObject) {
        Rectangle boundingBox = gameObject.getBoundingBox();
        Array<GridCell> cellsToAdd = getCellsAtRect(boundingBox);
        for (GridCell gridCell : cellsToAdd) {
            gridCell.add(gameObject);
        }
    }

    public void removeEntityByRect (GameObject gameObject) {
        Array<GridCell> cellsToRemoveFrom = getCellsAtRect(gameObject.getBoundingBox());
        for (GridCell gridCell : cellsToRemoveFrom) {
            gridCell.remove(gameObject);
        }
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


}
