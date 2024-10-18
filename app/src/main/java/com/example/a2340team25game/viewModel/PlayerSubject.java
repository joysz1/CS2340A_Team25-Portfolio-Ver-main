package com.example.a2340team25game.viewModel;


public interface PlayerSubject {
    void registerEnemyObserver(EnemyObserver enemyObserver);
    void removeEnemyObserver(EnemyObserver enemyObserver);
    void notifyEnemyObservers(int playerX, int playerY);
    void registerPowerUpObserver(PowerUpObserver powerUpObserver);
    void removePowerUpObserver(PowerUpObserver powerUpObserver);
    void notifyPowerUpObservers(int playerX, int playerY);
}
