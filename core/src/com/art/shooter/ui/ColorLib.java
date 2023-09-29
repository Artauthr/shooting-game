package com.art.shooter.ui;

import com.badlogic.gdx.graphics.Color;

public enum ColorLib {
    CHARCOAL_GRAY("#333333"),
    DARKISH_GREY("#57524B"),
    CADET_BLUE("#acb3bf"),
    RED_FADED("#632929"),
    RED_DARK("#261010"),
    POWERSHELL_BLUE("#04121f"),
    ;

    ColorLib(String hex) {
        this.hex = hex;
    }


    private final String hex;



    public Color getColor() {
        return Color.valueOf(hex);
    }

}
