package com.example.a2340team25game.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.Skeleton;
import com.example.a2340team25game.model.enemies.SkeletonSpawner;
import com.example.a2340team25game.viewModel.EnemyViewModel;
import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;
import com.example.a2340team25game.viewModel.PlayerViewModel;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.os.Looper;

public class ChristopherJalenJacksonUnitTests {

    @Test
    public void testDifficultyNotZero() {
        if (Player.getInstance() != null) {
            assertNotEquals(null, Player.getInstance().getDifficulty());
        }
    }

    @Test
    public void testCharacterIDNotNull() {
        if (Player.getInstance() != null) {
            assertNotEquals(null, Player.getInstance().getCharacterID());
        }
    }

    @Test
    public void testTopCollision() {
        if (Player.getInstance() != null) {
            MoveInDirectionStrategy mids = new MoveInDirectionStrategy();
            mids.update("Top Collision");
            assertEquals(true, mids.isCollisionTop());
        }
    }

    @Test
    public void testBottomCollision() {
        if (Player.getInstance() != null) {
            MoveInDirectionStrategy mids = new MoveInDirectionStrategy();
            mids.update("Bottom Collision");
            assertEquals(true, mids.isCollisionBottom());
        }
    }

    @Test
    public void testEnemyAttack() {
        PlayerViewModel player = new PlayerViewModel("x", 100, 1, 0);
        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy skel = ss.spawnEnemy();
        skel.attack();
        assertEquals(85, Player.getInstance().getHealth());
    }

    @Test
    public void testPlayerEnemyCollision() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        PlayerViewModel player = new PlayerViewModel("x", 100, 1, 0);
        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy skel = ss.spawnEnemy();
        EnemyViewModel skelVM = new EnemyViewModel(100, 100, skel);
        Player.getInstance().setXPos(100);
        Player.getInstance().setYPos(100);
        skelVM.updateEnemy(Player.getInstance().getXPos(), Player.getInstance().getYPos());
        assertEquals(true, skelVM.getIsPlayerHit());
    }

    @Test
    public void testEnemyReduceHealthCorrectly() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy skel = ss.spawnEnemy();
        EnemyViewModel skelVM = new EnemyViewModel(100, 100, skel);
        int health = skel.getHealth();
        skel.reduceHealth(10);
        assertEquals(health - 10, skel.getHealth());
    }

    @Test
    public void testEnemyXPosSetCorrectly() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy skel = ss.spawnEnemy();
        EnemyViewModel skelVM = new EnemyViewModel(0, 200, skel);
        assertEquals(0, skel.getXPos());
    }
}
