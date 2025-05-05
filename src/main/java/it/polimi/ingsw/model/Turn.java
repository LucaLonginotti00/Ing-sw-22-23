package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represents the current turn. Two ints represent the current player (0) and the next player (1).
 */
public class Turn implements Serializable {
    private int current_player;

    /**
     * Constructor of the class. Sets the current player to 0.
     */
    public Turn(){
        current_player=0;
    }

    /**
     * This method returns the current player.
     * @return The number representing the current player.
     */
    public int getCurrent_player() {
        return current_player;
    }

    /**
     * This method sets the current player.
     * @param current_player The number representing the current player.
     */
    public void setCurrent_player(int current_player) {
        this.current_player = current_player;
    }
}
