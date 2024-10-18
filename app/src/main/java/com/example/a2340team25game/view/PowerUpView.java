package com.example.a2340team25game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.powerUps.HealthPowerUp;
import com.example.a2340team25game.model.powerUps.PowerUp;
import com.example.a2340team25game.model.powerUps.ScorePowerUp;
import com.example.a2340team25game.model.powerUps.SpeedPowerUp;
import com.example.a2340team25game.viewModel.PowerUpViewModel;

public class PowerUpView extends View {

    private int startX;
    private int startY;
    private Rect powerSpriteRect;
    private Rect powerPos;
    private Bitmap spriteSheet;
    private PowerUpViewModel powerUpViewModel;

    public PowerUpView(Context context, int startX, int startY, PowerUp powerUp) {
        super(context);
        this.startX = startX;
        this.startY = startY;
        if (powerUp instanceof HealthPowerUp) {
            this.spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        } else if (powerUp instanceof SpeedPowerUp) {
            this.spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.feather);
        } else if (powerUp instanceof ScorePowerUp) {
            this.spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.plus);
        }
        this.powerUpViewModel = new PowerUpViewModel(startX, startY, powerUp);
        //this.enemyViewModel.setEnemy(enemy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.powerSpriteRect = new Rect(0, 0, 32, 32);

        this.powerPos = new Rect(this.powerUpViewModel.getXPos(), this.powerUpViewModel.getYPos(),
                this.powerUpViewModel.getXPos() + 128, this.powerUpViewModel.getYPos() + 128);

        canvas.drawBitmap(spriteSheet, this.powerSpriteRect, this.powerPos, null);

        this.setFocusable(true);
    }

    public PowerUpViewModel getPowerUpViewModel() {
        return this.powerUpViewModel;
    }
}
