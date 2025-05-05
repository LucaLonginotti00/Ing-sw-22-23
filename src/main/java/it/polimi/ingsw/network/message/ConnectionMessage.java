package it.polimi.ingsw.network.message;

/**
 * This enumeration contains the basic messages for connection to server.
 */
public enum ConnectionMessage {
    PING("Ping"),
    CONNECTION_CLOSED("Connection closed");
    String message;

    /**
     * Constructor of the class.
     * @param message A message.
     */
    ConnectionMessage(String message){
        this.message = message;
    }

    /**
     * This method returns the message.
     * @return The message.
     */
    public String getMessage(){
        return message;
    }
}
