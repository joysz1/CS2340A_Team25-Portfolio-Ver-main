package com.example.a2340team25game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.enemies.Enemy;
import com.example.a2340team25game.model.enemies.Skeleton;
import com.example.a2340team25game.model.enemies.Slime;
import com.example.a2340team25game.model.enemies.Spider;
import com.example.a2340team25game.model.enemies.Zombie;
import com.example.a2340team25game.viewModel.EnemyViewModel;

public class EnemyView extends View {

    private int startX;
    private int startY;
    private Rect enemySpriteRect;
    private Rect enemyPos;
    private Bitmap spriteSheet;
    private EnemyViewModel enemyViewModel;

    public EnemyView(Context context, int startX, int startY, Enemy enemy) {
        super(context);
        this.startX = startX;
        this.startY = startY;
        if (enemy instanceof Skeleton) {
            this.spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.skeleton);
        } else if (enemy instanceof Slime) {
            this.spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.slime);
        } else if (enemy instanceof Spider) {
            this.spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.spider);
        } else if (enemy instanceof Zombie) {
            this.spriteSheet = BitmapFactory.decodeResource(getResources(), R.drawable.zombie);
        }
        this.enemyViewModel = new EnemyViewModel(startX, startY, enemy);
        //this.enemyViewModel.setEnemy(enemy);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.enemySpriteRect = new Rect(0, 0, 32, 32);

        this.enemyPos = new Rect(this.enemyViewModel.getXPos(), this.enemyViewModel.getYPos(),
                this.enemyViewModel.getXPos() + 128, this.enemyViewModel.getYPos() + 128);

        canvas.drawBitmap(spriteSheet, this.enemySpriteRect, this.enemyPos, null);

        this.setFocusable(true);
        /*individualSprite = new Rect(0, 0, 32, 32);

        playerPos = new Rect(Player.getInstance().getXPos(),
                Player.getInstance().getYPos() ,
                Player.getInstance().getXPos() + 128,
                Player.getInstance().getYPos() + 128);

        canvas.drawBitmap(spriteSheet, individualSprite, playerPos, null);

        this.setFocusable(true);*/
    }

    public EnemyViewModel getEnemyViewModel() {
        return this.enemyViewModel;
    }
}
