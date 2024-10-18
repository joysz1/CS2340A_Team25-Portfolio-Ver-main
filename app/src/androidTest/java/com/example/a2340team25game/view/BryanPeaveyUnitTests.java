package com.example.a2340team25game.view;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.os.Looper;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.Skeleton;
import com.example.a2340team25game.model.enemies.SkeletonSpawner;
import com.example.a2340team25game.model.enemies.Slime;
import com.example.a2340team25game.model.enemies.SlimeSpawner;
import com.example.a2340team25game.model.enemies.Spider;
import com.example.a2340team25game.model.enemies.SpiderSpawner;
import com.example.a2340team25game.model.enemies.Zombie;
import com.example.a2340team25game.model.enemies.ZombieSpawner;
import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;
import com.example.a2340team25game.viewModel.PlayerViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;


public class BryanPeaveyUnitTests {
    @Before
    public void setUp() {
        // Initialize Espresso-Intents before each test
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    /*
    @Test
    public void nextButtonOnMap1() {

        try (ActivityScenario<Map1> scenario = ActivityScenario.launch(Map1.class)) {

            Espresso.onView(withId(R.id.nextButton))
                    .check(matches(isDisplayed()))
                    .check(matches(withText("NEXT")))
                    .perform(click());

            intended(IntentMatchers.hasComponent(Map1.class.getName()));
        }
    }
    @Test
    public void nextButtonOnMap2() {
        try (ActivityScenario<Map2> scenario = ActivityScenario.launch(Map2.class)) {

            Espresso.onView(withId(R.id.nextButton))
                    .check(matches(isDisplayed()))
                    .check(matches(withText("NEXT")))
                    .perform(click());

            intended(IntentMatchers.hasComponent(Map2.class.getName()));
        }
    } */

    private MoveInDirectionStrategy moveInDirectionStrategy;

    @Test
    public void rightMovementStart() throws NoSuchFieldException, IllegalAccessException {
        // Ensure a Looper is prepared for this thread.
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        char expectedDirection = 'R';
        moveInDirectionStrategy.moveStart(expectedDirection);

        Field field = MoveInDirectionStrategy.class.getDeclaredField("directionMoving");
        field.setAccessible(true);
        char actualDirection = (char) field.get(moveInDirectionStrategy);

        assertEquals(expectedDirection, actualDirection);
    }

    @Test
    public void rightMovementStop() throws NoSuchFieldException, IllegalAccessException {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // Start moving in a direction
        moveInDirectionStrategy.moveStart('R');

        // Stop the movement
        moveInDirectionStrategy.moveStop();

        Field field = MoveInDirectionStrategy.class.getDeclaredField("directionMoving");
        field.setAccessible(true);
        char actualDirection = (char) field.get(moveInDirectionStrategy);

        // checking if direction is changed to 0
        assertEquals('\0', actualDirection);
    }

    @Test
    public void difficultyDamage() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // checking if easy damage != medium damage
        PlayerViewModel player = new PlayerViewModel("x", 100, 2, 0);
        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy skel = ss.spawnEnemy();
        skel.attack();
        assertNotEquals(85, Player.getInstance().getHealth());
    }

    @Test
    public void enemyDifferences() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        PlayerViewModel player = new PlayerViewModel("x", 100, 1, 0);

        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy skel = ss.spawnEnemy();
        Skeleton skel2 = (Skeleton) skel;
        SlimeSpawner ss1 = new SlimeSpawner();
        Enemy slim = ss1.spawnEnemy();
        Slime slim2 = (Slime) slim;
        SpiderSpawner ss2 = new SpiderSpawner();
        Enemy spid = ss2.spawnEnemy();
        Spider spid2 = (Spider) spid;
        ZombieSpawner zs = new ZombieSpawner();
        Enemy zomb = zs.spawnEnemy();
        Zombie zomb2 = (Zombie) zomb;

        boolean skelSlim = (skel2.getHealth() != slim2.getHealth()) && (skel2.getMoveSpeed() != slim2.getMoveSpeed());
        boolean skelSpid = (skel2.getMoveSpeed() != spid2.getMoveSpeed()) && (skel2.getAttack() != spid2.getAttack());
        boolean skelZomb = (skel2.getHealth() != zomb2.getHealth()) && (skel2.getAttack() != zomb2.getAttack());
        boolean slimSpid = (slim2.getHealth() != spid2.getHealth()) && (slim2.getMoveSpeed() != spid2.getMoveSpeed());
        boolean slimZomb = (slim2.getHealth() != zomb2.getHealth()) && (slim2.getMoveSpeed() != zomb2.getMoveSpeed());
        boolean spidZomb = (spid2.getHealth() != zomb2.getHealth()) && (spid2.getMoveSpeed() != zomb2.getMoveSpeed());

        boolean allDifferences = skelSlim && skelSpid && skelZomb && slimSpid && slimZomb && spidZomb;

        // checking that every enemy differs by at least two attributes
        assertEquals(true, allDifferences);
    }

    /*
     * Assures that the score is lowered by the amount of health that is lost.
     */
    @Test
    public void scoreLostHealth() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        PlayerViewModel player = new PlayerViewModel("x", 100, 1, 0);

        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy giveScore = ss.spawnEnemy();
        Player.getInstance().attack(giveScore);

        int initialScore = Player.getInstance().getScore().getValue(); // 50

        Enemy skel = ss.spawnEnemy();
        skel.attack();

        int skelDamage = 15;
        int finalScore = Player.getInstance().getScore().getValue();

        assertNotEquals(initialScore, finalScore);
        assertEquals(initialScore - finalScore, skelDamage);

        initialScore = Player.getInstance().getScore().getValue(); // 35

        ZombieSpawner zs = new ZombieSpawner();
        Enemy zomb = zs.spawnEnemy();
        zomb.attack();

        int zombDamage = 10;
        finalScore = Player.getInstance().getScore().getValue();
        
        assertEquals(initialScore - finalScore, zombDamage);
    } // scoreLostHealth

    /*
     * Assures that the score is increased by 50 points upon destroying an enemy.
     */
    @Test
    public void scoreDestroy() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        PlayerViewModel player = new PlayerViewModel("x", 15, 1, 0);
        int initialScore = Player.getInstance().getScore().getValue();

        SkeletonSpawner ss = new SkeletonSpawner();
        Enemy skel = ss.spawnEnemy();
        Player.getInstance().attack(skel);

        int killPts = 50;
        int finalScore = Player.getInstance().getScore().getValue();

        assertNotEquals(initialScore, finalScore);
        assertEquals(finalScore - initialScore, killPts);

    } // scoreKill
}
