package com.art.shooter.logic;

import lombok.Getter;
import lombok.Setter;

public class GameLogic {
    @Getter
    private boolean paused = false;

    public GameLogic () {

    }

    public void pauseGame () {
        this.paused = true;
    }

    public void unPause () {
        this.paused = false;
    }


}
