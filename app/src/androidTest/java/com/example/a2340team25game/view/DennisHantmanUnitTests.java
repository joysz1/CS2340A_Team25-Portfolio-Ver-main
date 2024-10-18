package com.example.a2340team25game.view;

import static org.junit.Assert.assertEquals;

import android.os.Looper;

import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.SlimeSpawner;
import com.example.a2340team25game.model.enemies.SpiderSpawner;
import com.example.a2340team25game.model.enemies.ZombieSpawner;
import com.example.a2340team25game.model.powerUps.DefaultPowerUp;
import com.example.a2340team25game.model.powerUps.HealthPowerUp;
import com.example.a2340team25game.model.powerUps.SpeedPowerUp;
import com.example.a2340team25game.view.ConfigurationActivity;
import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;
import com.example.a2340team25game.viewModel.PlayerViewModel;

import org.junit.Test;

import java.lang.reflect.Field;

public class DennisHantmanUnitTests {

    @Test
    public void testHealthDifficulty() {
        if (Player.getInstance() != null && Player.getInstance().getDifficulty() != 0) {
             int difficulty = Player.getInstance().getDifficulty();
             int health = Player.getInstance().getHealth();

            switch (difficulty) {
                case 1: assertEquals(health, 100);
                    break;
                case 2: assertEquals(health, 75);
                    break;
                case 3: assertEquals(health, 50);
            }
        }
    }

    @Test
    public void testCharacterID() {
        if (Player.getInstance() != null) {
            int selectedCharID = ConfigurationActivity.getSelectedCharacterID();
            Player.getInstance().setCharacterID(selectedCharID);
            int playerCharID = Player.getInstance().getCharacterID();

            assertEquals(playerCharID, selectedCharID);
        }
    }

    @Test
    public void testMovePlayerDown() throws NoSuchFieldException, IllegalAccessException {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        MoveInDirectionStrategy moveInDirectionStrategy = new MoveInDirectionStrategy();
        moveInDirectionStrategy.moveStart('D');

        Field field = MoveInDirectionStrategy.class.getDeclaredField("directionMoving");
        field.setAccessible(true);
        char direction = (char) field.get(moveInDirectionStrategy);
        assertEquals('D', direction);
    }

    @Test
    public void testMovePlayerLeft() throws NoSuchFieldException, IllegalAccessException {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        MoveInDirectionStrategy moveInDirectionStrategy = new MoveInDirectionStrategy();
        moveInDirectionStrategy.moveStart('L');

        Field field = MoveInDirectionStrategy.class.getDeclaredField("directionMoving");
        field.setAccessible(true);
        char direction = (char) field.get(moveInDirectionStrategy);
        assertEquals('L', direction);
    }

    @Test
    public void testSlimeMultipleAttacks() {
        PlayerViewModel player = new PlayerViewModel("test", 50, 1, 0);
        SlimeSpawner slimeSpawner = new SlimeSpawner();
        Enemy slime = slimeSpawner.spawnEnemy();
        slime.attack();
        slime.attack();
        assertEquals(40, Player.getInstance().getHealth());
        slime.attack();
        slime.attack();
        assertEquals(30, Player.getInstance().getHealth());
        slime.attack();
        assertEquals(25, Player.getInstance().getHealth());
    }

    @Test
    public void testMultipleEnemyAttack() {
        PlayerViewModel player = new PlayerViewModel("test", 50, 1, 0);
        ZombieSpawner zombieSpawner = new ZombieSpawner();
        Enemy zombie = zombieSpawner.spawnEnemy();
        SpiderSpawner spiderSpawner = new SpiderSpawner();
        Enemy spider = spiderSpawner.spawnEnemy();
        Enemy spider2 = spiderSpawner.spawnEnemy();
        zombie.attack();
        assertEquals(40, Player.getInstance().getHealth());
        spider.attack();
        assertEquals(35, Player.getInstance().getHealth());
        spider2.attack();
        assertEquals(30, Player.getInstance().getHealth());
    }

    @Test
    public void testSpeedPowerUp() {
        PlayerViewModel playerVM = new PlayerViewModel("test", 50, 1, 0);
        SpeedPowerUp speed = new SpeedPowerUp(new DefaultPowerUp());
        speed.changeStat();
        assertEquals(30, Player.getInstance().getMoveSpeed());
        speed.changeStat();
        SpeedPowerUp speed2 = new SpeedPowerUp(new DefaultPowerUp());
        speed2.changeStat();
        assertEquals(40, Player.getInstance().getMoveSpeed());
    }

    @Test
    public void testHealthPowerUp() {
        PlayerViewModel playerVM = new PlayerViewModel("test", 50, 1, 0);
        HealthPowerUp health = new HealthPowerUp(new DefaultPowerUp());
        health.changeStat();
        assertEquals(60, Player.getInstance().getHealth());
        health.changeStat();
        HealthPowerUp health2 = new HealthPowerUp(new DefaultPowerUp());
        health2.changeStat();
        assertEquals(80, Player.getInstance().getHealth());
    }
}
