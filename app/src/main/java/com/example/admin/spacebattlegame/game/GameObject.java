package com.example.admin.spacebattlegame.game;

import android.graphics.Canvas;
import android.graphics.Color;

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
    public int color;

    public GameObject() {
        this.pos = new Vector2d(640 / 2, 480 / 2);
        this.radius = 0;
        this.playerId = -1;
        this.dead = false;
        this.color = Color.WHITE;
    }

    public GameObject(Vector2d pos) {
        this();
        this.pos.x = pos.x;
        this.pos.y = pos.y;
    }

    public abstract GameObject copy();

    public abstract void update();

    public abstract void draw(Canvas c);

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
}
