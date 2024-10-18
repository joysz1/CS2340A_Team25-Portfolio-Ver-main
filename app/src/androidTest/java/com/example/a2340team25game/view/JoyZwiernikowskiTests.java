package com.example.a2340team25game.view;

// Using espresso to test our games ui elements
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.model.enemies.Skeleton;
import com.example.a2340team25game.model.enemies.SkeletonSpawner;
import com.example.a2340team25game.model.enemies.Spider;
import com.example.a2340team25game.model.enemies.SpiderSpawner;
import com.example.a2340team25game.model.powerUps.DefaultPowerUp;
import com.example.a2340team25game.model.powerUps.HealthPowerUp;
import com.example.a2340team25game.model.powerUps.SpeedPowerUp;
import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class JoyZwiernikowskiTests {

    @Before
    public void setUp() {
        // Initialize Espresso-Intents before each test
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    //Sprint 1 Test, no longer valid
//    @Test
//    public void nextButton_goesToMap1() {
//        // Launch the GameActivity
//        try (ActivityScenario<GameActivity> scenario = ActivityScenario.launch(GameActivity.class)) {
//            Espresso.onView(withId(R.id.nextButton)).perform(click());
//            // Verify that MainActivity was started
//            intended(IntentMatchers.hasComponent(Map1.class.getName()));
//        }
//    }

    //Verify that a blank name cannot continue to GameActivity
    @Test
    public void blankNameCannotGoNext() {
        try (ActivityScenario<ConfigurationActivity> scenario =
                     ActivityScenario.launch(ConfigurationActivity.class)) {
            Player.getInstance().setName("");
            Espresso.onView(withId(R.id.playButton)).perform(click());
            intended(IntentMatchers.hasComponent(ConfigurationActivity.class.getName()));
        }
    }

    @Test
    public void testRightCollision() {
        if (Player.getInstance() != null) {
            MoveInDirectionStrategy mids = new MoveInDirectionStrategy();
            mids.update("Right Collision");
            assertTrue(mids.isCollisionRight());
        }
    }

    @Test
    public void testLeftCollision() {
        if (Player.getInstance() != null) {
            MoveInDirectionStrategy mids = new MoveInDirectionStrategy();
            mids.update("Left Collision");
            assertTrue(mids.isCollisionLeft());
        }
    }

    @Test
    public void testSkeletonCreation() {
        SkeletonSpawner spawner = new SkeletonSpawner();
        Skeleton skeleton = (Skeleton) spawner.createEnemy();
        assertTrue(skeleton.getAttack() == 15);
        assertTrue(skeleton.getHealth() == 5);
        assertTrue(skeleton.getMoveSpeed() == 10);
    }

    @Test
    public void testSpiderCreation() {
        SpiderSpawner spawner = new SpiderSpawner();
        Spider spider = (Spider) spawner.createEnemy();
        assertTrue(spider.getAttack() == 5);
        assertTrue(spider.getHealth() == 5);
        assertTrue(spider.getMoveSpeed() == 15);
    }

    @Test
    public void testSpeedPowerUp() {
        SpeedPowerUp speed = new SpeedPowerUp(new DefaultPowerUp());
        speed.changeStat();
        assertTrue(Player.getInstance().getMoveSpeed() == 30);
    }

    @Test
    public void testHealthPowerUp() {
        HealthPowerUp health = new HealthPowerUp(new DefaultPowerUp());
        health.changeStat();
        assertTrue(Player.getInstance().getHealth() == 110);
    }
}
