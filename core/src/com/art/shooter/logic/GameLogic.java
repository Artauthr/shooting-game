package com.art.shooter.logic;

import com.art.shooter.ui.GameUI;
import com.art.shooter.ui.dialogs.PauseDialog;
import lombok.Getter;

public class GameLogic {
    @Getter
    private boolean paused = false;

    public GameLogic () {

    }

    public void pauseGame () {
        this.paused = true;
        GameUI.showDialog(PauseDialog.class);
    }

    public void unPause () {
        this.paused = false;
        GameUI.hideDialog(PauseDialog.class);
    }


}
