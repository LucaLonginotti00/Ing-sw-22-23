package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.view.ViewInterface;
import java.io.Serializable;

/**
 * General features of a message from the server to the client.
 */
public abstract class MessagesToClient implements Serializable {
    private boolean timer;

    public MessagesToClient(boolean timer){
        this.timer = timer;
    }

    public abstract void handleMessage(ViewInterface view);

    public boolean hasTimer(){
        return timer;
    }
}
