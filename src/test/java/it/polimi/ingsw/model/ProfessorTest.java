package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorTest {

    @Test
    void getColour_prof() {
        Professor prof = new Professor(DiskColour.RED);
        assertEquals(1,prof.getColour_prof());
    }
}