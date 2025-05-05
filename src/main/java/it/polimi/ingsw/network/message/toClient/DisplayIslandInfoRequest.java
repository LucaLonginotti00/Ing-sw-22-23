package it.polimi.ingsw.network.message.toClient;


import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to request the info of the island.
 */
public class DisplayIslandInfoRequest extends MessagesToClient{
    Island island;
    int islandID;
    public DisplayIslandInfoRequest(Island island, int islandID){
        super(true);
        this.island=island;
        this.islandID=islandID;
    }
    @Override
    public void handleMessage(ViewInterface view) {
        view.displayIslandInfo(island,islandID);}

    @Override
    public String toString(){
        return "Asking to display the info of the island";
    }
}
