package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message to confirm that the previous message has been received.
 */
public class WaitingInTheLobbyReply implements MessagesToServer {


    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setWaitingInTheLobby(true);

    }

    @Override
    public String toString(){
        return "Received reply";
    }
}
