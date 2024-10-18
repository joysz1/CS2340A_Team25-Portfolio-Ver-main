package com.example.a2340team25game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.graphics.Paint;
import android.graphics.Color;
import android.os.Handler;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Player;

import java.util.Random;

public class PlayerView extends View {
    private Bitmap spriteSheet;
    private Rect individualSprite;

    private Rect playerPos;
    private Bitmap weaponBitmap; // Bitmap for the weapon
    private Rect weaponPos; // Position for the weapon
    private boolean isAttacking = false;
    private long attackDuration = 500;
    private String[] attackPhrases = {"Attack!", "Die!", "Too easy!", "Strike!", "Bam!",
                                      "Slash!", "Hit!", "Thud!", "Punch!", "Kick!"};
    private String currentAttackPhrase = "Attack!";
    private Paint textPaint;
    private Paint backgroundPaint;
    private float cornerRadius = 20;
    private Random random = new Random();

    public PlayerView(Context context) {
        super(context);

        // initialze the text paint for attack text
        textPaint = new Paint();
        textPaint.setColor(Color.YELLOW); // Set text color
        textPaint.setTextSize(50); // Set text size
        textPaint.setTextAlign(Paint.Align.CENTER);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setStyle(Paint.Style.FILL);

        if (Player.getInstance().getCharacterID() == R.id.mage) {
            spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.mage_ss);
        } else if (Player.getInstance().getCharacterID() == R.id.rogue) {
            spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.rogue_ss);
        } else if (Player.getInstance().getCharacterID() == R.id.elf) {
            spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.elf_ss);
        }
        weaponBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.weapon_image);

        // Initialize weapon position based on the player's initial position
        int weaponSize = 64; // Example size, adjust as needed
        weaponPos = new Rect(0, 0, weaponSize, weaponSize); // Initial position

    }

    private void chooseRandomAttackPhrase() {
        int index = random.nextInt(attackPhrases.length);
        currentAttackPhrase = attackPhrases[index];
    }


    public void performAttack() {
        chooseRandomAttackPhrase();
        isAttacking = true;
        invalidate();

        new Handler().postDelayed(() -> {
            isAttacking = false;
            invalidate(); // redraw the player to normal state
        }, attackDuration);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        individualSprite = new Rect(0, 0, 32, 32);
        playerPos = new Rect(Player.getInstance().getXPos(),
                Player.getInstance().getYPos(),
                Player.getInstance().getXPos() + 128,
                Player.getInstance().getYPos() + 128);


        // Position the weapon relative to the player
        int weaponOffsetX = -5;
        int weaponOffsetY = 40;
        weaponPos.set(playerPos.right + weaponOffsetX,
                playerPos.top + weaponOffsetY,
                playerPos.right + weaponOffsetX + weaponPos.width(),
                playerPos.top + weaponOffsetY + weaponPos.height());

        canvas.drawBitmap(spriteSheet, individualSprite, playerPos, null);

        canvas.drawBitmap(weaponBitmap, null, weaponPos, null); // Draw the weapon

        if (isAttacking) {
            Rect textBounds = new Rect();
            textPaint.getTextBounds(currentAttackPhrase, 0,
                    currentAttackPhrase.length(), textBounds);
            int textWidth = textBounds.width();
            int textHeight = textBounds.height();

            // rounded corners
            float left = playerPos.centerX() - textWidth / 2 - 10;
            float top = playerPos.top - textHeight - 30;
            float right = playerPos.centerX() + textWidth / 2 + 10;
            float bottom = playerPos.top - 5;

            // draw rounded background rectangle above the player
            canvas.drawRoundRect(left, top, right, bottom, cornerRadius,
                    cornerRadius, backgroundPaint);
            canvas.drawText(currentAttackPhrase, playerPos.centerX(),
                    playerPos.top - 15, textPaint);
        }
        this.setFocusable(true);
    }

}
