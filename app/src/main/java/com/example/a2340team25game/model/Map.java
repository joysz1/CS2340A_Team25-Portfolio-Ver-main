package com.example.a2340team25game.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2340team25game.view.GameActivity;

public class Map extends AppCompatActivity {


    private static final int[][] COLLISIONS_BOXES = fileToColBox("collisionBoxes.csv");
    private boolean[][] collisionMap;

    public Map() {
        this.collisionMap = new boolean[13 * 128][20 * 128];
    }
    public int[][] fileTo2DArr(String fileName) {

        int[][] mapArr = null;

        try {
            InputStream inputStream = GameActivity.getAssetManager().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            int numOfRows = Integer.parseInt(line.substring(0, line.indexOf(",")));
            line = line.substring(line.indexOf(",") + 1);
            int numOfColumns = Integer.parseInt(line.substring(0, line.indexOf(",")));
            mapArr = new int[numOfRows][numOfColumns];
            int tileID;
            for (int row = 0; row < numOfRows; row++) {
                line = reader.readLine();
                for (int cell = 0; cell < numOfColumns; cell++) {
                    if (line.contains(",")) {
                        tileID = Integer.parseInt(line.substring(0, line.indexOf(",")));
                        mapArr[row][cell] = tileID;
                        line = line.substring(line.indexOf(",") + 1);
                    } else {
                        tileID = Integer.parseInt(line);
                        mapArr[row][cell] = tileID;
                    }
                    boolean[][] singleTileColMap = generatePixelMap(COLLISIONS_BOXES[tileID]);
                    for (int collisionMapRow = row * 128;
                         collisionMapRow < (row + 1) * 128;
                         collisionMapRow++) {
                        for (int collisionMapCell = cell * 128;
                             collisionMapCell < (cell + 1) * 128;
                             collisionMapCell++) {
                            this.collisionMap[collisionMapRow][collisionMapCell]
                                    = singleTileColMap[collisionMapRow - row * 128]
                                    [collisionMapCell - cell * 128];
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.v("Map Exception", e.getMessage());
        }
        return mapArr;
    }

    public static int[][] fileToColBox(String fileName) {
        int[][] colBox = new int[100][4];

        try {
            InputStream inputStream = GameActivity.getAssetManager().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            while (line != null) {
                int idx = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                int xLine = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                int yLine = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                int quartile = Integer.parseInt(line.substring(0, line.indexOf(',')));
                line = line.substring(line.indexOf(',') + 1);
                int quartileState = Integer.parseInt(line);

                colBox[idx][0] = xLine;
                colBox[idx][1] = yLine;
                colBox[idx][2] = quartile;
                colBox[idx][3] = quartileState;

                line = reader.readLine();
            }
        } catch (Exception e) {
            Log.v("ColBox Exception", e.getMessage());
        }
        return colBox;
    }

    public static int[][] getCollisionBox() {
        return COLLISIONS_BOXES;
    }

    public boolean[][] generatePixelMap(int[] colBoxArr) {
        boolean[][] pixelMap = new boolean[128][128];

        if (colBoxArr[3] == 1) { //Quartile State: True (Quartile Number Represents the Collision Bo
            if (colBoxArr[2] == 1) { //Quartile Number
                for (int row = 0; row < colBoxArr[1]; row++) {
                    for (int col = colBoxArr[0]; col < 128; col++) {
                        pixelMap[row][col] = true;
                    }
                }
            } else if (colBoxArr[2] == 2) {
                for (int row = colBoxArr[1]; row < 128; row++) {
                    for (int col = colBoxArr[0]; col < 128; col++) {
                        pixelMap[row][col] = true;
                    }
                }
            } else if (colBoxArr[2] == 3) {
                for (int row = colBoxArr[1]; row < 128; row++) {
                    for (int col = 0; col < colBoxArr[0]; col++) {
                        pixelMap[row][col] = true;
                    }
                }
            } else if (colBoxArr[2] == 4) {
                for (int row = 0; row < colBoxArr[1]; row++) {
                    for (int col = 0; col < colBoxArr[0]; col++) {
                        pixelMap[row][col] = true;
                    }
                }
            }
        } else if (colBoxArr[2] != 0) {
            for (int row = 0; row < 128; row++) {
                for (int col = 0; col < 128; col++) {
                    pixelMap[row][col] = true; //Sets all spots to true
                }
            }

            if (colBoxArr[2] == 1) { //Quartile Number
                for (int row = 0; row < colBoxArr[1]; row++) {
                    for (int col = colBoxArr[0]; col < 128; col++) {
                        pixelMap[row][col] = false;
                    }
                }
            } else if (colBoxArr[2] == 2) {
                for (int row = colBoxArr[1]; row < 128; row++) {
                    for (int col = colBoxArr[0]; col < 128; col++) {
                        pixelMap[row][col] = false;
                    }
                }
            } else if (colBoxArr[2] == 3) {
                for (int row = colBoxArr[1]; row < 128; row++) {
                    for (int col = 0; col < colBoxArr[0]; col++) {
                        pixelMap[row][col] = false;
                    }
                }
            } else if (colBoxArr[2] == 4) {
                for (int row = 0; row < colBoxArr[1]; row++) {
                    for (int col = 0; col < colBoxArr[0]; col++) {
                        pixelMap[row][col] = false;
                    }
                }
            }

        }

        return pixelMap;
    }

    public boolean[][] getCollisionMap() {
        return this.collisionMap;
    }
}
