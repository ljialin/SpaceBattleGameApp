package com.example.admin.spacebattlegame.game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.admin.spacebattlegame.tools.Vector2d;

import java.util.ArrayList;

/**
 * Created by Jialin Liu on 13/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class SpaceBattleGameModel {
    static final int NB_SHIP = 2;
    static final int[] PLAYER_COLOR = new int[] {Color.BLUE, Color.GREEN};
    private ArrayList<Ship> ships;
    public int score;
    public int timeRemaining=100;

    static Paint paintBlue, paintGreen;
    static {
        paintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);
        paintGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStyle(Paint.Style.FILL);
    }

    public SpaceBattleGameModel(int width, int height) {
        int centerX = width/2;
        int centerY = height/2;
        this.ships = new ArrayList<>();
        this.ships.add(new Ship(paintBlue, new Vector2d(centerX*1.1, centerY), new Vector2d(0, -1), 0));
        this.ships.add(new Ship(paintGreen, new Vector2d(centerX*0.9, centerY), new Vector2d(0, 1), 1));
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }

    public void update(Rect surfaceFrame, int delay) {

    }
}
