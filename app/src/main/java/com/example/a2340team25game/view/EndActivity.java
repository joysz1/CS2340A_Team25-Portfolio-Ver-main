package com.example.a2340team25game.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Score;
import com.example.a2340team25game.model.Leaderboard;

import java.util.ArrayList;
public class EndActivity extends AppCompatActivity {

    private TextView score1;
    private Leaderboard leaderboard;
    private TextView endingMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        endingMessage = findViewById(R.id.endingMessage);

        // Retrieve the playerLost flag from the intent
        boolean playerLost = getIntent().getBooleanExtra("playerLost", false);
        if (playerLost) {
            endingMessage.setText("You Lost!");
        }

        leaderboard = (Leaderboard) getIntent().getExtras().getSerializable("leaderboard");

        score1 = findViewById(R.id.score1);

        if (leaderboard != null) {
            ArrayList<Score> scores = new ArrayList<>();
            leaderboard.reverseInorderTraversal(scores);
            String result = "";

            Score recentScore = (Score) getIntent().getExtras().getSerializable("recentScore");
            if (recentScore != null) {
                result += "Most Recent Attempt: " + recentScore.toString() + "\n";
            } else {
                result += "Most Recent Attempt: Not available\n";
            }

            if (!scores.isEmpty()) {
                for (int i = 0; i < scores.size() && i < 5; i++) {
                    result += "Attempt " + (i + 1) + ": " + scores.get(i).toString() + "\n";
                }
            } else {
                result += "No previous attempts available\n";
            }
            score1.setText(result);
        } else {
            score1.setText("Leaderboard data is not available.");
        }

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndActivity.this, MainActivity.class);
                intent.putExtra("leaderboard", leaderboard);
                startActivity(intent);
                finish();
            }
        });
    }
}
