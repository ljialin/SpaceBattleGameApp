package com.example.admin.spacebattlegame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.admin.spacebattlegame.game.Ship;

import java.util.List;

/**
 * Created by Jialin Liu on 13/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public final String TAG = "GameView: ";
    public MainActivity mainActivity;
    GameThread thread;

    public GameView(Context context) {
        super(context);
        this.mainActivity = (MainActivity) context;

        getHolder().addCallback(this);
        setFocusable(false);
        requestFocus();

        postInvalidate();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mainActivity = (MainActivity) context;
        mainActivity.initModel(getWidth(), getHeight());
        postInvalidate();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mainActivity = (MainActivity) context;
        mainActivity.initModel(getWidth(), getHeight());
        postInvalidate();
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        System.out.println(TAG +  "surfaceCreated: "+getWidth()+"," + getHeight());
        mainActivity.initModel(getWidth(),  getHeight());
        thread = new GameThread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        Ship[] ships = mainActivity.getModel().getAvatars();
        for (Ship ship : ships) {
            ship.draw(canvas);
        }
        postInvalidate();
    }

}
