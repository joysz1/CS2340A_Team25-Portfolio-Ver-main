package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;

public class SpiderSpawner extends EnemySpawner {
    @Override
    public Enemy createEnemy() {
        return new Spider(5, 15, 5, new MoveInDirectionStrategy());
    }
}
