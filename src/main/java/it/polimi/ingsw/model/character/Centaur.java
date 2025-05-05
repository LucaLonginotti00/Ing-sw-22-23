package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.CharacterCard;
import it.polimi.ingsw.model.GameState;

/**
 * This class represents the centaur character card.
 */
public class Centaur extends CharacterCard {
    private final int ID_code=6;
    private int cost=3;
    private int uses=0;
    private String name="CENTAUR";

    /**
     * This method implements the effect of the centaur card.
     * When this card is played, the influence on the island where mother nature is, is calculated without counting the towers.
     * @param GS The game state.
     */
    @Override
    public void effect(GameState GS){

        for(int i=0;i< GS.getGT().getIslands().size();i++){
            GS.getGT().getIslands().get(GS.getGT().getMother_nature_pos()).setInclude_towers(false);
        }
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
