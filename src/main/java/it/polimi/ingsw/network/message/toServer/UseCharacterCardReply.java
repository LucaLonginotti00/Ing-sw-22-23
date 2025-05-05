package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;
/**
 * Message to choose to use a character card.
 */
public class UseCharacterCardReply implements MessagesToServer{
    private final int choice;

    public UseCharacterCardReply(int choice){this.choice=choice;}
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {

        clientHandler.setUseCharacterCard(choice);
    }
    @Override
    public String toString() {
        return "Choice Received";
    }
}
