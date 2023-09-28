package com.art.shooter.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Resources {
    private AssetManager assetManager;

    public Resources () {
        assetManager = new AssetManager();
        assetManager.load("gameassets/gameatlas.atlas", TextureAtlas.class);
    }

    public void load () {
        if (assetManager.update(16)) {
        }
    }
}
