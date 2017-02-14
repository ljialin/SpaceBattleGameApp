package com.example.admin.spacebattlegame.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.GLES20;

import com.example.admin.spacebattlegame.game.physics.ForcePhysics;
import com.example.admin.spacebattlegame.game.physics.GravityPhysics;
import com.example.admin.spacebattlegame.game.physics.RotationPhysics;
import com.example.admin.spacebattlegame.tools.Utils;
import com.example.admin.spacebattlegame.tools.Vector2d;


import static com.example.admin.spacebattlegame.game.Constants.*;
import static com.example.admin.spacebattlegame.game.SpaceBattleGameModel.PLAYER_COLOR;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class Ship extends GameObject {
    static final String TAG = "Ship: ";
    private int playerId;
    private Vector2d dir;
    private Vector2d velocity;
    private boolean thrusting;
    private int nbMissiles;
    private int healthPoints;
    private int nbKills;
    private double cost;
    private int winState;

    Paint paint;

    static final Vector2d[] shipPoints = new Vector2d[] {new Vector2d(0,0),
            new Vector2d(-SHIP_RADIUS/Math.sqrt(2),SHIP_RADIUS/Math.sqrt(2)),
            new Vector2d(SHIP_RADIUS,0),
            new Vector2d(-SHIP_RADIUS/Math.sqrt(2),-SHIP_RADIUS/Math.sqrt(2))};

    /** define the shape of the ship */
    /**
     * Constructor
     */
    public Ship(Paint paint, Vector2d pos, Vector2d dir, int playerId) {
        super(paint,pos);
        this.paint = paint;
        this.playerId = playerId;
        this.dir = dir;
        setParam();
        System.out.println("Ship " + playerId + " created");
    }

    public Ship(Paint paint, Vector2d pos, Vector2d dir, Vector2d velocity, int playerId) {
        this(paint, pos, dir, playerId);
        this.velocity = velocity;
    }

    public void setParam() {
        this.color = PLAYER_COLOR[playerId];
        this.velocity = new Vector2d();
        this.thrusting = false;
        this.nbMissiles = MISSILE_MAX_RESOURCE;
        this.healthPoints = MAX_HEALTH_POINTS;
        this.nbKills = 0;
        this.cost = 0;
        this.winState = 0;
        this.radius = SHIP_RADIUS;
        this.thrusting = false;
        this.cost = 0.0;
        this.nbKills = 0;
        isWrappable = true;
    }

    public void reset(int x, int y) {
        this.pos.set(x, y);
        this.dir.set(0, -1);
        this.velocity.zero();
        setParam();
    }

    public void update(Types.ACTIONS action) {
        this.thrusting = false;
        switch (action) {
            case ACTION_THRUST:
                this.thrusting = true;
                ForcePhysics.thrust(velocity, dir);
                break;
            case ACTION_LEFT:
                RotationPhysics.steer(dir, -1.0);
                break;
            case ACTION_RIGHT:
                RotationPhysics.steer(dir, 1.0);
                break;
            case ACTION_FIRE:
                ForcePhysics.repulse(pos, dir, false);
                break;
            case ACTION_NIL:
                break;
            default:
                break;
        }

        GravityPhysics.gravity(pos, velocity);

        velocity.x = Utils.clamp(-Constants.SHIP_MAX_SPEED, velocity.x,
                Constants.SHIP_MAX_SPEED);
        velocity.y = Utils.clamp(-Constants.SHIP_MAX_SPEED, velocity.y,
                Constants.SHIP_MAX_SPEED);

        pos.add(velocity);
    }

    public double dotTo(Ship other)
    {
        Vector2d diff = Vector2d.subtract(other.pos,this.pos);
        Vector2d front = new Vector2d(this.dir);
        front.normalise();
        diff.normalise();
        return diff.dot(front);
    }

    public double dotDirections(Ship other)
    {
        Vector2d thisFront = new Vector2d(this.dir);
        Vector2d otherFront = new Vector2d(other.dir);
        thisFront.normalise();
        otherFront.normalise();
        return thisFront.dot(otherFront);
    }

    public double distTo(Ship other)
    {
        Vector2d diff = Vector2d.subtract(other.pos, this.pos);
        return diff.mag();
    }

    public Vector2d getDirection() {
        return dir;
    }

    public int getScore() {
        int score = (int) (this.nbKills * KILL_AWARD - this.cost);
        return score;
    }


    public boolean fire() {

        if (this.nbMissiles >0) {
            boolean canFire = true;
            this.cost += 1;
            return canFire;
        }
        return false;
    }

    public void kill() {
        this.nbKills++;
    }

    @Override
    public void injured(int harm) {
        this.healthPoints = this.healthPoints - harm;
        this.healthPoints = Utils.clamp(0, this.healthPoints, Constants.MAX_HEALTH_POINTS);
    }

    @Override
    public Ship copy() {
        Ship cloneShip = new Ship(this.paint, pos.copy(), dir.copy(), velocity.copy(), playerId);
        cloneShip.thrusting = this.thrusting;
        cloneShip.nbMissiles = this.nbMissiles;
        cloneShip.healthPoints = this.healthPoints;
        cloneShip.nbKills = this.nbKills;
        cloneShip.cost = this.cost;
        cloneShip.winState = this.winState;
        return cloneShip;
    }

    @Override
    public void update() {
        throw new IllegalArgumentException("You shouldn't be calling this...");
    }

    @Override
    public void draw(Canvas canvas) {
//        canvas.drawCircle((float) this.pos.x, (float) this.pos.y, (float) this.radius, paint);
//        final RectF rect = new RectF();
//        //Example values
//        float left = (float) this.pos.x - SHIP_RADIUS*2/3;
//        float top = (float) this.pos.y + SHIP_RADIUS;
//        float right = (float) this.pos.x + SHIP_RADIUS*2/3;
//        float bottom = (float) this.pos.y - SHIP_RADIUS;
//        rect.set(left, top, right, bottom);
//        paint.setStrokeWidth(20);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStyle(Paint.Style.STROKE);

        drawShip(canvas);
//        float degree = 75;// (float) Math.atan2(dir.x, dir.y);
//        canvas.drawArc(rect, degree, 360, false, paint);
//        canvas.drawCircle((float) (this.pos.x+SHIP_RADIUS*2/3*Math.sin(degree)), (float) (this.pos.y+SHIP_RADIUS*Math.cos(degree)), SHIP_RADIUS/4, paint);
//        System.out.println("ship drawn");
    }

    private void drawShip(Canvas canvas) {
        Vector2d[] newPoints = new Vector2d[shipPoints.length];
        for (int i=0; i<shipPoints.length; i++) {
//            newPoints[i] = shipPoints[i].mul(dir.sin()).add(pos);
            newPoints[i] = shipPoints[i].rotate(dir).add(pos);
        }
        drawPoly(canvas, newPoints);
    }

    private void drawPoly(Canvas canvas, Vector2d[] points) {
        if (points.length < 2) {
            return;
        }
        // path
        Path polyPath = new Path();
        polyPath.moveTo((float) points[0].x, (float) points[0].y);
        int i, len;
        len = points.length;
        for (i = 0; i < len; i++) {
            polyPath.lineTo((float) points[i].x, (float) points[i].y);
        }
        polyPath.lineTo((float) points[0].x, (float) points[0].y);

        // draw
        canvas.drawPath(polyPath, paint);
    }

    public double getCost() {
        return this.cost;
    }

    public int getHealthPoints() {
        return this.healthPoints;
    }

    @Override
    public boolean isDead() {
        return (this.healthPoints<=0);
    }

    public void update(Rect rect) {
        pos.add(velocity);
        pos.wrap(rect.width(), rect.height());
    }

    public Vector2d getVelocity() {
        return this.velocity;
    }
}
