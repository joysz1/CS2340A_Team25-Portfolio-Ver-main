package com.example.a2340team25game.viewModel;


import android.view.Choreographer;

import androidx.lifecycle.ViewModel;

import com.example.a2340team25game.model.powerUps.PowerUp;


public class PowerUpViewModel extends ViewModel implements PowerUpObserver {
    private int xPos;
    private int yPos;
    private int startX;
    private int startY;
    private PowerUp powerUp;

    private boolean isPlayerHit;

    public PowerUpViewModel(int startX, int startY, PowerUp powerUp) {
        this.xPos = startX;
        this.yPos = startY;
        this.startX = startX;
        this.startY = startY;
        this.isPlayerHit = false;
        this.powerUp = powerUp;
        Choreographer.getInstance().postFrameCallback(movementCallback);
    }

    public int getXPos() {
        return this.xPos;
    }
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    public int getYPos() {
        return this.yPos;
    }
    public void seYPos(int yPos) {
        this.yPos = yPos;
    }
    public boolean getIsPlayerHit() {
        return this.isPlayerHit;
    }
    @Override
    public void updatePowerUp(int playerX, int playerY) {
        if ((playerX >= this.xPos) && (playerX <= this.xPos + 128) && (playerY >= this.yPos)
                && (playerY <= this.yPos + 128)
            || (playerX + 128 >= this.xPos) && (playerX + 128 <= this.xPos + 128)
                    && (playerY >= this.yPos) && (playerY <= this.yPos + 128)
            || (playerX >= this.xPos) && (playerX <= this.xPos + 128)
                    && (playerY + 128 >= this.yPos)
                    && (playerY + 128 <= this.yPos + 128)
            || (playerX + 128 >= this.xPos) && (playerX + 128 <= this.xPos + 128)
                    && (playerY + 128 >= this.yPos) && (playerY + 128 <= this.yPos + 128)) {
            //Log.v("Col", "Enemy Collision");
            powerUp.changeStat();
            this.xPos = 9999;
            this.yPos = 9999;
        }
    }

    private Choreographer.FrameCallback movementCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            Choreographer.getInstance().postFrameCallback(this);
        }
    };
}
