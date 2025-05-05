package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message for the colour.
 */
public class ColourResponse implements MessagesToServer {
    private final int colour;

    public ColourResponse(int colour){this.colour=colour;}

    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {

        clientHandler.setcolour(colour);
    }

    @Override
    public String toString() {
        return "Colour Received";
    }
}
