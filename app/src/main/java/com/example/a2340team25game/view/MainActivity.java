package com.example.a2340team25game.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Leaderboard;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout welcome;
    private Button startBtn;
    private Button exitBtn;
    private int time = 0;
    private Leaderboard leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcome = findViewById(R.id.welcome);
        startBtn = findViewById(R.id.start_button);
        exitBtn = findViewById(R.id.exit_button);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToConfigurationScreen();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    private void switchToConfigurationScreen() {
        Intent switchIntent = new Intent(this, ConfigurationActivity.class);
        startActivity(switchIntent);
        finish();
    }

}
