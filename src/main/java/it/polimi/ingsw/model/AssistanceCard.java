package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This enumeration contains all the assistance cards numbered from 1 to 10.
 * The card 0 is just used to set the discard deck at the start of the game.
 */
public enum AssistanceCard implements Serializable {
    STARTER(0,0), TORTOISE(1,1), ELEPHANT(2,1),
    BULLDOG(3,2),OCTOPUS(4,2),
    LIZARD(5,3), FOX(6,3),
    EAGLE(7,4), CAT(8,4),
    OSTRICH(9,5), LION(10,5);

    private final int value;
    private final int mother_nature_movement;

    /**
     * Constructor of the class.
     * @param value The value of the card (from 1 to 10).
     * @param mother_nature_movement The max movement of mother nature that player can do when the card is played (from 1 tp 5).
     */
    AssistanceCard(int value, int mother_nature_movement) {
        this.value = value;
        this.mother_nature_movement = mother_nature_movement;
    }

    /**
     * This method returns the max movement of mother nature.
     * @return The max movement of mother nature.
     */
    public int getMother_nature_movement() {
        return mother_nature_movement;
    }

    /**
     * This method returns the value of the card.
     * @return The value of the card.
     */
    public int getValue() {
        return value;
    }

}
