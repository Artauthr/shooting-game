package com.art.shooter.ui;

import com.art.shooter.chars.ACharacter;
import com.art.shooter.logic.API;
import com.art.shooter.utils.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

public class GameUI {

    private ObjectMap<Class<? extends ADialog>, ADialog> dialogMap = new ObjectMap<>();

    @Getter
    private Stage stage;
    private Table rootUI;
    private boolean initialized = false;

    @Getter
    private Table dialogContainer;

    @Getter
    private final BitmapFont font;

    @Getter
    private final Label.LabelStyle regularLabelStyle;
    private Array<ACharacter> trackedCharacters = new Array<>();

    private Table mainLayout;

    public GameUI(Viewport viewport, Batch batch) {
        stage = new Stage(viewport, batch);

        rootUI = new Table();
        rootUI.setFillParent(true);
        rootUI.setTouchable(Touchable.enabled);

        Utils.UIViewPort = stage.getViewport();
        stage.addActor(rootUI);

        font = new BitmapFont();
        regularLabelStyle = new Label.LabelStyle(font, Color.WHITE);
        init();
    }

    public InputProcessor getStageForInputProcessor () {
        return stage;
    }

    private void init () {
        mainLayout = constructMainLayout();
        mainLayout.setFillParent(true);

        dialogContainer = new Table();
        dialogContainer.setFillParent(true);
        rootUI.addActor(dialogContainer);

        final Table debugTable = constructDebugTable();
        mainLayout.add(debugTable);

    }

    public static <T extends ADialog> T showDialog(Class<T> clazz) {
        T dialog = getDialog(clazz);
        dialog.show();

        return (T) dialog;
    }

    public static <T extends ADialog> T hideDialog(Class<T> clazz) {
        T dialog = getDialog(clazz);
        dialog.hide();

        return (T) dialog;
    }

    public static <T extends ADialog> T getDialog(Class<T> clazz) {
        GameUI gameUI = get();
        if(!gameUI.dialogMap.containsKey(clazz)) {
            try {
                ADialog dialog = ClassReflection.newInstance(clazz);
                gameUI.dialogMap.put(clazz, dialog);
            } catch (ReflectionException e) {
                throw new RuntimeException(e);
            }
        }

        ADialog dialog = gameUI.dialogMap.get(clazz);

        return (T) dialog;
    }

    private Table constructMainLayout () {
        final Table mainLayout = new Table();
        mainLayout.setFillParent(true);
        final Table debugTable = new Table();
        mainLayout.add(debugTable).expand().top().right();
        return mainLayout;
    }

    private Table constructDebugTable () {
        final Table segment = new Table();
        return segment;
    }

    public static GameUI get() {
        return API.get(GameUI.class);
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
