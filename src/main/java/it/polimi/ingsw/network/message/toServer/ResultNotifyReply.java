package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message to confirm that the previous message has been received.
 */
public class ResultNotifyReply implements MessagesToServer{

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setResultNotify(true);

    }

    @Override
    public String toString(){
        return "Received reply";
    }

}
