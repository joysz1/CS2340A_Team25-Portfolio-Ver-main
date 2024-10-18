package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.model.Leaderboard;
import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.view.GameActivity;
import com.example.a2340team25game.viewModel.MovementStrategy;

public class Spider implements Enemy {
    private MovementStrategy movementStrategy;
    private int health;
    private int movementSpeed;
    private int attack;
    private int xPos;
    private int yPos;

    public Spider(int health, int movementSpeed, int attack, MovementStrategy movementStrategy) {
        this.health = health;
        this.movementSpeed = movementSpeed;
        this.attack = attack;
        this.movementStrategy = movementStrategy;
    }
    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    public void attack() {
        Player.getInstance().setHealth(Player.getInstance().getHealth() - attack);
        Player.getInstance().getScore()
                .setValue(Player.getInstance().getScore().getValue() - attack);
        Leaderboard.getInstance().addOrUpdateScore(Player.getInstance().getScore());
        GameActivity.updateScoreDisplay();
    }
    @Override
    public void reduceHealth(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            destroy();
        }
    }

    public void destroy() {
        GameActivity.removeActiveEnemy(this);

    }

    public int getMoveSpeed() {
        return this.movementSpeed;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }
    @Override
    public int getXPos() {
        return xPos;
    }

    @Override
    public int getYPos() {
        return yPos;
    }
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    @Override
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }


}
