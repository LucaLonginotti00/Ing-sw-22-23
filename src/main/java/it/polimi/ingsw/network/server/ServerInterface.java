package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.GameMode;

/**
 * This interface represents some server methods.
 */
public interface ServerInterface {

    /**
     * This method starts a new game manager.
     * @param mode The game mode.
     */
    void newGameManager(GameMode mode);

    /**
     * This method sets the number of players for the next game.
     * @param clientHandler The client handler interface.
     * @param numberOfPlayersForNextGame The number of players for the next game.
     */
    void setNumberOfPlayersForNextGame(ClientHandlerInterface clientHandler, int numberOfPlayersForNextGame);
}

