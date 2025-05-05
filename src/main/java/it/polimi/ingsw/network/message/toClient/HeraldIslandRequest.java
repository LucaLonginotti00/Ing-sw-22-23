package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

/**
 * Message to request to choose an island after the use of the herald character card.
 */
public class HeraldIslandRequest extends MessagesToClient{

    List<Island> islands;
    public HeraldIslandRequest(List<Island> islands){
        super(true);
        this.islands=islands;
    }
    @Override
    public void handleMessage(ViewInterface view) {
        view.heraldIsland(islands);}


    @Override
    public String toString(){
        return "Received ask ";
    }
}
