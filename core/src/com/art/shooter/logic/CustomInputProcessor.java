package com.art.shooter.logic;

import com.art.shooter.chars.MainCharacter;
import com.art.shooter.map.MapManager;
import com.art.shooter.utils.Utils;
import com.art.shooter.utils.screenUtils.Grid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CustomInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            GameLogic gameLogic = API.get(GameLogic.class);
            if (gameLogic.isPaused()) {
                gameLogic.unPause();
            } else {
                gameLogic.pauseGame();
            }
        }
        if (keycode == Input.Keys.Q) {
            MainCharacter mainCharacter = API.get(CharacterManager.class).getMainCharacter();
            System.out.println("mainCharacter.pos.x = " + mainCharacter.pos.x);
            System.out.println("mainCharacter.pos.y = " + mainCharacter.pos.y);
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            if (API.get(GameLogic.class).isInEditMode()) {
                MapManager mapManager = API.get(MapManager.class);
                Vector2 wallPos = new Vector2(screenX, Utils.camera.viewportHeight - screenY);
                final Vector2 originPoint = API.get(Grid.class).getClosestPoint(screenX, Utils.camera.viewportHeight - screenY);
                boolean vertical = API.get(Grid.class).isVertical(screenX, Utils.camera.viewportHeight - screenY);
                System.out.println("vertical = " + vertical);
                if (vertical) {
                    mapManager.placeWall(originPoint, true);
                } else {
                    mapManager.placeWall(originPoint, false);
                }
                System.out.println("originPoint = " + originPoint);
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        final OrthographicCamera camera = Utils.camera;

        // get the mouse position in world coordinates before adjusting the zoom
        final Vector3 mouseWorldCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mouseWorldCoords);

        // calculate the new zoom level
        final float deltaZoom = 2f; // TODO: 04.09.23 make static or a field variable
        float newZoom = camera.zoom + deltaZoom * amountY;

        // set minimum and maximum zoom levels
        float minZoom = 5f; // TODO: 04.09.23 make static or a field variable
        float maxZoom = 8f; // TODO: 04.09.23 make static or a field variable
        if (newZoom < minZoom) {
            newZoom = minZoom;
        } else if (newZoom > maxZoom) {
            newZoom = maxZoom;
        }

        // calculate the position adjustments to keep the mouse position constant
        float deltaX = (mouseWorldCoords.x - camera.position.x) * (1 - newZoom / camera.zoom);
        float deltaY = (mouseWorldCoords.y - camera.position.y) * (1 - newZoom / camera.zoom);

        // update the camera's zoom and position
//        camera.zoom = newZoom;
//        camera.position.add(deltaX, deltaY, 0);
        camera.update();
        return true;
    }
}
