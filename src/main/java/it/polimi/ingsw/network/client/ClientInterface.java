package it.polimi.ingsw.network.client;

import java.io.Serializable;

/**
 * This interface contains the method to send a message to the server.
 */
public interface ClientInterface {

    /**
     * The method to send a message to the server.
     * @param message A message.
     */
    void sendMessageToServer(Serializable message);
}
