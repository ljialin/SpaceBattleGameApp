package com.example.admin.spacebattlegame;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import com.example.admin.spacebattlegame.game.Ship;
import com.example.admin.spacebattlegame.game.SpaceBattleGameModel;

public class GameThread extends Thread {
    static final String TAG = "";
    GameView GameView;
    // set this to false for a graceful death
    boolean running;
    int delay = 50; // delay in ms
    SpaceBattleGameModel model;

    public GameThread(GameView GameView) {
        this.GameView = GameView;
        running = true;
        System.out.println("TestActivity: Making Game Data");
        model = new SpaceBattleGameModel(GameView.getWidth(), GameView.getHeight());
        System.out.println("TestActivity: Test Thread About to Start");
        start();

    }

    public void run() {
        System.out.println("TestActivity: TestThread Running");
        int count = 0;
        while (running) {
            Canvas c = null;
            count++;
            SurfaceHolder surfaceHolder = GameView.getHolder();
            surfaceHolder.getSurfaceFrame();
            model.update(surfaceHolder.getSurfaceFrame(), delay);
            GameView.mainActivity.updateScore(model.score);
            try {
                c = surfaceHolder.lockCanvas(null);
                draw(surfaceHolder, c);
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
            try {
                sleep(delay);
                if (count % 50 == 0) {
                    System.out.println("TestActivity: " + count);
                }
            } catch (Exception e) {
                System.out.println("TestActivity: TestThread Exception: " + e);
            }
        }
        running = false;  // set this in case we got here via gameOver()
        System.out.println("GameView: exiting TestThread.run()");
    }

    public void draw(SurfaceHolder surfaceHolder, Canvas c) {
        // draw the background
        Rect rect = surfaceHolder.getSurfaceFrame();
        c.drawRect(rect, bg);
        // and the movable objects
        for (Ship ship : model.getShips()) ship.draw(c);
        // and the game data ...
        drawText(rect, c);
    }

    public void drawText(Rect rect, Canvas c) {
        tp.setTextSize(rect.height() / 20);
        float inset = -tp.ascent() * 2;
        tp.setTextAlign(Paint.Align.LEFT);
        c.drawText("Score = " + model.score, inset, inset, tp);
        tp.setTextAlign(Paint.Align.RIGHT);
        String timeRemaining = String.format("%.2fs", model.timeRemaining / 1000.0f);
        c.drawText(timeRemaining, rect.width() - inset, inset, tp);
        tp.setTextAlign(Paint.Align.CENTER);
        c.drawText("High = " + GameView.mainActivity.highScore, rect.width() / 2, inset, tp);
    }

    public void update(Rect rect) {
        // update the game objects etc
        for (Ship ship : model.getShips()) ship.update(rect);
        GameView.mainActivity.updateScore(model.score);
    }

    public void dieAndWait() {
        running = false;
        try {
            join();
            System.out.println("TestActivity: dieAndWait() " + this.getState());
        } catch (Exception e) {

        }
    }

    static Paint bg = new Paint();

    static {
        bg.setColor(0xFFCC0077);
        bg.setStyle(Paint.Style.FILL);
    }

    static Paint tp = new Paint();

    static {
        tp.setColor(Color.WHITE);
        tp.setStyle(Paint.Style.FILL);
        tp.setAntiAlias(true);
        tp.setTextSize(30);
    }
}
