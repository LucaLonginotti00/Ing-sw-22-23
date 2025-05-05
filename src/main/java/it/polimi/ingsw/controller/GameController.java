package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColour;
import it.polimi.ingsw.network.message.toClient.*;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * This class is used to manage game phases.
 */
public class GameController implements Serializable {

    private static final long serialVersionUID = 4405183481677036856L;
    private GameMode gameMode;
    private List<Player> players;
    private String[] names;
    private List<ClientHandler> clientHandlers;
    public static final String SERVER_NICKNAME = "server";
    private Server server;
    private ReentrantLock lockConnections = new ReentrantLock(true);
    private int[] wizards = {0,1,2,3};
    private TurnController turnController;

    /**
     * Constructor of the class.
     * @param gameMode The game mode.
     */
    public GameController(GameMode gameMode){
        this.gameMode = gameMode;
        this.players = new LinkedList<>() ;
        this.clientHandlers = new LinkedList<>();
    }

    /**
     * This method handles a client's disconnection.
     * @param nickname The nickname of the disconnected client.
     */
    public synchronized void handleClientDisconnection(String nickname) {
        ClientHandler connection = getConnectionByNickname(nickname);
        forceEndMultiplayerGame(nickname);
        server.removeConnectionGame(connection);
    }

    /**
     * This method is used force the end of the game.
     * @param nickname The nickname of the disconnected client.
     */
    private void forceEndMultiplayerGame(String nickname){
        for (Player player : getPlayers_ID()) {
            //For each player that is still active I notify the end of the game
            getConnectionByNickname(player.getNickname()).sendMessageToClient(new NotifyDisconnection(nickname));
            getConnectionByNickname(player.getNickname()).setGameStarted(false);
            getConnectionByNickname(player.getNickname()).setGameController(null);

        }
    }

    /**
     * This method returns the list of players.
     * @return The list of players.
     */
    public List<Player> getPlayers_ID() {
        return players;
    }

    /**
     * This method is used to start a game.
     */
    public void start() throws IOException {
        startNewGame();
    }

    /**
     * This method starts a new game.
     */
    private void startNewGame() throws IOException {
        Server.SERVER_LOGGER.log(Level.INFO, "Creating a new " + gameMode.name().replace("_"," ") + ", players: " + clientHandlers.stream().map(ClientHandler::getNickname).collect(Collectors.toList()));
        players = addPlayer(clientHandlers);
        TurnController turnController = new TurnController(clientHandlers.size(),getArrayNickname(clientHandlers),wizards,getBooleanGameMode(gameMode),players,clientHandlers);
        turnController.setGameController(this);
        setturnController(turnController);

        while(turnController.getendgame()==false) {
            turnController.planning_phase_general();
            turnController.action_phase();
        }
        endGame(turnController);
    }

    /**
     * This method adds a connection to the client handlers' list.
     * @param connection ClientHandler of the connection to add.
     */
    public void addConnection(ClientHandler connection) {
        lockConnections.lock();
        try {
            this.clientHandlers.add(connection);
        }
        finally {
            lockConnections.unlock();
        }
    }

    /**
     * This method removes a connection from the client handlers' list.
     * Used in case of disconnection of a client.
     * @param connection ClientHandler of the connection to remove.
     */
    public void removeConnection(ClientHandler connection){
        lockConnections.lock();
        try {
            this.clientHandlers.remove(connection);
        } finally {
            lockConnections.unlock();
        }
    }

    /**
     * This method adds a player to the list, given the list of client handler.
     * @param clientHandlers The list of client handlers.
     * @return The updated list of players.
     */
    public List<Player> addPlayer(List<ClientHandler> clientHandlers) {
        TowerColour towerColour = null;
        for (int i=0; i<clientHandlers.size(); i++) {
            String nickname = clientHandlers.get(i).getNickname();
            if(clientHandlers.size()==3) {
                if(i == 0)
                    towerColour =TowerColour.BLACK;
                if (i==1)
                    towerColour = TowerColour.WHITE;
                if (i==2)
                    towerColour = TowerColour.GREY;
            }
                else if(clientHandlers.size()==2)
            {
                if (i==0)
                    towerColour = TowerColour.BLACK;
                
                if (i==1)
                    towerColour = TowerColour.WHITE;
                
            }
                int wiz = i;
                int ID = i;

            Player p = new Player(nickname,wiz,towerColour,ID);
            players.add(p);
        }
        return players;
    }

    /**
     * This method returns the array of string containing the nicknames.
     * @param clientHandlers The list of client handlers.
     * @return The array of string containing the nicknames.
     */
    public String[] getArrayNickname(List<ClientHandler> clientHandlers) {
        names = new String[clientHandlers.size()];
        for (int i=0; i<clientHandlers.size(); i++) {
                names[i] = clientHandlers.get(i).getNickname();
        }
        return names;
    }

    /**
     * This method returns a client handler, given a nickname.
     * @param nickname The nickname of the client handler wanted.
     * @return The corresponding client handler.
     */
    public ClientHandler getConnectionByNickname(String nickname){
        lockConnections.lock();
        try{
            for (ClientHandler clientHandler : clientHandlers){
                if (clientHandler.getNickname().equals(nickname)){
                    return clientHandler;
                }
            }
        } finally {
            lockConnections.unlock();
        }
        return null;
    }

    /**
     * This method is used to start the end game. It gives the results to the players.
     */
    public void endGame(TurnController turnController) throws IOException {
        getServer().gameEnded(this,new ResultsNotify(turnController.getGS().getGT().getIslands(),players,turnController.getGS().getGT().getBoards()));
    }

    /**
     * This method is used to send the same message to all the clients connected.
     * @param message The message to be sent.
     */
    public void sendMessageToAll(MessagesToClient message){
        lockConnections.lock();
        try{
            for (ClientHandler clientHandler : clientHandlers)
                clientHandler.sendMessageToClient(message);
        } finally {
            lockConnections.unlock();
        }
    }

    /**
     * This method returns the game mode.
     * @param gameMode The game mode.
     * @return {@code True} if the game mode is expert, {@code False} if it's standard.
     */
    public boolean getBooleanGameMode(GameMode gameMode) {
        if(gameMode==GameMode.STANDARD)
            return false;
        else
            return true;
    }

    /**
     * This method sets the server.
     * @param server The server.
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * This method returns the server.
     * @return The server.
     */
    public Server getServer() {
        return server;
    }

    /**
     * This method returns the end game.
     * @return The end game.
     */
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * This method returns the turn controller.
     * @param turnController The turn controller.
     */
    public void setturnController(TurnController turnController){
        this.turnController=turnController;
    }

}
