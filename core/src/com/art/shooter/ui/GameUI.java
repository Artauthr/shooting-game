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
    private static GameUI instance;
    private boolean initialized;
    private BitmapFont font;
    private Label.LabelStyle labelStyle;
    private Array<ACharacter> trackedCharacters = new Array<>();

    private Table positionalLabelHolder;

    public GameUI(Viewport viewport, Batch batch) {
        initialized = false;
        stage = new Stage(viewport, batch);

        rootUI = new Table();
        rootUI.setFillParent(true);
        rootUI.setTouchable(Touchable.enabled);

        Utils.UIViewPort = stage.getViewport();
        stage.addActor(rootUI);

        font = new BitmapFont();
        labelStyle = new Label.LabelStyle(font, Color.WHITE);
    }



    public void init () {
        if (initialized) return;

        for (ACharacter trackedCharacter : trackedCharacters) {
            Label xPosLabel = new Label("x", labelStyle);
            Label yPosLabel = new Label("y", labelStyle);
            final Table posTable = new Table();
            rootUI.add(posTable);
            System.out.println("Pos table added");
        }

        initialized = true;
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
