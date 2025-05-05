package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message to display the dining room.
 */
public class DisplayDiningRoomColourFullReply implements MessagesToServer {

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setDisplayDiningRoom(true);

    }

    @Override
    public String toString(){
        return "Received reply";
    }
}
