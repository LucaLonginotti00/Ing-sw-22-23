package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message to move students.
 */
public class MoveStudentReply implements MessagesToServer{
    private final int IslandToMove;

    public MoveStudentReply(int IslandToMove){
        this.IslandToMove=IslandToMove;
    }


    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler){
        clientHandler.setIslandToMove(IslandToMove);
    }

    @Override
    public String toString() {
        return "Received Island to move";
    }
}
