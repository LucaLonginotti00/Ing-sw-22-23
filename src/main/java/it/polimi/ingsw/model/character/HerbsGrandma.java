package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameState;

/**
 * This class represents the herbs grandma character card.
 * This card starts with 4 prohibition cards that can be put on the island.
 */
public class HerbsGrandma extends CharacterCard {
    private final int ID_code=5;
    private int cost=1;
    private int uses=0;
    private int prohibition_cards = 4;
    private int index_to=0;
    private String name="HERBS GRANDMA";

    /**
     * This method implements the effect of the herbs grandma card.
     * When played, a prohibition card is moved to the selected island.
     * If mother nature moves on that island the influence is not calculated and the towers are not placed.
     * The prohibition card is moved again into this card.
     * @param game_state The game state.
     */
    @Override
    public void effect(GameState game_state){
        if (prohibition_cards==0) {
            return;
        }
        else {
            prohibition_cards--;
            game_state.getGT().getIslands().get(index_to).setProhibition_card(true);
            this.setUses();
        }
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
     * this method return the name of the card.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method returns the prohibition card to the character card by increasing its attribute by 1.
     */
    public void setProhibition_cards() {
        if (prohibition_cards<4) this.prohibition_cards++;
        else return;
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