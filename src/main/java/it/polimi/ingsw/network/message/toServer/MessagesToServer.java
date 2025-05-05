package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

import java.io.Serializable;

/**
 * Interface for the messages to the server.
 */
public interface MessagesToServer extends Serializable {
    void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler);
}
