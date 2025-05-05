package it.polimi.ingsw.network.message.toServer;

import it.polimi.ingsw.network.server.ClientHandlerInterface;
import it.polimi.ingsw.network.server.ServerInterface;

/**
 * Class to communicate the number of players desired.
 */
public class NumberOfPlayersReply implements MessagesToServer{
    private final int n_players;

    public NumberOfPlayersReply(int n_players){
        this.n_players = n_players;
    }

    @Override
    public void handleMessage(ServerInterface server, ClientHandlerInterface clientHandler) {
        clientHandler.setNumberOfPlayersForNextGame(n_players);
    }

    @Override
    public String toString() {
        return "Received number of players: " + n_players;
    }
}
