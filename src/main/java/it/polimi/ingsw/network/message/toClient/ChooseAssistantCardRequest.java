package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.GameTable;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to choose an assistant card.
 */
public class ChooseAssistantCardRequest extends MessagesToClient {
    Player player;
    GameTable GT;
    public ChooseAssistantCardRequest(Player player, GameTable GT){
        super(true);
        this.player=player;
        this.GT=GT;
    }


    @Override
    public void handleMessage(ViewInterface view) {
        view.displayChooseAssistantCardRequest(player,GT);}

    @Override
    public String toString() {return "Asking to choose an assistant card";}

}
