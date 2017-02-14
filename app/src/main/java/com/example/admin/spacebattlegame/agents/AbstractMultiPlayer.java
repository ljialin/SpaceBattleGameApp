package com.example.admin.spacebattlegame.agents;

import com.example.admin.spacebattlegame.game.SpaceBattleGameModel;
import com.example.admin.spacebattlegame.game.Types;

import java.util.ArrayList;

/**
 * Created by Jialin Liu on 14/02/2017.
 * CSEE, University of Essex
 * jialin.liu@essex.ac.uk
 */
public abstract class AbstractMultiPlayer {
    /**
     * playerID
     */
    private int playerID;


    /**
     * Set this variable to FALSE to avoid logging the actions to a file.
     */
    private static final boolean SHOULD_LOG = true;

    /**
     * Last action executed by this agent.
     */
    private String lastAction = null;

    /**
     * Random seed of the game.
     */
    private int randomSeed;

    /**
     * Is this a human player?
     */
    private boolean isHuman;

    /**
     * Picks an action. This function is called every game step to request an
     * action from the player.
     * @param stateObs Observation of the current state.
     * @return An action for the current state
     */
    public abstract Types.ACTIONS act(SpaceBattleGameModel stateObs);

    /**
     * Function called when the game is over.
     * @param stateObs the game state at the end of the game
     */
    public void result(SpaceBattleGameModel stateObs)
    {
    }

    /**
     * This function sets up the controller to save the actions executed in a given game.
     * @param randomSeed Seed for the sampleRandom generator of the game to be played.
     * @param isHuman Indicates if the player is a human or not.
     */
    public void setup(int randomSeed, boolean isHuman) {
        this.randomSeed = randomSeed;
        this.isHuman = isHuman;
    }

    /**
     * Gets the last action executed by this controller.
     * @return the last action
     */
    public String getLastAction()
    {
        return lastAction;
    }


    /**
     * @return the ID of this player
     */
    public int getPlayerID() { return playerID; }

    /**
     * Set the ID of this player.
     * @param id - the player's ID
     */
    public void setPlayerID(int id) { playerID = id; }

    /**
     * Get the history of actions of this player.
     * @return arrayList of all actions
     */
    public ArrayList<Types.ACTIONS> getAllActions() { return Types.AVAILABLE_ACTIONS; }
}
