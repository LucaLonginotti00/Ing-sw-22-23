package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message for the reply of the position.
 */
public class PositionReply implements MessagesToServer{
    private final int pos;

    public PositionReply(int pos){this.pos=pos;}

    public int getPos() {
        return pos;
    }

    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {

        clientHandler.setpos(pos);
    }

    @Override
    public String toString() {
        return "Position Received";
    }
}
