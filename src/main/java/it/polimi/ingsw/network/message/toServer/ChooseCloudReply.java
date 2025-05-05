package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message for the chosen cloud.
 */
public class ChooseCloudReply implements MessagesToServer{

    private final int CloudChosen;

    public ChooseCloudReply(int CloudChosen){this.CloudChosen=CloudChosen;}

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setCloudChosen(CloudChosen);
    }

    @Override
    public String toString() {
        return "Received Cloud chosen";
    }


}
