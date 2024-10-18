package com.example.a2340team25game.model;


import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.view.GameActivity;

import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;
import com.example.a2340team25game.viewModel.MovementStrategy;


public class Player implements MovementStrategy {
    private static Player player;
    private String name;
    private int health;
    private int difficulty;
    private int characterID;
    private Score score;
    private int xPos;
    private int yPos; // Character's position
    private static final int SPRITE_HEIGHT_AND_WIDTH = 128;
    private int pointsForDefeatingEnemy = 50;   // points given for killing enemy

    private int moveSpeed;
    private MovementStrategy movementStrategy;
    private String weaponType; // Character's weapon

    private Player() {
        this.xPos = 0;
        this.yPos = 0;
        this.moveSpeed = 25;
        this.setMovementStrategy(new MoveInDirectionStrategy());

    }

    public static Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    public void setMovementStrategy(MovementStrategy strategy) {
        this.movementStrategy = strategy;
    }

    public void moveStart(char direction) {
        if (movementStrategy != null) {
            movementStrategy.moveStart(direction);
        }
    }

    public void moveStop() {
        if (movementStrategy != null) {
            movementStrategy.moveStop();
        }
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setHealth(int health) {
        if (health > 100) {
            this.health = 100; // limiting the health at 100
        } else if (health < 0) {
            this.health = 0; // ensure health doesn't go below 0
        } else {
            this.health = health;
        }
    }


    public int getHealth() {
        return this.health;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public int getCharacterID() {
        return this.characterID;
    }
    public void setScore(int value, String name) {
        if (this.score == null) {
            this.score = new Score(value, name);
        } else {
            this.score.setValue(value);
            if (!this.score.getName().equals(name)) {
                this.score.setName(name);
            }
        }
    } // setScore

    public Score getScore() {
        if (this.score == null) {
            score = new Score(0, this.name);
        } // if
        return this.score;
    } // getScore

    public void resetScore() {
        this.score = new Score(0, this.name);
    }

    /**
     * Attacks the given enemy.
     * @param enemy The enemy to attack.
     */
    public void attack(Enemy enemy) {
        if (enemy != null) {
            enemy.reduceHealth(100);
            if (enemy.getHealth() <= 0) {
                // when enemy is defeated, increase score
                Player.getInstance().getScore().setValue(this.score.getValue()
                        + pointsForDefeatingEnemy);
                Leaderboard.getInstance().addOrUpdateScore(this.getScore());
                // Update the score display
                GameActivity.updateScoreDisplay();
            }
        }
    }


    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    public int getXPos() {
        return this.xPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    public int getYPos() {
        return this.yPos;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public int getMoveSpeed() {
        return this.moveSpeed;
    }

    public MovementStrategy getMovementStrategy() {
        return this.movementStrategy;
    }
}
