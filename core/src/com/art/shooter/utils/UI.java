package com.art.shooter.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UI {
    private Stage stage;
    private Table rootUI;
    private static UI instance;

    private UI (Viewport viewport, Batch batch) {
        stage = new Stage(viewport, batch);

        rootUI = new Table();
        rootUI.setFillParent(true);
        rootUI.setTouchable(Touchable.enabled);

        MiscUtils.UIViewPort = stage.getViewport();
    }


    public void onResize () {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public void act () {
        stage.act();
    }

    public void draw () {
        stage.getViewport().apply();
        stage.draw();
    }
}
