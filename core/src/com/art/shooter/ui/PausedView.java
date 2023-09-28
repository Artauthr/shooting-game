package com.art.shooter.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PausedView extends Table {
    public PausedView () {
        this.setFillParent(true);
        final FileHandle fileHandle = Gdx.files.internal("ui-white-pixel.9.png");

        this.setBackground(internal);
    }
}
