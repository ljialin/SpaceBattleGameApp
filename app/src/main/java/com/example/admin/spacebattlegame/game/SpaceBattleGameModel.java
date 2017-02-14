package com.example.admin.spacebattlegame.game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.admin.spacebattlegame.agents.sampleRandom.Agent;
import com.example.admin.spacebattlegame.tools.Vector2d;

import java.util.ArrayList;

/**
 * Created by Jialin Liu on 13/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

public class SpaceBattleGameModel {
    static final String TAG ="SpaceBattleGameModel: ";
    static final int NB_SHIP = 2;
    static final int[] PLAYER_COLOR = new int[] {Color.BLUE, Color.GREEN};
    private Ship[] avatars;
    private ArrayList<GameObject> objects;
    private int width;
    private int height;
    public int score;
    public int timeRemaining = 1000000;
    public Agent opponent;
    public boolean isEnded = false;

    static Paint paintBlue, paintGreen;
    static {
        paintBlue = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBlue.setColor(Color.BLUE);
        paintBlue.setStyle(Paint.Style.FILL);
        paintGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintGreen.setColor(Color.GREEN);
        paintGreen.setStyle(Paint.Style.FILL);
    }

    public SpaceBattleGameModel(int width, int height) {
        this.width = width;
        this.height = height;
        int centerX = width/2;
        int centerY = height/2;
        this.avatars = new Ship[NB_SHIP];
        System.out.println(TAG + "Ship 1 at position (" + centerX*1.1 +  "," + centerY + ")");
        System.out.println(TAG + "Ship 2 at position (" + centerX*0.9 +  "," + centerY + ")");
        this.avatars[0] = new Ship(paintBlue, new Vector2d(centerX*1.1, centerY), new Vector2d(0, -1), 0);
        this.avatars[1] = new Ship(paintGreen, new Vector2d(centerX*0.9, centerY), new Vector2d(0, 1), 1);
        opponent = new Agent(this,1);
        objects = new ArrayList<>();
    }

    public Ship[] getAvatars() {
        return this.avatars;
    }

    public ArrayList<GameObject> getObjects() { return this.objects;}

    public void update(Rect surfaceFrame, int delay) {
        update();
    }

    public void update() {
        Types.ACTIONS[] actions = new Types.ACTIONS[NB_SHIP];
        actions[0] = null; // TODO: 14/02/2017 human player's behaviour here
        actions[1] = opponent.act(this.copy());


        advance(actions);

        score = avatars[0].getScore();
        if (avatars[0].isDead() && avatars[1].isDead()) {
            isEnded = true;
        }
    }

    public void advance(Types.ACTIONS[] actions) {
        for (int i=0; i<actions.length; i++) {
            if(actions[i] == null) {
                avatars[i].update(Types.ACTIONS.ACTION_NIL);
            } else {
                if (actions[i].equals(Types.ACTIONS.ACTION_FIRE)) {
                    fireMissile(i);
                } else {
                    avatars[i].update(actions[i]);
                }
            }
            wrap(avatars[i]);
        }

        for (GameObject ob : objects) {
            ob.update();
            wrap(ob);
        }

//        checkCollision();

        removeDead();

        for (int i=0; i<NB_SHIP; i++) {
            this.avatars[i].getScore();
        }

//        gameTick++;

//    System.out.println("StateObservationMulti : gameTick=" + gameTick);
//        if (visible) {
//            view.repaint();
//            sleep();
//        }
    }

    protected void fireMissile(int playerId) {
        // need all the usual missile firing code here
        Ship currentShip = this.avatars[playerId];
        boolean fired = this.avatars[playerId].fire();
//    System.out.println("Ship " + playerId + " fires ? " + fired);
        if(fired) {
            Missile m;
            if (playerId ==0 ) {
                m = new Missile(paintBlue, playerId, this.avatars[playerId].getPosition(), new Vector2d(0, 0));
            } else {
                m = new Missile(paintGreen, playerId, this.avatars[playerId].getPosition(), new Vector2d(0, 0));
            }
            m.setVelocityByDir(this.avatars[playerId].getDirection());
            m.velocity.add(currentShip.getVelocity());
            m.setPlayerId(playerId);
            m.getPosition().add(m.velocity, (currentShip.getRadius() + m.getRadius()) * 1.5 / m.velocity.mag());
            this.objects.add(m);
        }
    }

    public SpaceBattleGameModel copy() {
        SpaceBattleGameModel state = new SpaceBattleGameModel(width, height);
        state.avatars = copyShips();
        state.objects = copyObjects();
        return state;
    }

    protected void removeDead() {
        for(int i=objects.size()-1; i>=0; i--) {
            GameObject ob = objects.get(i);
            if(ob.isDead())
                objects.remove(i);
        }
    }

    protected ArrayList<GameObject> copyObjects() {
        ArrayList<GameObject> objectClone = new ArrayList<>();
        for (GameObject object : objects) {
            objectClone.add(object.copy());
        }

        return objectClone;
    }

    protected Ship[] copyShips() {
        Ship[] avatarClone = new Ship[NB_SHIP];
        for (int i=0; i<NB_SHIP; i++) {
            avatarClone[i] = avatars[i].copy();
        }

        return avatarClone;
    }


    private void wrap(GameObject ob) {
        /** only wrap objects which are wrappable */
        if (ob.isWrappable()) {
            double x = (ob.getPosition().x + width) % width;
            double y = (ob.getPosition().y + height) % height;

            ob.setPosition(x, y);
        }
    }
}
