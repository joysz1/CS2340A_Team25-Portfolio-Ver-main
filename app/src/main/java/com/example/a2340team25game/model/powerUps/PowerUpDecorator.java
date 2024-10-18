package com.example.a2340team25game.model.powerUps;

public class PowerUpDecorator implements PowerUp {
    protected PowerUp powerUp;

    public PowerUpDecorator(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public void changeStat() {
        this.powerUp.changeStat();
    }
}
