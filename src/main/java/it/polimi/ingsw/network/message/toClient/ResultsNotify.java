package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

/**
 * This message is used to show the results of the game.
 */
public class ResultsNotify extends MessagesToClient {

    List<Island> islands;
    List<Player> players;
    Board[] boards;

    public ResultsNotify(List<Island> islands, List<Player> players, Board[] boards) {
        super(true);
        this.islands = islands;
        this.players = players;
        this.boards=boards;
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayResults(islands,players,boards);
    }

    @Override
    public String toString(){
        return "Shows the results of the game";
    }
}
