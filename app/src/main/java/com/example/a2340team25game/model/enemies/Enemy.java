package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.viewModel.MovementStrategy;

public interface Enemy {
    void setMovementStrategy(MovementStrategy strategy);
    MovementStrategy getMovementStrategy();


    void attack();

    void reduceHealth(int damage);

    int getMoveSpeed();
    int getXPos();
    int getYPos();
    int getHealth();
    void setXPos(int xPos);
    void setYPos(int yPos);
}

