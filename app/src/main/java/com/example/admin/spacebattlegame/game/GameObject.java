package com.example.admin.spacebattlegame.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.admin.spacebattlegame.tools.Vector2d;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public abstract class GameObject {
    protected Vector2d pos;
    protected double radius;
    protected boolean dead;
    protected int playerId;
    protected boolean isWrappable;
    public int color;
    Paint paint;

    public GameObject(Paint paint) {
        this.pos = new Vector2d(0, 0);
        this.radius = 0;
        this.playerId = -1;
        this.dead = false;
        this.paint = paint;
        this.color = Color.WHITE;
    }

    public GameObject(Paint paint, Vector2d pos) {
        this(paint);
        this.pos.x = pos.x;
        this.pos.y = pos.y;
    }

    public abstract GameObject copy();

    public abstract void update();

    public abstract void draw(Canvas canvas);

    public Vector2d getPosition() {
        return pos;
    }

    public void setPosition(double x, double y) {
        this.pos.x = x;
        this.pos.y = y;
    }


    public abstract void injured(int harm);

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int _playerId) {
        this.playerId = _playerId;
    }

    public double getRadius() {
        return this.radius;
    }

    public boolean isDead() {
        return dead;
    }

    public void kill() {
        this.dead = true;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isWrappable() {
        return isWrappable;
    }
}
