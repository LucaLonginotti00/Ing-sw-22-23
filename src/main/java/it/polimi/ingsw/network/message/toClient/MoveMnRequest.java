package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.ViewInterface;


/**
 * Message to ask to move mother nature.
 */
public class MoveMnRequest extends MessagesToClient{
    int Mn_pos;
    Player Current_Player;

    public MoveMnRequest(int Mn_pos, Player Current_Player){
        super(true);
        this.Mn_pos=Mn_pos;
        this.Current_Player=Current_Player;
    }


    @Override
    public void handleMessage(ViewInterface view) {
        view.motherNatureMovementRequest(Mn_pos,Current_Player);
    }

    @Override
    public String toString(){
        return "Asking to move mother nature";
    }
}
