package com.example.a2340team25game.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Player;

public class PlayerViewModel extends ViewModel {
    private Player player;

    public PlayerViewModel(String name, int health, int difficulty, int characterID) {
        player = Player.getInstance();
        player.setName(name);
        player.setHealth(health);
        player.setDifficulty(difficulty);
        player.setCharacterID(characterID);
    }

    public PlayerViewModel(String name) {
        this(name, 100, 1, R.id.elf);
    }
}
