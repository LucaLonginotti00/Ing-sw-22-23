package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message to display the previously chosen student.
 */
public class DisplayStudentChosenPreviouslyReply implements MessagesToServer{

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setDisplayStudentChosenPreviously(true);

    }

    @Override
    public String toString(){
        return "Received reply";
    }
}
