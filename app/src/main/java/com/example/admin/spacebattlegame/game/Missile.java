package com.example.admin.spacebattlegame.game;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.admin.spacebattlegame.tools.Vector2d;

/**
 * Created by Jialin Liu on 14/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class Missile extends Weapon {

    public Missile(Paint paint, int playerId, Vector2d pos, Vector2d velocity, int missileTTL) {
        super(paint, playerId, pos, velocity);
        this.ttl = missileTTL;
        setRadius();
    }

    public Missile(Paint paint, int playerId, Vector2d pos, Vector2d velocity) {
        this(paint, playerId, pos, velocity, Constants.MISSILE_MAX_TTL);
    }

    public void setVelocityByDir(Vector2d dir) {
        this.velocity = Vector2d.multiply(dir, Constants.MISSILE_MAX_SPEED);
    }

    @Override
    public void update() {
        if (!isDead()) {
            pos.add(velocity);
            ttl--;
        }
    }

    @Override
    public void setRadius() {
        this.radius = Constants.MISSILE_RADIUS;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float) this.pos.x, (float) this.pos.y, (float) this.radius, paint);
    }

    @Override
    public GameObject copy() {
        Missile object = new Missile(paint, playerId, pos.copy(), velocity.copy(), ttl);
        return object;
    }
}
