package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;

public class SkeletonSpawner extends EnemySpawner {
    @Override
    public Enemy createEnemy() {
        return new Skeleton(5, 10, 15, new MoveInDirectionStrategy());
    }
}
