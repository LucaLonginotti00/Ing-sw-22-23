package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represent the professors.
 * There is one professor for each colour. See more at: {@link DiskColour}.
 */
public class Professor implements Serializable {
    private final DiskColour colour_prof;

    /**
     * Constructor of the class.
     * @param colour_prof The colour of the professor.
     */
    public Professor(DiskColour colour_prof) {
        this.colour_prof = colour_prof;
    }

    /**
     * This method returns the colour of the professor.
     * @return The int representing the colour of the professor. See more at: {@link DiskColour}.
     */
    public int getColour_prof() {
        return colour_prof.getTranslateColour();
    }
}
