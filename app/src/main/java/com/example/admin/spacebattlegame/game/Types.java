package com.example.admin.spacebattlegame.game;

import android.view.KeyEvent;

import com.example.admin.spacebattlegame.tools.Vector2d;

import java.util.ArrayList;

/**
 * Created by Jialin Liu on 13/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class Types {
    public static final Vector2d NIL = new Vector2d(0, 0);
    public static final Vector2d THRUST = new Vector2d(0, 1);
    public static final Vector2d RIGHT = new Vector2d(1, 0);
    public static final Vector2d LEFT = new Vector2d(-1, 0);
    public static final Vector2d FIRE = new Vector2d(0, 0);

    public static enum ACTIONS {
        ACTION_NIL,
        ACTION_THRUST,
        ACTION_LEFT,
        ACTION_RIGHT,
        ACTION_FIRE;
    }

//    public static ArrayList<String> AVAILABLE_ACTIONS = new ArrayList<String>() {{
//        add("NIL");
//        add("THRUST");
//        add("RIGHT");
//        add("LEFT");
//        add("FIRE");
//    }};

    public static ArrayList<ACTIONS> AVAILABLE_ACTIONS = new ArrayList<ACTIONS>() {{
        add(Types.ACTIONS.ACTION_NIL);
        add(Types.ACTIONS.ACTION_LEFT);
        add(Types.ACTIONS.ACTION_RIGHT);
        add(Types.ACTIONS.ACTION_THRUST);
        add(Types.ACTIONS.ACTION_FIRE);
    }};
}
