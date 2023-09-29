package com.art.shooter.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public abstract class ADialog extends Table {
    private Table content;
    public abstract void buildContent (Table content);

    public ADialog () {
        final Table titleSegment = constructTitleSegment();
    }

    private Table constructTitleSegment () {
        final Table titleSegment = new Table();
        final GameUI gameUI = GameUI.get();
        final Label titleLabel = new Label(getDialogTitle(), gameUI.getRegularLabelStyle());
        titleLabel.setAlignment(Align.center);
        titleLabel.setWrap(true);
        titleSegment.add(titleLabel).expandX().center();
        return titleSegment;
    }

//    private Table constructOverlay () {
//        final Table overlayTable = new Table();
//        overlayTable.?
//    }

    protected String getDialogTitle () {
        return "Title";
    }

}
