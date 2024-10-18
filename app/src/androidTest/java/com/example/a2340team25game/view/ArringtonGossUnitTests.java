package com.example.a2340team25game.view;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.SkeletonSpawner;
import com.example.a2340team25game.model.enemies.SpiderSpawner;
import com.example.a2340team25game.model.powerUps.DefaultPowerUp;
import com.example.a2340team25game.model.powerUps.ScorePowerUp;
import com.example.a2340team25game.viewModel.EnemyViewModel;
import com.example.a2340team25game.viewModel.PlayerViewModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
//import org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;


public class ArringtonGossUnitTests {

    @Test
    public void testScorePowerUpIncreasesScore() {
        ScorePowerUp score = new ScorePowerUp(new DefaultPowerUp());
        score.changeStat();
        assertTrue(Player.getInstance().getScore().getValue() == 200);
    }

    @Test
    public void testScorePowerUpCreation() {
        ScorePowerUp score = new ScorePowerUp(new DefaultPowerUp());
        assertTrue(score.getScoreChange() == 100);
    }
    @Test
    public void testEnemyAttackWithDifferentPlayerHealth() {
        Player player = Player.getInstance();
        player.setHealth(150);
        SpiderSpawner ss = new SpiderSpawner();
        Enemy spider = ss.spawnEnemy();
        spider.attack();
        assertEquals(145, player.getHealth());
    }
    @Test
    public void testPlayerHealthAfterMultipleEnemyAttacks() {
        Player player = Player.getInstance();
        player.setHealth(100);
        SpiderSpawner ss = new SpiderSpawner();
        Enemy spider = ss.spawnEnemy();


        spider.attack();
        spider.attack();
        spider.attack();

        assertEquals(85, player.getHealth());
    }
}
//    @Test
//    public void testWhiteSpaceOnlyName() {
//        if (Player.getInstance() != null) {
//            Player.getInstance().setName("TestName");
//            String player = Player.getInstance().getName();
//            assertNotEquals(player.trim().length(), 0);
//        }
//    }
//
//    @Test
//    public void testEmptyOrNullName() {
//        if (Player.getInstance() != null) {
//            Player.getInstance().setName("TestName");
//            assertNotEquals(Player.getInstance().getName(),"");
//            assertNotEquals(Player.getInstance().getName(), null);
//        }
//    }
//
//    @Test
//    public void testRestartGameButton() {
//
//        try (ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class)) {
//            onView(withId(R.id.btnBackToMain))
//                    .check(matches(isDisplayed()))
//                    .check(matches(withText("BACK TO HOME")))
//                    .perform(click());
//
//            intended(IntentMatchers.hasComponent(MainActivity.class.getName()));
//        }
//    }
//    @Test
//    public void testCorrectLeaderboardScore() {
//        Leaderboard leaderboard = Leaderboard.getInstance();
//        String playerName = "Arrington";
//
//        Score playerScore = new Score(100, playerName);
//        leaderboard.add(playerScore);
//
//        int leaderboardScore = leaderboard.get(playerScore).getValue();
//
//        assertEquals(playerScore, leaderboardScore);
//    }
//
//
//}
