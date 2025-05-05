package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to notify that the player is waiting to start the game.
 */
public class WaitingInTheLobbyMessage extends MessagesToClient{

    public WaitingInTheLobbyMessage() {
        super(false);
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayWaitingMessage();
    }

    @Override
    public String toString(){
        return "Sending waiting in the lobby message";
    }
}
