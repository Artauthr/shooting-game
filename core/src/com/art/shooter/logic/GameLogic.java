package com.art.shooter.logic;

import lombok.Getter;
import lombok.Setter;

public class GameLogic {
    @Getter
    private boolean paused = false;

    private static GameLogic instance;
    private GameLogic () {

    }

    public static GameLogic getInstance () {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    public void pauseGame () {
        this.paused = true;
    }

    public void unPause () {
        this.paused = false;
    }


}
