package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;

public class ZombieSpawner extends EnemySpawner {
    @Override
    public Enemy createEnemy() {
        return new Zombie(10, 10, 10, new MoveInDirectionStrategy());
    }
}
