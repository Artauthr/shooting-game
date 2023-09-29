package com.art.shooter.ui;

import com.art.shooter.ui.buttons.ImageOffsetButton;
import com.art.shooter.ui.buttons.OffsetButton;
import com.art.shooter.utils.Resources;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import lombok.Getter;

public abstract class ADialog extends Table {
    // title
    protected Table titleSegment;
    protected Label titleLabel;

    // content
    protected Table contentWrapper;
    protected Table content;

    @Getter
    protected ImageOffsetButton closeButton;

    @Getter
    protected Table dialog;
    protected Table overlayTable;

    @Getter
    private boolean shown;
    protected float duration = 0.08f;

    protected boolean selfHide = true;

    public ADialog() {
        initDialog();

        initCloseButton();

        // init overlay
        overlayTable = new Table();
        constructOverlay(overlayTable);

        // init title segment
        titleSegment = new Table();
        constructTitleSegment(titleSegment);

        // init content
        content = new Table();
        constructContent(content);

        // wrap content
        contentWrapper = constructContentWrapper();

        // assemble dialog
        dialog = new Table();
        constructDialog(dialog);

        constructLayout();

    }

    protected void constructLayout() {
        add(dialog);
    }

    protected void constructOverlay(Table overlayTable) {
        overlayTable.add(closeButton).expand().top().right().pad(20).padTop(18);
    }

    protected void initCloseButton () {
        // init close button
        final Drawable drawable = Resources.obtainDrawable("ui-close-icon");
        closeButton = new ImageOffsetButton(OffsetButton.Style.RED, drawable);
        closeButton.setOnTouchUp(this::hide);
    }

    protected void initDialog() {
        setBackground(Resources.obtainDrawable("ui-white-pixel", Color.valueOf("#10101070")));
        setTouchable(Touchable.enabled);
        setFillParent(true);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getTarget() == ADialog.this && selfHide) {
                    hide();
                }
            }
        });
    }

    protected Table constructContentWrapper() {
        final Table contentWrapper = new Table();
        contentWrapper.add(titleSegment).growX();
        contentWrapper.row();
        contentWrapper.add(content).grow();
        return contentWrapper;
    }

    protected void constructDialog(Table dialog) {
        dialog.setBackground(getDialogBackground());
        dialog.setTouchable(Touchable.enabled);
        dialog.stack(contentWrapper, overlayTable).grow();
    }

    protected Drawable getDialogBackground() {
        return Squircle.SQUIRCLE_50.getDrawable(ColorLib.POWERSHELL_BLUE.getColor());
    }

    protected void constructTitleSegment(Table titleSegment) {
        final Label.LabelStyle regularLabelStyle = GameUI.get().getRegularLabelStyle();
        titleLabel = new Label(getDialogTitle(), regularLabelStyle);
        titleLabel.setColor(getTitleFontColor());
        titleLabel.setScale(getTitleFontScale());

        titleSegment.add(titleLabel).pad(50);
    }

    protected float getTitleFontScale () {
        return 1;
    }

    protected Color getTitleFontColor() {
        return ColorLib.CADET_BLUE.getColor();
    }

    protected String getDialogTitle() {
        return "";
    }

    protected abstract void constructContent(Table content);

    public void show() {
        GameUI.get().getDialogContainer().addActor(this);

        shown = true;

        dialog.pack();
        dialog.setTransform(true);
        dialog.setScale(0.9f);
        dialog.getColor().a = 0.0f;
        dialog.setOrigin(Align.center);
        dialog.clearActions();

        dialog.addAction(Actions.parallel(
                Actions.fadeIn(duration),
                Actions.sequence(
                        Actions.scaleTo(1f, 1f, duration),
                        Actions.run(() -> dialog.setTransform(false))
                )
        ));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    public void hide() {
        shown = false;
        clearActions();
        remove();
    }

    public void hideCloseButton() {
        closeButton.setVisible(false);
    }

    public void showCloseButton() {
        closeButton.setVisible(true);
    }
}
