package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message for the position to move.
 */
public class PositionToMoveReply implements MessagesToServer{
    private final int pos;

    public PositionToMoveReply(int pos){this.pos=pos;}

    public int getPos() {
        return pos;
    }

    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setposToMove(pos);
    }

    @Override
    public String toString() {
        return "Position Received";
    }

}
