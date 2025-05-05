package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to notify the disconnection of a player.
 */
public class NotifyDisconnection extends MessagesToClient{

    String name;

    public NotifyDisconnection(String name) {
        super(false);
        this.name = name;
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayDisconnection(name);
    }

    public String toString(){
        return "Notifying the disconnection of " + name;
    }
}
