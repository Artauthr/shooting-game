package com.art.shooter.ui;

import com.badlogic.gdx.graphics.Color;
import lombok.Getter;

public enum ColorLibrary {
    CHARCOAL_GRAY("#333333");

    ColorLibrary(String hex) {
        this.hex = hex;
    }


    private final String hex;



    public Color getColor() {
        return Color.valueOf(hex);
    }

}
