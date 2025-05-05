package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameState;

/**
 * This class represents the herald character card.
 */
public class Herald extends CharacterCard {
    private final int ID_code=3;
    private int cost=3;
    private int uses=0;
    private int index_to;
    private String name="HERALD";

    /**
     * This method implements the effect of the herald card.
     * The player chooses an island and calculates the influence of it even if mother nature is not there.
     * Then the game continues normally.
     * @param game_state The game state.
     */
    @Override
    public void effect(GameState game_state){
        game_state.getGT().getIslands().get(index_to).calculate_influence(0, game_state.getGT().getBoards());
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
     * This method sets the index of the island into which the prohibition card will be moved to.
     * @param index_to The index of the island into which the prohibition card will be moved to.
     */
    @Override
    public void setIndex_to(int index_to) {
        this.index_to = index_to;
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