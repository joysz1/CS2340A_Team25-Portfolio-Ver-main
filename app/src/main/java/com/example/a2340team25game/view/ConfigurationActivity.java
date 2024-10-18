package com.example.a2340team25game.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Button;
import android.content.Intent;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.a2340team25game.R;
import com.example.a2340team25game.R.id;
import com.example.a2340team25game.model.Leaderboard;
import com.example.a2340team25game.viewModel.PlayerViewModel;

public class ConfigurationActivity extends AppCompatActivity {

    private EditText inputName;
    private Button playButton;
    private RadioButton easyButton;
    private RadioButton mediumButton;
    private RadioButton hardButton;
    private int health;
    private static int selectedCharacterID;
    private int difficulty;
    private RadioGroup playerSelection;
    private ImageView playerGif;
    private boolean validName = false;

    private PlayerViewModel playerViewModel;

    private Leaderboard leaderboard;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        //Setting up views
        inputName = (EditText) findViewById(R.id.inputName);
        playButton = findViewById(R.id.playButton);
        easyButton = (RadioButton) findViewById(R.id.easyButton);
        mediumButton = (RadioButton) findViewById(R.id.mediumButton);
        hardButton = (RadioButton) findViewById(R.id.hardButton);
        playerGif = (ImageView) findViewById(id.playerStart);
        leaderboard = Leaderboard.getInstance();

        //presetting health and difficulty if player does not select
        health = 100;
        difficulty = 1;

        //Fire character prepicked in case player does not choose
        playerSelection = (RadioGroup) findViewById(R.id.playerSelection);
        playerSelection.clearCheck();
        selectedCharacterID = R.id.elf;
        playerSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selected = (RadioButton) findViewById(checkedId);
                if (selected.getId() == R.id.mage) {
                    //Sets selected character's id to be passed to game activity
                    selectedCharacterID = R.id.mage;
                    //Changes the source for the gif
                    playerGif.setImageResource(R.drawable.mage_static);
                    //Plays the gif
                    Glide.with(ConfigurationActivity.this).load(
                            R.drawable.mage_static).into(playerGif);
                    playerGif.setVisibility(View.VISIBLE);
                } else if (selected.getId() == R.id.rogue) {
                    selectedCharacterID = id.rogue;
                    playerGif.setImageResource(R.drawable.rogue_static);
                    Glide.with(ConfigurationActivity.this).load(
                            R.drawable.rogue_static).into(playerGif);
                    playerGif.setVisibility(View.VISIBLE);
                } else if (selected.getId() == R.id.elf) {
                    selectedCharacterID = id.elf;
                    playerGif.setImageResource(
                            R.drawable.elf_static);
                    Glide.with(ConfigurationActivity.this).load(
                            R.drawable.elf_static).into(playerGif);
                    playerGif.setVisibility(View.VISIBLE);
                } else {
                    playerGif.setVisibility(View.GONE);
                }
            }
        });




        playButton.setOnClickListener(v -> {

            //here I would add where you get the selected player

            validName = checkAllFields();

            if (validName) {
                Intent i = new Intent(ConfigurationActivity.this, GameActivity.class);
                i.putExtra("health", health);
                i.putExtra("name", inputName.getText().toString());
                i.putExtra("character", selectedCharacterID);
                i.putExtra("difficulty", difficulty);
                i.putExtra("leaderboard", leaderboard);
                //playerViewModel = new ViewModelProvider(this).get(PlayerViewModel.class);
                startActivity(i);
                //playerViewModel = new PlayerViewModel(inputName.getText().toString(), health,
                // difficulty, selectedCharacterID);
                finish();
            }
        });
    }
    private boolean checkAllFields() {
        if (inputName.getText() == null || inputName.getText().toString().trim().length() == 0) {
            inputName.setError("You must input a valid name that is not empty or whitespace only.");
            return false;
        }
        return true;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        int id = view.getId();
        if (checked) {
            if (id == R.id.easyButton) {
                health = 100;
                difficulty = 1;
            } else if (id == R.id.mediumButton) {
                health = 75;
                difficulty = 2;
            } else if (id == R.id.hardButton) {
                health = 50;
                difficulty = 3;
            }
        }
    }

    public static int getSelectedCharacterID() {
        return selectedCharacterID;
    }
}
