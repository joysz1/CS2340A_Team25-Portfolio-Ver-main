package com.example.a2340team25game.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.util.Log;
//import android.view.LayoutInflater;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.LinearLayout.LayoutParams;
//import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Leaderboard;
import com.example.a2340team25game.model.Player;
//import com.example.a2340team25game.model.Score;
import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.EnemySpawner;
import com.example.a2340team25game.model.enemies.SkeletonSpawner;
import com.example.a2340team25game.model.enemies.SlimeSpawner;
import com.example.a2340team25game.model.enemies.SpiderSpawner;
import com.example.a2340team25game.model.enemies.ZombieSpawner;
import com.example.a2340team25game.model.powerUps.DefaultPowerUp;
import com.example.a2340team25game.model.powerUps.HealthPowerUp;
import com.example.a2340team25game.model.powerUps.PowerUp;
import com.example.a2340team25game.model.powerUps.ScorePowerUp;
import com.example.a2340team25game.model.powerUps.SpeedPowerUp;
import com.example.a2340team25game.viewModel.EnemyObserver;
import com.example.a2340team25game.viewModel.MoveInDirectionStrategy;
//import com.example.a2340team25game.viewModel.MovementStrategy;
import com.example.a2340team25game.viewModel.Observer;
import com.example.a2340team25game.viewModel.PlayerSubject;
import com.example.a2340team25game.viewModel.PlayerViewModel;
import com.example.a2340team25game.viewModel.PowerUpObserver;
import com.example.a2340team25game.viewModel.Subject;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements Subject, PlayerSubject {

    private TextView healthText;
    private TextView nameText;
    private TextView difficultyText;
    //private ImageView charView;
    private Context gameContext;
    private PlayerView playerView;
    private String healthString;
    private String nameString;
    private String difficultyString;
    private Button endButton;
    //private Button nextButton;
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button attackButton;
    private static TextView scoreDisplay;
    private int time = 0;
    private static final int ATTACK_RANGE = 200; // player's attack range

    private boolean gameEnded = false;
    private Leaderboard leaderboard;
    private MapView mapView;
    //int currentScene = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private static final int FRAME_RATE = 60; // Frames per second
    private Handler frameHandler;
    private static AssetManager assetManager;
    private Intent receivedIntent;
    private PlayerViewModel playerViewModel;
    private List<Observer> observers = new ArrayList<>();

    private List<EnemyObserver> enemyObservers = new ArrayList<>();
    private List<PowerUpObserver> powerUpObservers = new ArrayList<>();
    private ViewGroup parentLayout;
    private ViewGroup.LayoutParams layoutParams;
    private EnemySpawner enemySpawner; //skeletonSpawner, slimeSpawner, spiderSpawner, zombieSpawner
    private EnemyView enemyView1;
    private EnemyView enemyView2; //skeletonView1, slimeView1, spiderView1, zombieView1;

    private Enemy enemy1;
    private Enemy enemy2; //skeleton1, slime1, spider1, zombie1;
    private PowerUpView powerView;
    private PowerUp healthPower;
    private PowerUp speedPower;
    private PowerUp scorePower;

    private static List<Enemy> activeEnemies = new ArrayList<>();


    public static void addActiveEnemy(Enemy enemy) {
        activeEnemies.add(enemy);
    }

    public static void removeActiveEnemy(Enemy enemy) {
        activeEnemies.remove(enemy);
    }



    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        gameContext = this;

        receivedIntent = getIntent();

        assetManager = getApplicationContext().getAssets();

        setContentView(R.layout.activity_game);

        mapView = new MapView(this, "Map1.csv", 1);

        playerViewModel = new PlayerViewModel(receivedIntent.getStringExtra("name"),
                receivedIntent.getIntExtra("health", 0),
                receivedIntent.getIntExtra("difficulty", 0),
                receivedIntent.getIntExtra("character", 0));

        registerObserver((MoveInDirectionStrategy) Player.getInstance().getMovementStrategy());

        playerView = new PlayerView(this);

        Player.getInstance().setXPos(1210);
        Player.getInstance().setYPos(600);
        Player.getInstance().resetScore();

        frameHandler = new Handler();

        enemySpawner = new SkeletonSpawner();
        enemy1 = enemySpawner.spawnEnemy();

        // Set initial positions of the enemy
        enemy1.setXPos(1210);
        enemy1.setYPos(1376);

        enemyView1 = new EnemyView(this, enemy1.getXPos(), enemy1.getYPos(), enemy1);

        healthPower = new HealthPowerUp(new DefaultPowerUp());
        speedPower = new SpeedPowerUp(new DefaultPowerUp());
        scorePower = new ScorePowerUp(new DefaultPowerUp());
        powerView = new PowerUpView(this, 2000, 1376, healthPower);
        registerPowerUpObserver(powerView.getPowerUpViewModel());


        registerEnemyObserver(enemyView1.getEnemyViewModel());

        parentLayout = findViewById(R.id.gameScreen);

        layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        parentLayout.addView(mapView, layoutParams);

        leaderboard = (Leaderboard) getIntent().getExtras().getSerializable("leaderboard");

        //time = 100;
        scoreDisplay = findViewById(R.id.scoreDisplay);
        updateScoreDisplay();

        //health display
        healthText = findViewById(R.id.health);
        healthString = "Health: " + Player.getInstance().getHealth();
        healthText.setText(healthString);

        //name display
        nameText = findViewById(R.id.name);
        nameString = "Name: " + Player.getInstance().getName();
        nameText.setText(nameString);

        //difficulty display
        if (Player.getInstance().getCharacterID() == 1) {
            difficultyString = ("Difficulty: Easy");
        } else if (Player.getInstance().getCharacterID() == 2) {
            difficultyString = ("Difficulty: Medium");
        } else if (Player.getInstance().getCharacterID() == 3) {
            difficultyString = ("Difficulty: Hard");
        }

        difficultyText = findViewById(R.id.difficulty);
        difficultyText.setText(difficultyString);

        parentLayout.addView(playerView, layoutParams);
        parentLayout.addView(enemyView1, layoutParams);
        parentLayout.addView(powerView, layoutParams);

        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        attackButton = findViewById(R.id.attackButton);

        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAttack();
            }
        });
        upButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Player.getInstance().moveStart('U');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Player.getInstance().moveStop();
                }
                return true;
            }
        });
        downButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Player.getInstance().moveStart('D');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Player.getInstance().moveStop();
                }
                return true;
            }
        });
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Player.getInstance().moveStart('L');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Player.getInstance().moveStop();
                }
                return true;
            }
        });
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Player.getInstance().moveStart('R');
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    Player.getInstance().moveStop();
                }
                return true;
            }
        });
        frameHandler.postDelayed(frameUpdateRunnable, 1000 / FRAME_RATE);
    }


    public static AssetManager getAssetManager() {
        return assetManager;
    }

    public static void updateScoreDisplay() {
        if (scoreDisplay != null && Player.getInstance().getScore() != null) {
            scoreDisplay.post(new Runnable() {
                @Override
                public void run() {
                    scoreDisplay.setText("Score: " + Player.getInstance().getScore().getValue());
                }
            });
        }
    }
    private Runnable frameUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            collisionHandling();
            notifyEnemyObservers(Player.getInstance().getXPos(), Player.getInstance().getYPos());

            notifyPowerUpObservers(Player.getInstance().getXPos(), Player.getInstance().getYPos());


            playerView.invalidate();
            checkAndDestroyEnemies();
            frameHandler.postDelayed(this, 1000 / FRAME_RATE);


            checkSwitchMap();

            healthString = "Health: " + Player.getInstance().getHealth();
            healthText.setText(healthString);

            if (Player.getInstance().getHealth() <= 0 && !gameEnded) {
                gameEnded = true;
                endGame(Player.getInstance().getScore().getValue(), true);
            }


        }
    };


    //checking for active enemies and removing dead ones
    private void checkAndDestroyEnemies() {
        List<Enemy> destroyedEnemies = new ArrayList<>();
        for (Enemy enemy : activeEnemies) {
            if (enemy.getHealth() <= 0) {
                destroyEnemyView(enemy);
                destroyedEnemies.add(enemy);
            }
        }
        activeEnemies.removeAll(destroyedEnemies);
    }


    private void endGame(int currentScore, boolean playerLost) {
        Player.getInstance().moveStop();
        Player.getInstance().setScore(currentScore, nameText.getText().toString());
        Leaderboard.getInstance().addOrUpdateScore(Player.getInstance().getScore());

        Intent i = new Intent(this, EndActivity.class);
        i.putExtra("leaderboard", Leaderboard.getInstance());
        i.putExtra("recentScore", Player.getInstance().getScore());
        i.putExtra("playerLost", playerLost);

        startActivity(i);
        finish();
    }


    //player's attack function
    void performAttack() {
        Enemy targetEnemy = findEnemyInRange();
        if (targetEnemy != null) {
            Player.getInstance().attack(targetEnemy);
            playerView.performAttack();
            if (targetEnemy.getHealth() <= 0) {
                destroyEnemyView(targetEnemy);
                removeActiveEnemy(targetEnemy);
            }
        }
    }


    //deleting enemy views after attack
    private void destroyEnemyView(Enemy enemy) {
        if (enemy.equals(enemy1)) {
            parentLayout.removeView(enemyView1);
            removeEnemyObserver(enemyView1.getEnemyViewModel());

        } else if (enemy.equals(enemy2)) {
            parentLayout.removeView(enemyView2);
            removeEnemyObserver(enemyView2.getEnemyViewModel());

        }

    }

    //finding closest enemy to player and returning it
    private Enemy findEnemyInRange() {
        Enemy closestEnemy = null;
        double minDistance = Double.MAX_VALUE;

        int playerX = Player.getInstance().getXPos();
        int playerY = Player.getInstance().getYPos();

        Log.d("AttackCheck", "Player X: " + playerX + ", Y: " + playerY);
        for (Enemy enemy : activeEnemies) {
            int enemyX = enemy.getXPos();
            int enemyY = enemy.getYPos();
            double distance = Math.sqrt(Math.pow(enemyX - playerX, 2)
                    + Math.pow(enemyY - playerY, 2));
            Log.d("AttackCheck", "Enemy X: " + enemyX + ", Y: "
                    + enemyY + ", Distance: " + distance);

            if (distance < ATTACK_RANGE && distance < minDistance) {
                closestEnemy = enemy;
                minDistance = distance;
            }
        }

        return closestEnemy;
    }



    private void checkSwitchMap() {
        if (Player.getInstance().getYPos() < 10 && Player.getInstance().getXPos() < 2560
                && Player.getInstance().getXPos() > 2560 - 2 * 128 && mapView.getMapNumber() == 1) {
            mapView = new MapView(gameContext, "Map2.csv", 2);
            Player.getInstance().setYPos(12 * 128 - 6);

            removeEnemyObserver(enemyView1.getEnemyViewModel());
            parentLayout.removeView(enemyView1);
            enemySpawner = new SlimeSpawner();
            enemy1 = enemySpawner.spawnEnemy();
            enemyView1 = new EnemyView(this, 1210, 1000, enemy1);
            enemySpawner = new SpiderSpawner();
            enemy2 = enemySpawner.spawnEnemy();
            enemyView2 = new EnemyView(this, 300, 500, enemy2);
            parentLayout.addView(enemyView1, layoutParams);
            parentLayout.addView(enemyView2, layoutParams);
            registerEnemyObserver(enemyView1.getEnemyViewModel());
            registerEnemyObserver(enemyView2.getEnemyViewModel());

            removePowerUpObserver(powerView.getPowerUpViewModel());
            powerView = new PowerUpView(this, 1210, 1100, speedPower);
            parentLayout.addView(powerView, layoutParams);
            registerPowerUpObserver(powerView.getPowerUpViewModel());

        } else if (Player.getInstance().getYPos() < 10 && Player.getInstance().getXPos() < 1376
                && Player.getInstance().getXPos() > 1184 && mapView.getMapNumber() == 2) {
            mapView = new MapView(gameContext, "Map3.csv", 3);
            Player.getInstance().setYPos(12 * 128 - 6);

            removeEnemyObserver(enemyView1.getEnemyViewModel());
            removeEnemyObserver(enemyView2.getEnemyViewModel());
            parentLayout.removeView(enemyView1);
            parentLayout.removeView(enemyView2);
            enemySpawner = new ZombieSpawner();
            enemy1 = enemySpawner.spawnEnemy();
            enemyView1 = new EnemyView(this, 300, 500, enemy1);
            parentLayout.addView(enemyView1, layoutParams);
            registerEnemyObserver(enemyView1.getEnemyViewModel());

            removePowerUpObserver(powerView.getPowerUpViewModel());
            powerView = new PowerUpView(this, 1210, 1200, scorePower);
            parentLayout.addView(powerView, layoutParams);
            registerPowerUpObserver(powerView.getPowerUpViewModel());

        } else if (Player.getInstance().getXPos() < 10 && Player.getInstance().getYPos() < 896
                && Player.getInstance().getYPos() > 640 && mapView.getMapNumber() == 3) {
            if (!gameEnded) {
                gameEnded = true;

                Player.getInstance().moveStop();

                Leaderboard.getInstance().addOrUpdateScore(Player.getInstance().getScore());


                Intent i = new Intent(this, EndActivity.class);
                i.putExtra("leaderboard", leaderboard);
                i.putExtra("recentScore", Player.getInstance().getScore());
                startActivity(i);
                finish();
            }

        }
    }
    public void collisionHandling() {
        boolean[][] collisionMap = mapView.getMap().getCollisionMap();
        int playerX = Player.getInstance().getXPos();
        int playerY = Player.getInstance().getYPos();

        // Adding checks to ensure playerX and playerY are within array bounds
        int mapHeight = collisionMap.length;
        int mapWidth = collisionMap[0].length; // Assuming the collision map is a non-empty rectang

        // Check for Left Center collision
        if (playerY + 64 >= 0 && playerY + 64 < mapHeight && playerX >= 0 && playerX < mapWidth) {
            if (collisionMap[playerY + 64][playerX]) {
                notifyObservers("Left Collision");
            } else {
                notifyObservers("No Left Collision");
            }
        }

        // Check for Top Center collision
        if (playerY >= 0 && playerY < mapHeight && playerX + 64 >= 0 && playerX + 64 < mapWidth) {
            if (collisionMap[playerY][playerX + 64]) {
                notifyObservers("Top Collision");
            } else {
                notifyObservers("No Top Collision");
            }
        }

        // Check for Right Center collision
        if (playerY + 64 >= 0 && playerY + 64 < mapHeight && playerX + 128 >= 0
                && playerX + 128 < mapWidth) {
            if (collisionMap[playerY + 64][playerX + 128]) {
                notifyObservers("Right Collision");
            } else {
                notifyObservers("No Right Collision");
            }
        }

        // Check for Bottom Center collision
        if (playerY + 128 >= 0 && playerY + 128 < mapHeight && playerX + 64 >= 0
                && playerX + 64 < mapWidth) {
            if (collisionMap[playerY + 128][playerX + 64]) {
                notifyObservers("Bottom Collision");
            } else {
                notifyObservers("No Bottom Collision");
            }
        }
    }


    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String collisionStatus) {
        for (Observer observer : observers) {
            observer.update(collisionStatus);
        }
    }

    @Override
    public void registerEnemyObserver(EnemyObserver enemyObserver) {
        enemyObservers.add(enemyObserver);
    }

    @Override
    public void removeEnemyObserver(EnemyObserver enemyObserver) {
        enemyObservers.remove(enemyObserver);
    }

    @Override
    public void notifyEnemyObservers(int playerX, int playerY) {
        for (EnemyObserver enemyObserver : enemyObservers) {
            enemyObserver.updateEnemy(playerX, playerY);
        }
    }
    @Override
    public void registerPowerUpObserver(PowerUpObserver powerUpObserver) {
        powerUpObservers.add(powerUpObserver);
    }

    @Override
    public void removePowerUpObserver(PowerUpObserver powerUpObserver) {
        powerUpObservers.remove(powerUpObserver);
    }

    @Override
    public void notifyPowerUpObservers(int playerX, int playerY) {
        for (PowerUpObserver powerUpObserver : powerUpObservers) {
            powerUpObserver.updatePowerUp(playerX, playerY);
        }
    }
    public boolean isRunning() {
        if (!gameEnded) {
            return true;
        }
        return false;
    }
}
