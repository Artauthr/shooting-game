package com.art.shooter.ui.buttons;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class ImageOffsetButton extends OffsetButton {
    private Drawable drawable;

    public ImageOffsetButton(Style style, Drawable drawable) {
        super(style);
        this.drawable = drawable;
    }

    @Override
    protected void buildInnerContent(Table content) {
        Image iconImg = new Image(drawable, Scaling.fit);
        content.add(iconImg).pad(25).grow();
    }
}
