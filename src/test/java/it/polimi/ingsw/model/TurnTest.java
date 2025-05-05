package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    @Test
    void setCurrent_player() {
        Turn turn = new Turn();
        turn.setCurrent_player(2);
        assertEquals(2,turn.getCurrent_player());
    }
}