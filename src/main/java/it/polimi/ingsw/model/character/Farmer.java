package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameState;

import java.io.Serializable;

/**
 * This class represents the farmer character card.
 */
public class Farmer extends CharacterCard {
    private final int ID_code=2;
    private int cost=2;
    private int uses=0;
    private String name="FARMER";

    /**
     * This method implements the effect of the farmer card.
     * When this card is played, the player takes control of the professors even if he/she has the same number of
     * students as the controller.
     * @param game_state The game state.
     */
    @Override
    public void effect(GameState game_state){

        game_state.getGT().getBoards()[game_state.getCurr_player()].setFarmer_state(true);
        setUses();
    }

    /**
     * this method return the name of the card.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method is used when a card has been used.
     * It increases the uses and the cost by 1.
     */
    public void setUses() {
        this.uses++;
        setCost(cost+1);
    }

    /**
     * This method returns the cost.
     * @return The cost.
     */
    public int getCost() {
        return cost;
    }

    /**
     * This method sets the cost.
     * @param cost The new cost of the card.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * This method returns the uses of the card.
     * @return The uses of the card.
     */
    public int getUses() {
        return uses;
    }

    /**
     * This method returns the ID code of the card.
     * @return The ID code of the card.
     */
    public int getID_code() {
        return ID_code;
    }
}