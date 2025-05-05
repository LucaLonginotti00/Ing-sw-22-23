package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameState;

/**
 * This class represents the magic mailman character card.
 */
public class MagicMailman extends CharacterCard {
    private final int ID_code=4;
    private int cost=1;
    private int uses=0;
    private String name="MAGIC MAILMAN";

    /**
     * This method implements the effect of the magic mailman card.
     * When played, it increases the possible moves of mother nature by 2.
     * @param game_state The game state.
     */
    @Override
    public void effect(GameState game_state){
       game_state.getPlayers()[game_state.getGT().getCurrent_player()].setMoves(game_state.getPlayers()[game_state.getGT().getCurrent_player()].getChosen_card().getMother_nature_movement()+2);
        this.setUses();
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