package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message for the mother nature movement.
 */
public class MoveMnReply implements MessagesToServer{
    private final int Mnmovement;

    public MoveMnReply(int Mnmovement){
        this.Mnmovement = Mnmovement;
    }

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {

        clientHandler.setMnmovement(Mnmovement);
    }

    @Override
    public String toString() {
        return "Received Movement: " + Mnmovement;
    }

}
