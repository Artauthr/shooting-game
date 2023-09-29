package com.art.shooter.utils;

import com.art.shooter.logic.API;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ObjectMap;
import lombok.Getter;

public class Resources {
    private boolean loading = false; // is loading right now
    private final ObjectMap<String, Drawable> drawableCache;
    private Skin uiSkin;

    @Getter
    private boolean loaded = false; //completely finished loading
    @Getter
    private AssetManager assetManager;

    public Resources () {
        assetManager = new AssetManager();
        drawableCache = new ObjectMap<>();
    }

    public void queueLoading () {
        assetManager.load("gameassets/gameatlas.atlas", TextureAtlas.class);
        loading = true;
    }

    public void updateLoading () {
        boolean complete = assetManager.update(16);
        if (complete) {
            if (loading) {
                loading = false;
                finishLoad();
            }
        }
    }

    private void finishLoad () {
        TextureAtlas atlas = assetManager.get("gameassets/gameatlas.atlas", TextureAtlas.class);
        this.uiSkin = new Skin(atlas);
        loaded = true;
        System.out.println("Finished Loading Resources");
    }

    public static Drawable obtainDrawable (String region) {
        return obtainDrawable(region, Color.WHITE);
    }

    public static Drawable obtainDrawable (String region, Color color) {
        final Resources resources = API.get(Resources.class);

        if (resources.drawableCache.containsKey(region)) {
            return resources.drawableCache.get(region);
        }

        final Drawable drawable = resources.uiSkin.newDrawable(region, color);
        resources.drawableCache.put(region, drawable);
        return drawable;
    }


}
