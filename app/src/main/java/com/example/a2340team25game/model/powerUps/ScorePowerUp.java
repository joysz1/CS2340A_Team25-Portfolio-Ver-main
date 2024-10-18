package com.example.a2340team25game.model.powerUps;

import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.view.GameActivity;

public class ScorePowerUp extends PowerUpDecorator {
    private int scoreChange;
    public ScorePowerUp(PowerUp powerUp) {
        super(powerUp);
        this.scoreChange = 100;
    }

    public void changeStat() {
        super.changeStat();
        int score = Player.getInstance().getScore().getValue();
        Player.getInstance().getScore().setValue(score + scoreChange);
        GameActivity.updateScoreDisplay();
    }

    public int getScoreChange() {
        return scoreChange;
    }

}
