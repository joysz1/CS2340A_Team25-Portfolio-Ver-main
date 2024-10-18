package com.example.a2340team25game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import com.example.a2340team25game.R;
import com.example.a2340team25game.model.Map;

public class MapView extends View {
    private static int[][] mapLayout;
    private Bitmap tileset;
    private static final int TILE_HEIGHT_AND_WIDTH = 128;
    private Map map;

    private int mapNumber = 0;

    public MapView(Context context, String fileName, int mapNumber) {
        super(context);
        map = new Map();
        this.mapLayout = map.fileTo2DArr(fileName);
        this.mapNumber = mapNumber;
        //Log.v("Test-1", Integer.toString(mapLayout[0][0]));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        tileset = BitmapFactory.decodeResource(getResources(), R.drawable.tile_sheet);

        if (tileset != null) {
            for (int y = 0; y < this.mapLayout.length; y++) {
                for (int x = 0; x < this.mapLayout[y].length; x++) {
                    int tileID = this.mapLayout[y][x];

                    int srcX = (tileID % (tileset.getWidth() / TILE_HEIGHT_AND_WIDTH))
                            * TILE_HEIGHT_AND_WIDTH;
                    int srcY = (tileID / (tileset.getWidth() / TILE_HEIGHT_AND_WIDTH))
                            * TILE_HEIGHT_AND_WIDTH;
                    Rect srcRect = new Rect(srcX + 1, srcY + 1, srcX
                            + TILE_HEIGHT_AND_WIDTH - 1, srcY + TILE_HEIGHT_AND_WIDTH - 1);

                    int dstX = x * TILE_HEIGHT_AND_WIDTH;
                    int dstY = y * TILE_HEIGHT_AND_WIDTH;
                    Rect dstRect = new Rect(dstX, dstY, dstX
                            + TILE_HEIGHT_AND_WIDTH, dstY + TILE_HEIGHT_AND_WIDTH);

                    canvas.drawBitmap(tileset, srcRect, dstRect, null);
                }
            }
        }
    }

    public static int[][] getMapLayout() {
        return mapLayout;
    }

    public int getMapNumber() {
        return this.mapNumber;
    }

    public Map getMap() {
        return this.map;
    }
}
