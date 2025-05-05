package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message to confirm that the previous message has been received.
 */
public class ShowStudentsReply implements MessagesToServer{
    private final int index;

    public ShowStudentsReply(int index){this.index=index;}

    public int getIndex() {
        return index;
    }

    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {

        clientHandler.setMonkStudent(index);
    }

    @Override
    public String toString() {
        return "Received reply";
    }
}
