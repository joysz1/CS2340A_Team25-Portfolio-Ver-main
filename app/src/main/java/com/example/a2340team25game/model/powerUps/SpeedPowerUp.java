package com.example.a2340team25game.model.powerUps;

import com.example.a2340team25game.model.Player;

public class SpeedPowerUp extends PowerUpDecorator {
    private int speedChange;
    public SpeedPowerUp(PowerUp powerUp) {
        super(powerUp);
        this.speedChange = 5;
    }

    public void changeStat() {
        super.changeStat();
        Player.getInstance().setMoveSpeed(Player.getInstance().getMoveSpeed() + speedChange);
    }

}
