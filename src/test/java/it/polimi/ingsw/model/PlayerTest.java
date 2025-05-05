package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the class {@link it.polimi.ingsw.model.Player}.
 */
class PlayerTest {
    Player player = new Player("ff",1, TowerColour.GREY,1);

    @Test
    public void testGetDeck(){
        Deck deck = new Deck();

        assertEquals(player.getDeck(), deck);
    }

    @Test
    public void testCoin(){
        player.setCoin(20);

        assertEquals(20, player.getCoin());
    }

    @Test
    public void testGetPlayerID() {
        assertEquals(1,player.getPlayer_ID());
    }

    @Test
    public void testGetTower_Colour() {
        assertEquals(1,player.getTower_colour());
    }

    @Test
    public void testGetName() {
        assertEquals("ff",player.getNickname());
    }

    @Test
    public void testGetWizard() {
        assertEquals(1,player.getWizard());
    }

}