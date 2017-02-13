package com.example.admin.spacebattlegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.spacebattlegame.game.SpaceBattleGameModel;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity: ";
    private GameView view;
    private SpaceBattleGameModel model;
    public int highScore = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        view = new GameView(this);
        setContentView(view);

    }

    public SpaceBattleGameModel getModel() {
        return this.model;
    }

    public void initModel(int width, int height) {
        model = new SpaceBattleGameModel(width, height);
        System.out.println(TAG + "model created");
    }


    public void updateScore(int score) {
        // TODO: 13/02/2017
    }
}
