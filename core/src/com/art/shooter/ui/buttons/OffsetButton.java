package com.art.shooter.ui.buttons;

import com.art.shooter.ui.ColorLib;
import com.art.shooter.ui.Squircle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Setter;

public class OffsetButton extends Table {
    private Table frontTable;
    private Cell<Table> frontCell;
    private ClickListener listener;
    private float offset;
    private float pad;
    private Style style;
    private boolean pressing;
    private boolean shouldRelease;
    private boolean releasing;
    private final Vector2 size = new Vector2();

    @Setter
    private Runnable onTouchDown;

    @Setter
    private Runnable onTouchUp;
    private float timer;
    private float pressDuration = 0.05f;

    public OffsetButton(Style style) {
        this.style = style;
        build();
        setStyle(style);
    }

    protected void buildInnerContent (Table content) {
    }

    public void build () {
        frontTable = new Table();
        frontCell = add(frontTable).grow();
        buildInnerContent(frontTable);

        initListeners();
    }

    private void initListeners () {
        listener = new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isAnimating()) return false;

                // get the size before animations
                size.set(getWidth(), getHeight());

                pressing = true;
                if (onTouchDown != null) onTouchDown.run();
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (pressing) {
                    // schedule release
                    shouldRelease = true;
                } else releasing = true;
            }
        };
        addListener(listener);
        setTouchable(Touchable.enabled);
    }

    private boolean isAnimating () {
        return pressing || releasing;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!pressing && shouldRelease) {
            releasing = true; shouldRelease = false;
        }

        if (!isAnimating()) return;

        timer += delta;

        if (pressing) {
            final float padBottom = MathUtils.clamp(Interpolation.sineIn.apply(offset, 0, timer / pressDuration), 0, offset);
            final float padTop = offset - padBottom;
            setHeight(size.y - padTop);
            frontCell.padBottom(padBottom + pad);

            if (timer >= pressDuration)  {
                setHeight(size.y - offset);
                frontCell.padBottom(pad);
                pressing = false;
                timer = 0;
            }
        } else if (releasing) {
            final float padBottom = MathUtils.clamp(Interpolation.sineOut.apply(0, offset, timer / pressDuration), 0, offset);
            final float padTop = offset - padBottom;
            setHeight(size.y - padTop);
            frontCell.padBottom(padBottom + pad);

            if (timer >= pressDuration)  {
                setHeight(size.y);
                frontCell.padBottom(offset + pad);
                releasing = false;
                timer = 0;

                if (onTouchUp != null) {
                    onTouchUp.run();
                }
            }
        }

        invalidate();
    }


    private void setStyle (Style style) {
        this.style = style;

        setBackground(style.back.getDrawable(style.enabledColor));
        invalidatePadAndOffset();
    }

    private void invalidatePadAndOffset () {
        this.frontTable.setBackground(style.front.getDrawable(style.enabledColor));
        this.frontCell.pad(pad).padBottom(offset + pad);
    }

    public enum Style {
        GRAY(Squircle.SQUIRCLE_50, Squircle.SQUIRCLE_40, ColorLib.CADET_BLUE.getColor(), ColorLib.DARKISH_GREY.getColor()),
        RED(Squircle.SQUIRCLE_50, Squircle.SQUIRCLE_40, ColorLib.RED_FADED.getColor(), ColorLib.RED_DARK.getColor()),

        ;
        private Squircle front;
        private Squircle back;
        private Color enabledColor;
        private Color disabledColor;

        Style(Squircle front, Squircle back, Color enabledColor, Color backColor) {
            this.front = front;
            this.back = back;
            this.enabledColor = enabledColor;
            this.disabledColor = backColor;
        }
    }

}
