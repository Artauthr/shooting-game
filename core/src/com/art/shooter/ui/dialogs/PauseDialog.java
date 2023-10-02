package com.art.shooter.ui.dialogs;

import com.art.shooter.ui.ADialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PauseDialog extends ADialog {

    @Override
    protected void constructContent(Table content) {
        content.add().size(200,300);

    }

    @Override
    protected String getDialogTitle() {
        return "Settings";
    }

    @Override
    protected float getTitleFontScale() {
        return 3f;
    }
}
