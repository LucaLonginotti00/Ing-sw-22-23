package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//100% COVERAGE

/**
 * This class tests the class {@link HerbsGrandma}.
 */
class HerbsGrandmaTest {

    List<Player> lPlayers = new ArrayList<>();
    private List<Player> addLPlayers(List lPlayers){
        lPlayers.add(new Player("ff",1, TowerColour.GREY,1));
        lPlayers.add(new Player("gg",2,TowerColour.GREY,2));
        return lPlayers;
    }
    HerbsGrandma grandma = new HerbsGrandma();
    GameState game_state = new GameState(2, new String[]{"FF", "HH"},new int[]{1,2},false,1, addLPlayers(lPlayers));

    @Test
    public void testSetUses1(){
        grandma.setUses();

        assertEquals(1, grandma.getUses());
    }
    @Test
    public void testSetUses2(){
        grandma.setUses();
        grandma.setUses();

        assertNotEquals(1, grandma.getUses());
    }

    @Test
    public void testID(){
        assertEquals(5, grandma.getID_code());
    }

    @Test
    public void testCost(){
        assertEquals(1, grandma.getCost());
    }


    @Test
    public void testEffect1(){
        grandma.setIndex_to(2);
        grandma.effect(game_state);

        assertEquals(true, game_state.getGT().getIslands().get(2).isProhibition_card());
    }

    @Test
    public void testEffect2(){
        grandma.setIndex_to(4);
        grandma.effect(game_state);
        grandma.setProhibition_cards();

        assertNotEquals(true, game_state.getGT().getIslands().get(1).isProhibition_card());
    }


}