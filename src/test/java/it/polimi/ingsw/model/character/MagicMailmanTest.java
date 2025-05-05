package it.polimi.ingsw.model.character;

import it.polimi.ingsw.model.AssistanceCard;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColour;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//100% COVERAGE

/**
 * This class tests the class {@link MagicMailman}.
 */
class MagicMailmanTest {

    List<Player> lPlayers = new ArrayList<>();
    private List<Player> addLPlayers(List lPlayers){
        lPlayers.add(new Player("ff",1, TowerColour.GREY,1));
        lPlayers.add(new Player("gg",2,TowerColour.GREY,2));
        return lPlayers;
    }
    MagicMailman magic_mailman = new MagicMailman();
    GameState game_state = new GameState(2, new String[]{"FF", "HH"},new int[]{1,2},false,1, addLPlayers(lPlayers));

    @Test
    public void testSetUses1(){
        magic_mailman.setUses();

        assertEquals(1, magic_mailman.getUses());
    }

    @Test
    public void testSetUses2(){
        magic_mailman.setUses();
        magic_mailman.setUses();

        assertNotEquals(1, magic_mailman.getUses());
    }


    @Test
    public void testID(){
        assertEquals(4, magic_mailman.getID_code());
    }

    @Test
    public void testCost(){
        assertEquals(1, magic_mailman.getCost());
    }


    @Test
    public void testEffect(){
        game_state.getPlayers()[0].setChosen_card(AssistanceCard.CAT);
        magic_mailman.effect(game_state);

        assertEquals(6, game_state.getPlayers()[0].getMoves());
    }


}