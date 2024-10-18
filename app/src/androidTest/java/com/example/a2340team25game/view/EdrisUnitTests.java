package com.example.a2340team25game.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import java.io.Serializable;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.a2340team25game.model.Player;
import com.example.a2340team25game.model.Score;
import com.example.a2340team25game.model.Leaderboard;
import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.Skeleton;

import com.example.a2340team25game.R;
import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

@RunWith(AndroidJUnit4.class)
public class EdrisUnitTests {

    private Player testPlayer;
    private Skeleton testEnemy;
    private Leaderboard testLeaderboard;

    @Before
    public void setupForGameLogicTests() {
        testPlayer = Player.getInstance();
        testEnemy = new Skeleton(100, 10, 10, null); // Sample enemy
        testLeaderboard = Leaderboard.getInstance();
        testPlayer.setScore(0, "TestPlayer");
        testLeaderboard.addOrUpdateScore(testPlayer.getScore());
    }

    @Before
    public void setUp() {
        // Initialize Espresso-Intents before each test
        Intents.init();
        moveInDirectionStrategy = new MoveInDirectionStrategy();

    }


    @After
    public void tearDown() {
        Intents.release();
    }


    @Test
    public void testScoreIncreasesOnAttack() {
        int initialScore = testPlayer.getScore().getValue();
        testPlayer.attack(testEnemy);
        int newScore = testPlayer.getScore().getValue();
        assertTrue("Score should increase after attacking an enemy", newScore > initialScore);
    }

    @Test
    public void testPlayerNameUpdate() {
        String expectedName = "TestPlayer";
        testPlayer.setName(expectedName);

        String actualName = testPlayer.getName();

        assertEquals("Player's name should be updated correctly", expectedName, actualName);
    }




    @Test
    public void endActivityDisplaysNoLeaderboardMessage_WhenLeaderboardIsNull() {
        // Create an Intent for EndActivity with a null Leaderboard
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), EndActivity.class);
        intent.putExtra("leaderboard", (Serializable) null); // Explicitly passing null with type casting

        try (ActivityScenario<EndActivity> scenario = ActivityScenario.launch(intent)) {
            onView(withId(R.id.score1)).check(matches(withText("Leaderboard data is not available.")));
        }
    }

    @Test
    public void endActivityDisplaysLostMessage_WhenPlayerLoses() {
        Leaderboard leaderboard = Leaderboard.getInstance();

        // Create an Intent for EndActivity
        Intent endActivityIntent = new Intent(ApplicationProvider.getApplicationContext(), EndActivity.class);
        endActivityIntent.putExtra("playerLost", true); // Indicate the player lost
        Score recentScore = new Score(100, "PlayerName");
        endActivityIntent.putExtra("recentScore", recentScore);

        try (ActivityScenario<EndActivity> scenario = ActivityScenario.launch(endActivityIntent)) {
            // Check if the text "You Lost!" is displayed
            onView(withText("You Lost!")).check(matches(isDisplayed()));
        }
    }


    @Test
    public void checkBackToMainButton_NavigatesToMainActivity() {
        // Obtain a Leaderboard instance
        Leaderboard leaderboard = Leaderboard.getInstance();

        // Create a Score object
        Score recentScore = new Score(100, "PlayerName");

        // Add the Score object to the Leaderboard
        leaderboard.addOrUpdateScore(recentScore);

        // Create an Intent with the necessary extras for EndActivity
        Intent endActivityIntent = new Intent(ApplicationProvider.getApplicationContext(), EndActivity.class);
        endActivityIntent.putExtra("leaderboard", leaderboard);
        endActivityIntent.putExtra("recentScore", recentScore);

        // Use ActivityScenario to launch EndActivity
        try (ActivityScenario<EndActivity> scenario = ActivityScenario.launch(endActivityIntent)) {

            // Verify that the 'Back To Home' button is displayed
            onView(withId(R.id.btnBackToMain))
                    .check(matches(isDisplayed()));

            onView(isRoot()).perform(closeSoftKeyboard());
            // Perform a click action on the 'Back To Home' button
            onView(withId(R.id.btnBackToMain)).perform(click());

            // Verify that the intended intent is to launch MainActivity
            intended(hasComponent(MainActivity.class.getName()));


        }
    }




    // Test 2
    @Test
    public void checkLeaderboardText_isDisplayed() {
        // Obtain a Leaderboard instance
        Leaderboard leaderboard = Leaderboard.getInstance();

        // Create a Score object
        Score recentScore = new Score(100, "PlayerName");

        // Add the Score object to the Leaderboard
        leaderboard.addOrUpdateScore(recentScore);

        // Create an Intent with the necessary extras
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), EndActivity.class);
        intent.putExtra("leaderboard", leaderboard);
        intent.putExtra("recentScore", recentScore);

        // Launch the current activity with the prepared Intent
        try (ActivityScenario<EndActivity> scenario = ActivityScenario.launch(intent)) {

            onView(withText("Name    Score    Date/Time"))
                    .check(matches(isDisplayed()));
        }
    }




    private MoveInDirectionStrategy moveInDirectionStrategy;



    @Test
    public void testMoveStart() throws NoSuchFieldException, IllegalAccessException {
        // Ensure a Looper is prepared for this thread.
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        char expectedDirection = 'U';
        moveInDirectionStrategy.moveStart(expectedDirection);

        Field field = MoveInDirectionStrategy.class.getDeclaredField("directionMoving");
        field.setAccessible(true);
        char actualDirection = (char) field.get(moveInDirectionStrategy);

        assertEquals(expectedDirection, actualDirection);
    }


    @Test
    public void testMoveStop() throws NoSuchFieldException, IllegalAccessException {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }

        // Start moving in a direction
        moveInDirectionStrategy.moveStart('U');

        // Stop the movement
        moveInDirectionStrategy.moveStop();

        Field field = MoveInDirectionStrategy.class.getDeclaredField("directionMoving");
        field.setAccessible(true);
        char actualDirection = (char) field.get(moveInDirectionStrategy);

        // checking if direction is changed to 0
        assertEquals('\0', actualDirection);
    }


}
