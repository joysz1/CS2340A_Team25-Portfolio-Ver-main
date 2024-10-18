package com.example.a2340team25game.viewModel;


import android.view.Choreographer;

import androidx.lifecycle.ViewModel;


import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.Skeleton;
import com.example.a2340team25game.model.enemies.Slime;
import com.example.a2340team25game.model.enemies.Spider;
import com.example.a2340team25game.model.enemies.Zombie;

import java.util.Timer;
import java.util.TimerTask;

public class EnemyViewModel extends ViewModel implements EnemyObserver {
    private int xPos;
    private int yPos;
    private int startX;
    private int startY;
    private char directionMoving;
    //String enemyType;
    private Enemy enemy;

    private boolean isPlayerHit;


    public EnemyViewModel(int startX, int startY, Enemy enemy) {
        this.xPos = startX;
        this.yPos = startY;
        this.startX = startX;
        this.startY = startY;
        this.isPlayerHit = false;
        if (enemy instanceof Skeleton) {
            this.directionMoving = 'L';
        } else if (enemy instanceof Slime) {
            this.directionMoving = 'R';
        } else if (enemy instanceof Spider) {
            this.directionMoving = 'U';
        } else if (enemy instanceof Zombie) {
            this.directionMoving = 'D';
        }
        this.enemy = enemy;
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

    private Choreographer.FrameCallback movementCallback = new Choreographer.FrameCallback() {
        @Override
        public void doFrame(long frameTimeNanos) {
            if (enemy instanceof Skeleton || enemy instanceof Slime) {
                if (directionMoving == 'L') {
                    if (startX - xPos > 200) {
                        directionMoving = 'R';
                    } else {
                        xPos -= enemy.getMoveSpeed();
                    }
                } else if (directionMoving == 'R') {
                    if (xPos - startX > 200) {
                        directionMoving = 'L';
                    } else {
                        xPos += enemy.getMoveSpeed();
                    }
                }
            } else if (enemy instanceof Spider || enemy instanceof Zombie) {
                if (directionMoving == 'U') {
                    if (startY - yPos > 200) {
                        directionMoving = 'D';
                    } else {
                        yPos -= enemy.getMoveSpeed();
                    }
                } else if (directionMoving == 'D') {
                    if (yPos - startY > 200) {
                        directionMoving = 'U';
                    } else {
                        yPos += enemy.getMoveSpeed();
                    }
                }
            }
            //updating enemy position
            enemy.setXPos(xPos);
            enemy.setYPos(yPos);
            Choreographer.getInstance().postFrameCallback(this);
        }
    };
    public boolean getIsPlayerHit() {
        return this.isPlayerHit;
    }

    @Override
    public void updateEnemy(int playerX, int playerY) {
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
            if (!this.isPlayerHit) {
                this.enemy.attack();
                this.isPlayerHit = true;
                Timer timer = new Timer();
                timer.schedule(new HitTimeBuffer(), 5000);
            }
        }
    }

    private class HitTimeBuffer extends TimerTask {
        @Override
        public void run() {
            isPlayerHit = false;
        }
    }

    /*public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }*/


}
