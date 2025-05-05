package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    List<Player> lPlayers = new ArrayList<>();
    private List<Player> addLPlayers(List lPlayers){
        lPlayers.add(new Player("ff",1, TowerColour.GREY,1));
        lPlayers.add(new Player("gg",2,TowerColour.GREY,2));
        return lPlayers;
    }
    
    Knight knight = new Knight();
    GameState game_state = new GameState(2, new String[]{"FF", "HH"},new int[]{1,2},true, 1, addLPlayers(lPlayers));

    @Test
    public void testSetUses1(){
        knight.setUses();

        assertEquals(1, knight.getUses());
    }
    @Test
    public void testSetUses2(){
        knight.setUses();
        knight.setUses();

        assertNotEquals(1, knight.getUses());
    }

    @Test
    public void testID(){
        assertEquals(8,knight.getID_code());
    }

    @Test
    public void testCost(){
        assertEquals(2, knight.getCost());
    }


    @Test
    public void testEffect(){
        knight.effect(game_state);
        game_state.getGT().getBoards()[0].setArrProfessors(new boolean[]{true,true,true,true,true});
        game_state.getGT().getIslands().get(game_state.getGT().getMother_nature_pos()).calculate_influence(1, game_state.getGT().getBoards());


        assertEquals(0, game_state.getGT().getIslands().get(game_state.getGT().getMother_nature_pos()+1).getInfluence_controller());
    }


}