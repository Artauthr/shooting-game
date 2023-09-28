package com.art.shooter.ui;

import com.art.shooter.chars.ACharacter;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

public class GameUI {

    @Getter
    private Stage stage;
    private Table rootUI;
    private boolean initialized = false;
    private BitmapFont font;
    private Array<ACharacter> trackedCharacters = new Array<>();

    public GameUI(Viewport viewport, Batch batch) {
        stage = new Stage(viewport, batch);

        rootUI = new Table();
        rootUI.setFillParent(true);
        rootUI.setTouchable(Touchable.enabled);

        Utils.UIViewPort = stage.getViewport();
        stage.addActor(rootUI);

        font = new BitmapFont();
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
