package com.example.admin.spacebattlegame.game;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Constants {
    public static double SHIP_MAX_SPEED = 10; // to optimise, id=2
    public static double THRUST_SPEED = 5; // to optimise, id=3
    public static int MISSILE_MAX_SPEED = 1; // to optimise, id=7
    public static int MISSILE_COOLDOWN = 4; // to optimise, id=8
    public static int SHIP_RADIUS = 50; // to optimise, id=1

    public static int KILL_AWARD = 100; // to optimise, id=11



    public static final double MAX_REPULSE_FORCE = 1.0;

    public static final int MISSILE_MAX_RESOURCE = 1000000;
    public static  double MISSILE_RADIUS = 10; // to optimise, id=5
    public static  int MISSILE_MAX_TTL = 50; // to optimise, id=6

    public static final double GRAVITY = 0;
    public static  double FRICTION = 0.99; // to optimise, id=9

    public static  double RADIAN_UNIT = 10 * Math.PI / 180; // to optimise, id=10



    public static final int MAX_HEALTH_POINTS = 1000;
    public static final int LIVE_AWARD = 10;

    public static final boolean SHOW_ROLLOUTS = true;
}
