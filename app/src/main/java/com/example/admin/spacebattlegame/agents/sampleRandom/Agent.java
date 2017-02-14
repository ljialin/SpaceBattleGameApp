package com.example.admin.spacebattlegame.agents.sampleRandom;

/**
 * Created by Jialin Liu on 14/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */

import com.example.admin.spacebattlegame.game.SpaceBattleGameModel;
import com.example.admin.spacebattlegame.game.Types;
import com.example.admin.spacebattlegame.agents.AbstractMultiPlayer;

import java.util.ArrayList;
import java.util.Random;

public class Agent extends AbstractMultiPlayer {

    int id; //this player's ID

    public Agent(SpaceBattleGameModel stateObs, int playerID){
        id = playerID;
    }

    @Override
    public Types.ACTIONS act(SpaceBattleGameModel stateObs) {
        ArrayList<Types.ACTIONS> availableActions = getAllActions();
        return availableActions.get(new Random().nextInt(availableActions.size()));
    }
}