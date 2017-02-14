package com.example.admin.spacebattlegame.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.admin.spacebattlegame.tools.Vector2d;

/**
 * Created by Jialin Liu on 14/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class Weapon extends GameObject {
    public Vector2d velocity;
    public int ttl;

    public Weapon(Paint paint, int playerId, Vector2d pos, Vector2d velocity) {
        super(paint,pos);
        this.playerId = playerId;
        this.velocity = velocity;
        setRadius();
    }

    public void setRadius() {
        this.radius = 1;
    }

    @Override
    public void update() {
        if (!isDead()) {
            pos.add(velocity);
            ttl--;
        }
    }

    @Override
    public GameObject copy() {
        Weapon object = new Weapon(paint, playerId, pos.copy(), velocity.copy());
        return object;
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public void injured(int harm) {
        this.ttl -= harm;
    }

    @Override
    public boolean isDead() {
        dead = ( ttl <= 0);
        return dead;
    }

    public void hit() {
        // kill it by setting ttl to zero
        ttl = 0;
    }

    public String toString() {
        return ttl + " :> " + pos;
    }
}
