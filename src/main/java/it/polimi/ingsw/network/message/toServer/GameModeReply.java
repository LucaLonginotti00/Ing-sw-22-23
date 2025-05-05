package it.polimi.ingsw.network.message.toServer;


import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Message with the chosen game mode by the client.
 */
public class GameModeReply implements MessagesToServer{
    private  GameMode gameMode;

    public GameModeReply(GameMode gameMode){
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public synchronized void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler){

        clientHandler.setGameMode(gameMode);
    }

    @Override
    public String toString() {
        return "Received game mode: " + gameMode.name().replace("_"," ");
    }
}
