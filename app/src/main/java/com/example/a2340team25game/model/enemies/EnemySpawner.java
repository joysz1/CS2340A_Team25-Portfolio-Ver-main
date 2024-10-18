package com.example.a2340team25game.model.enemies;

import com.example.a2340team25game.view.GameActivity;

public abstract class EnemySpawner {
    public Enemy spawnEnemy() {
        Enemy newEnemy = createEnemy();
        GameActivity.addActiveEnemy(newEnemy); // Notify the GameActivity
        return newEnemy;
    }

    public abstract Enemy createEnemy();
}
