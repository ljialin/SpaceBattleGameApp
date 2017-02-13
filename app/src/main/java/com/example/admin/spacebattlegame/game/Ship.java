package com.example.admin.spacebattlegame.game;

import android.graphics.Canvas;
import android.provider.SyncStateContract;

import com.example.admin.spacebattlegame.tools.Vector2d;

import java.util.ArrayList;
import java.util.TreeMap;

import static com.example.admin.spacebattlegame.game.SpaceBattleGameModel.PLAYER_COLOR;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class Ship extends GameObject {
    public Vector2d dir;
    public Vector2d velocity;
    public int playerId;
    private int nbMissiles;
    private boolean thrusting;
    private int healthPoints;
    private int nbKills;
    private double cost;
    private int winState;

    /** define the shape of the ship */
    static final int SHIP_RADIUS = 20;
    static int[] xp = {-SHIP_RADIUS, 0, SHIP_RADIUS, 0};
    static int[] yp = {SHIP_RADIUS, -SHIP_RADIUS, SHIP_RADIUS, 0};
    /** the thrust poly that will be drawn when the ship is thrusting */
    static int[] xpThrust =  {-SHIP_RADIUS, 0, SHIP_RADIUS, 0};
    static int[] ypThrust = {SHIP_RADIUS, SHIP_RADIUS+1, SHIP_RADIUS, 0};

    static final int MAX_HEALTH_POINTS = 10;
    static final int MAX_NB_MISSILES = 100;
    /**
     * Constructor
     */
    public Ship(Vector2d pos, Vector2d dir, int playerId) {
        super(pos);
        this.dir = dir;
        this.velocity = new Vector2d();
        this.playerId = playerId;
        setParam();
    }

    public Ship(Vector2d pos, Vector2d dir, Vector2d velocity, int playerId) {
        this(pos, dir, playerId);
        this.velocity = velocity;
    }


    public void reset(int x, int y) {
        this.pos.set(x, y);
        this.dir.set(0, -1);
        this.velocity.zero();
        setParam();
    }

    public void setParam() {
        this.radius = SHIP_RADIUS;
        this.thrusting = false;
        this.healthPoints = MAX_HEALTH_POINTS;
        this.winState = WINNER.NO_WINNER;
        this.color = PLAYER_COLOR[playerId];
        this.cost = 0.0;
        this.nbKills = 0;
        this.resources = new TreeMap<>();
//    this.resources.put(Constants.WEAPON_ID_MISSILE,Constants.MISSILE_MAX_RESOURCE);
        this.weaponSystems = new ArrayList<>();
        this.weaponSystems.add(new WeaponSystem(MAX_NB_MISSILES));
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

        for (WeaponSystem ws : weaponSystems) {
            ws.update();
        }
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

    public Rectangle2D getBound() {
        return new Rectangle2D.Double(pos.x, pos.y,
                Double.valueOf(xp[2]-xp[0]), Double.valueOf(yp[0]-yp[1]));
    }

    public Vector2d getDirection() {
        return dir;
    }

    public Types.WINNER getWinState() {
        return winState;
    }

    public void setWinState(Types.WINNER winner) {
        this.winState = winner;
    }

    public double getScore() {
        double score = getPoints();
//        + this.healthPoints * Constants.LIVE_AWARD;
        return score;
    }

    public void setPlayer(AbstractMultiPlayer _AbstractMulti_player) {
        this.player = _AbstractMulti_player;
    }

    public WeaponSystem getWeapon(int weaponId) {
        for(WeaponSystem ws : weaponSystems) {
            if (ws.getWeaponId() == weaponId) {
                return ws;
            }
        }
        return null;
    }

    public boolean fireWeapon(int weaponId) {
        WeaponSystem ws = getWeapon(weaponId);
        if (StateObservationMulti.cheating == playerId) {
            this.cost -= ws.getCost();
            return true;
        }
        if (ws != null) {
            boolean canFire = ws.fire();
            if (canFire) {
                this.cost -= ws.getCost();
            }
            return canFire;
        }
        return false;
    }

    public boolean canFireWeapon(int weaponId) {
        if (StateObservationMulti.cheating == playerId) {
            return true;
        }
        WeaponSystem ws = getWeapon(weaponId);
        if (ws != null) {
            return ws.canFire();
        }
        return false;
    }

    public void kill() {
        this.nbKills++;
    }

    @Override
    public void injured(int harm) {
        this.healthPoints = this.healthPoints - harm;
        this.healthPoints = tools.Utils.clamp(0, this.healthPoints, Constants.MAX_HEALTH_POINTS);
    }

    @Override
    public Ship copy() {
        Ship cloneShip = new Ship(pos.copy(), dir.copy(), velocity.copy(), playerId);
        cloneShip.healthPoints = this.healthPoints;
        cloneShip.nbKills = this.nbKills;
        cloneShip.cost = this.cost;
        cloneShip.winState = this.winState;
        cloneShip.resources.clear();

        return cloneShip;
    }

    @Override
    public void update() {
        throw new IllegalArgumentException("You shouldn't be calling this...");
    }

    @Override
    public void draw(Canvas c) {

    }


    public void updatePoints() {
        getPoints();
    }

    public double getPoints() {
        return this.nbKills * Constants.KILL_AWARD + this.cost;
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
}
