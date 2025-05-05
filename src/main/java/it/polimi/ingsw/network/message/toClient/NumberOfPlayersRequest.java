package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to ask the number of players.
 */
public class NumberOfPlayersRequest extends MessagesToClient{

    public NumberOfPlayersRequest() {
        super(true);
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayNumberOfPlayersRequest();
    }

    @Override
    public String toString(){
        return "Asking the desired number of players";
    }
}
