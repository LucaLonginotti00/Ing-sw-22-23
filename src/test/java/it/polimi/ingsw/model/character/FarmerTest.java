package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FarmerTest {

    List<Player> lPlayers = new ArrayList<>();
    private List<Player> addLPlayers(List lPlayers){
        lPlayers.add(new Player("ff",1, TowerColour.GREY,1));
        lPlayers.add(new Player("gg",2,TowerColour.GREY,2));
        return lPlayers;
    }

    Farmer farmer = new Farmer();
    GameState game_state = new GameState(2, new String[]{"FF", "HH"},new int[]{1,2},false, 1, addLPlayers(lPlayers));

    @Test
    public void testSetUses1(){
        farmer.setUses();

        assertEquals(1, farmer.getUses());
    }
    @Test
    public void testSetUses2(){
        farmer.setUses();
        farmer.setUses();

        assertNotEquals(1, farmer.getUses());
    }

    @Test
    public void testID(){
        assertEquals(2, farmer.getID_code());
    }

    @Test
    public void testCost(){
        assertEquals(2, farmer.getCost());
    }

    @Test
    public void testEffect(){
        
        farmer.effect(game_state);

        assertEquals(game_state.getGT().getBoards()[game_state.getCurr_player()].isFarmer_state(), true);

    }

}
