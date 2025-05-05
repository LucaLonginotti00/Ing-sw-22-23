package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.GameMode;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.toServer.GameModeReply;
import it.polimi.ingsw.network.message.toServer.NameReply;
import it.polimi.ingsw.network.message.toServer.NumberOfPlayersReply;
import it.polimi.ingsw.network.message.toServer.WaitingInTheLobbyReply;
import java.io.IOException;

/**
 * This class represents the start of the game.
 */
public class StartGame {
    private static final String DEFAULT_ADDRESS = "127.0.0.1";
    private static final int DEFAULT_PORT = 1250;

    /**
     * This method handles the starting connection.
     * @param cli The CLI main.
     * @param firstConnection {@code False} if it's the first connection.
     * @return The client.
     */
    public static Client InitialConnection(CLI cli, boolean firstConnection){
        int port;
        String IPAddress;
        boolean firstTry = true;

        do{
            if (!firstConnection && firstTry){
                System.out.println(" IP address and port provided are not valid please try again.");
            }
            if(firstTry)
                System.out.println("Enter the server's IP address or d for the default configuration:");
            else if (!firstTry)
                System.out.println("Invalid IP address, insert another address or enter d for default configuration: ");
            firstTry = false;
            IPAddress = InputParser.getLine();
            if (IPAddress.equalsIgnoreCase("d")){
                IPAddress = DEFAULT_ADDRESS;
                port = DEFAULT_PORT;
                return new Client(IPAddress, port, cli);
            }
        }while(!Utils.IPAddressIsValid(IPAddress));
        firstTry = true;
        //Insert port number
        do{
            if(firstTry)
                System.out.println("Enter the port you want to connect to: ");
            else
                System.out.println("Invalid port number please enter an integer between 1024 and 65535");
            firstTry = false;
            port = InputParser.getInt();
        }while (!Utils.portIsValid(port));
        return new Client(IPAddress, port, cli);
    }


    /**
     * This method displays the nickname request.
     * @param client The client.
     * @param retry {@code False} if it's the first request of nickname, {@code True} if not.
     */
    public static void displayNicknameRequest(Client client, boolean retry) {
        boolean check = false;
        if (!retry)
            System.out.println("Insert your nickname");
        else {
            System.out.println("Your nickname has already been taken, insert another one");
        }
        String selection = InputParser.getLine();
        while (!check) {
            if (selection == null) {
                System.out.println("Your nickname is invalid, please insert another one\n");
                selection = InputParser.getLine();
            } else {
                check = true;
                client.setName(selection);
                client.sendMessageToServer(new NameReply(selection));
            }

        }
    }

    /**
     * This method displays the game mode request.
     * @param client The client.
     */
    public static void displayGameModeRequest(Client client) {
        if (client.getGameMode().isPresent()){
            client.sendMessageToServer(new GameModeReply(client.getGameMode().get()));
            return;
        }
        System.out.println("\nConnection established!\n");
        System.out.println("Please choose the game mode, standard[s] or expert[e]:");
        GameMode gameMode = null;
        try {
            gameMode = getGameMode(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (gameMode == null)
            return;
        client.setGameMode(gameMode);
        client.sendMessageToServer(new GameModeReply(gameMode));
    }

    /**
     * This method gets the game mode.
     * @param client The client.
     * @return The game mode.
     * @throws IOException
     */
    private static GameMode getGameMode(Client client) throws IOException {
        while(true) {
            String gameModeString = InputParser.getLine();
            if (gameModeString == null)
                throw new IOException();
            if (gameModeString.equals("s"))
                return GameMode.STANDARD;
            else if (gameModeString.equals("e"))
                return GameMode.EXPERT;
            else {
                System.out.println("Invalid game mode, type  [s] for standard mode or [e] for expert mode");
            }
        }
    }

    /**
     * This method requests the number of players in the game.
     * @param client The client.
     */
    public static void NumberOfPlayersRequest(Client client){
        System.out.println("Insert the number of players [2] , [3] ");
        Integer choice = InputParser.getInt("Invalid number of players: please insert an integer number between 2 and 3", CLI.conditionOnIntegerRange(2, 3));
        if (choice != null)
            client.sendMessageToServer(new NumberOfPlayersReply(choice));
    }

    /**
     * This method displays the waiting message.
     */
    public static void displayWaitingMessage(Client client) {
        System.out.println("\nmaking the island floating...\n");
        client.sendMessageToServer(new WaitingInTheLobbyReply());
    }
}
