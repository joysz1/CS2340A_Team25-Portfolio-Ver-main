package com.example.a2340team25game.model.powerUps;

import com.example.a2340team25game.model.Player;

public class HealthPowerUp extends PowerUpDecorator {
    private int healthChange;
    public HealthPowerUp(PowerUp powerUp) {
        super(powerUp);
        this.healthChange = 10;
    }

    public void changeStat() {
        super.changeStat();
        Player.getInstance().setHealth(Player.getInstance().getHealth() + healthChange);
    }

}
