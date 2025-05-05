package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message to display the info of the islands.
 */
public class DisplayIslandInfoReply implements MessagesToServer{

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setDisplayIslandInfo(true);

    }

    @Override
    public String toString(){
        return "Received reply";
    }
}
