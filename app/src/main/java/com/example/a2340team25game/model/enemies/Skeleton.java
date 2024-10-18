package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.model.Leaderboard;
import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.model.Score;
import com.example.a2340team25game.view.GameActivity;
import com.example.a2340team25game.viewModel.MovementStrategy;

public class Skeleton implements Enemy {
    private MovementStrategy movementStrategy;
    private int health;
    private int movementSpeed;
    private int attack;
    private int xPos;
    private int yPos;

    public Skeleton(int health, int movementSpeed, int attack, MovementStrategy movementStrategy) {
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
        Player player = Player.getInstance();

        // reduce the player's health
        int newHealth = player.getHealth() - (attack + ((player.getDifficulty()) - 1) * attack);
        player.setHealth(newHealth);

        // reduce the score, but not below 0
        Score currentScore = player.getScore();
        int scoreValue = currentScore.getValue();
        if (scoreValue > attack) {
            currentScore.setValue(scoreValue - attack);
        } else {
            // ensure the score does not go below 0
            currentScore.setValue(0);
        }

        // update leaderboard and score display
        Leaderboard.getInstance().addOrUpdateScore(currentScore);
        GameActivity.updateScoreDisplay();
    }


    @Override
    public void reduceHealth(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            destroy();
        }
    }
    private void destroy() {
        GameActivity.removeActiveEnemy(this);

    }




    @Override
    public int getMoveSpeed() {
        return this.movementSpeed;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
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
