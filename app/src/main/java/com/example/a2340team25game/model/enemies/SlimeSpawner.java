package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;

public class SlimeSpawner extends EnemySpawner {
    @Override
    public Enemy createEnemy() {
        return new Slime(1, 5, 5, new MoveInDirectionStrategy());
    }
}
