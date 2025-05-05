package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the class {@link GameState}.
 */

class GameStateTest {

    List<Player> lPlayers = new ArrayList<>();
    private List<Player> addLPlayers(List lPlayers){
        lPlayers.add(new Player("ff",1,TowerColour.GREY,1));
        lPlayers.add(new Player("gg",2,TowerColour.GREY,2));
        return lPlayers;
    }

    GameState game_state = new GameState(2, new String[]{"FF","HH"}, new int[]{1,2}, false, 1, addLPlayers(lPlayers));
    GameState game_state2 = new GameState(4,new String[]{"FF","HH","GG","LL"}, new int[]{1,2,3,4}, false, 1, addLPlayers(lPlayers));

    private Player[] players = new Player[2];

    @Test
    public void testGetGT(){
        GameState Gs = new GameState(2, new String[]{"FF","HH"}, new int[]{1,2}, true, 1, addLPlayers(lPlayers));

        assertNotEquals(game_state.getGT(), Gs.getGT());
    }

    @Test
    public void testGetPlayers() {
        players[0]= new Player("FF",1, TowerColour.STARTER,1);
        players[1]= new Player("HH",2, TowerColour.GREY,2);
        assertEquals(lPlayers, game_state.getlPlayers());
    }

    @Test
    void testValue(){
       game_state.getPlayers()[1].setChosen_card(AssistanceCard.ELEPHANT);

       assertNotEquals(30, game_state.getPlayers()[1].getChosen_card().getValue());
    }
}

