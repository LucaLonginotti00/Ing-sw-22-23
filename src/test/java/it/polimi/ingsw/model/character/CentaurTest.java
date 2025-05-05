package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the class {@link it.polimi.ingsw.model.character.Centaur}.
 */


class CentaurTest {
    List<Player> lPlayers = new ArrayList<>();
    private List<Player> addLPlayers(List lPlayers){
        lPlayers.add(new Player("ff",1, TowerColour.GREY,1));
        lPlayers.add(new Player("gg",2,TowerColour.GREY,2));
        return lPlayers;
    }

    Centaur centaur = new Centaur();
    GameState game_state = new GameState(2, new String[]{"FF", "HH"},new int[]{1,2},false, 1, addLPlayers(lPlayers));

    @Test
    public void testSetUses1(){
        centaur.setUses();

        assertEquals(1, centaur.getUses());
    }
    @Test
    public void testSetUses2(){
        centaur.setUses();
        centaur.setUses();

        assertNotEquals(1, centaur.getUses());
    }

    @Test
    public void testID(){
        assertEquals(6, centaur.getID_code());
    }

    @Test
    public void testCost(){
        assertEquals(3, centaur.getCost());
    }

    @Test
    public void testEffect(){

        centaur.effect(game_state);
        game_state.getGT().getIslands().get(game_state.getGT().getMother_nature_pos()).calculate_influence(1, game_state.getGT().getBoards());

        assertEquals(0, game_state.getGT().getIslands().get(game_state.getGT().getMother_nature_pos()).getInfluence_controller());
    }


}