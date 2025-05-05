package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message for the chosen assistant card.
 */
public class ChooseAssistantCardReply implements MessagesToServer{
    private final int AssistantCardChosen;

    public ChooseAssistantCardReply(int AssistantCardChosen){this.AssistantCardChosen=AssistantCardChosen;}


    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {

        clientHandler.setAssistantCardChosen(AssistantCardChosen);
    }

    @Override
    public String toString() {
        return "Received Assistant card chosen: " + AssistantCardChosen;
    }

}
