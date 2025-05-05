package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.message.ConnectionMessage;
import it.polimi.ingsw.network.message.toClient.*;
import it.polimi.ingsw.network.message.toServer.MessagesToServer;
import it.polimi.ingsw.model.GameMode;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;

/**
 * This class is used by the server to handle the connection with the client.
 * Each client has a corresponding client handler.
 */
public class ClientHandler implements ClientHandlerInterface, Runnable {
    public static final int HEARTBEAT = 5000;
    public static final int TIMEOUT_FOR_RESPONSE = 240000;
    private final Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Server server;
    private Thread timer;
    private GameController gameController;
    private final Thread pinger;
    private boolean active = false;
    private boolean validNickname;
    private int mnmovement;
    private int pos;
    private int islandToMove;
    private int studToMove;
    private int positionChosen;
    private int colour;
    private  int assistantCardChosen;
    private  int cloudChosen;
    private int useChCard=0;
    private int ChCardUsed;
    private boolean canBeUsed;
    private boolean waitingInTheLobby;
    private boolean displayDiningRoom;
    private boolean displayIslandInfo;
    private boolean displayStudentChosenPreviously;
    private boolean resultNotify;
    private boolean timeoutExpired;
    private boolean notEnoughCoin;
    private int monkStudent;
    private int heraldIsland;
    private String nickname;
    private boolean gameStarted = false;
    private GameMode gameMode;


    /**
     * Constructor of the class.
     * It sends a ping message that check the client's connection until it become inactive.
     * @param socket The socket of the client.
     * @param server Server used for the connection.
     */
    public ClientHandler(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.validNickname = false;
        this.pinger = new Thread(() -> {
            while (active){
                try{
                    Thread.sleep(HEARTBEAT);
                    sendMessageToClient(ConnectionMessage.PING);
                }catch (InterruptedException e){
                    break;
                }
            }
        });
    }

    /**
     * This method is used to start receiving messages in the client handler.
     */
    public void run(){
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            active = true;
            pinger.start();
            while(active){
                try {
                    Object message = inputStream.readObject();
                    if(message != null && !(message == ConnectionMessage.PING)) {
                        stopTimer();
                        Server.SERVER_LOGGER.log(Level.INFO, "[" + (nickname != null ? nickname : socket.getInetAddress().getHostAddress()) + "]: " + message);
                            ((MessagesToServer) message).handleMessage(server, this);
                    }
                } catch (ClassNotFoundException ignored) {
                } catch (SocketTimeoutException e){ //when the timer has expired
                    sendMessageToClient(new TimeoutExpiredMessage());
                    handleSocketDisconnection(true);
                } catch (IOException e){//when the client is no longer connected
                    handleSocketDisconnection(false);
                }
            }
        }catch (IOException e){
            boolean timeout = e instanceof SocketTimeoutException;
            handleSocketDisconnection(timeout);
        }
    }

    /**
     * This method is used to start timer.
     */
    @Override
    public void startTimer(){
        timer = new Thread(() -> {
            try{
                Thread.sleep(TIMEOUT_FOR_RESPONSE);
                handleSocketDisconnection(true);
            } catch (InterruptedException e){ }
        });
        timer.start();
    }

    /**
     * This method is used to stop the timer.
     */
    public void stopTimer(){
        if (timer != null && timer.isAlive()){
            timer.interrupt();
            }
    }

    /**
     * This method sends a message to the client.
     * @param message The message to be sent.
     */
    @Override
    public synchronized void sendMessageToClient(Serializable message) {
        try {
            if (checkMessage(message))
                Server.SERVER_LOGGER.log(Level.INFO, "[" + (nickname != null ? nickname : socket.getInetAddress().getHostAddress()) + "]: " + message.toString());

            outputStream.writeObject(message);
            outputStream.flush();
            outputStream.reset();
            System.out.println();
            if (!message.equals(ConnectionMessage.PING)){
             wait();}
            if (message instanceof MessagesToClient &&((MessagesToClient) message).hasTimer())
                startTimer();
        } catch (IOException e) {

            handleSocketDisconnection(e instanceof SocketTimeoutException);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to check whether a message should be printed.
     * @param message The message to check.
     * @return {@code True} if the message must be printed.
     */
    private boolean checkMessage(Serializable message){
        return  (message != ConnectionMessage.PING && !(message instanceof TextMessage));
    }

    /**
     * This method handles client's disconnection.
     * @param timeout {@code True} if the timeout has been reached, {@code False} if not.
     */
    //If the timer is expired or the ping message cannot be sent due to disconnection of the client (it throws IO Exception) I tell the client that he has been disconnected
    private void handleSocketDisconnection(boolean timeout){
        stopTimer();
        if (!active)
            return;
        //The connection is not active anymore
        this.active = false;
        Server.SERVER_LOGGER.log(Level.SEVERE, "[" + (nickname != null ? nickname : socket.getInetAddress().getHostAddress())+ "]: " + "client disconnected" + (timeout ? " because the timeout has expired" : ""));

            gameController.handleClientDisconnection(nickname);

        try {
            if (timeout)
                outputStream.writeObject(new TimeoutExpiredMessage());
            else
                outputStream.writeObject(ConnectionMessage.CONNECTION_CLOSED);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) { }
        try {
            inputStream.close();
        } catch (IOException e) {
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    /**
     * This method returns the game mode selected.
     * @return The game mode selected.
     */
    @Override
    public GameMode getGameMode() {
        return gameMode;
    }

    /**
     * This method returns the nickname.
     * @return The nickname.
     */
    @Override
    public String getNickname(){
        return nickname;
    }

    /**
     * This method checks if mother nature can be moved.
     * @return {@code True} if mother nature can be moved, {@code False} if not.
     */
    @Override
    public boolean checkMnMovThisTurn() {
        return false;
    }

    /**
     * This method sets the position where to move the student.
     * @param pos The position where to move the student.
     */
    @Override
    public synchronized void setposToMove(int pos) {
        this.pos = pos;
        notify();
    }

    /**
     * This method sets the nickname.
     * @param nickname The nickname.
     */
    @Override
    public synchronized void setNickname(String nickname){
        this.nickname = nickname;
        notify();
    }

    /**
     * This method sets the movement of mother nature.
     * @param mnmovement The movement of mother nature.
     */
    @Override
    public synchronized void setMnmovement(int mnmovement) {
        this.mnmovement = mnmovement;
        notify();
    }

    /**
     * This method sets the game mode.
     * @param gameMode The game mode.
     */
    @Override
    public synchronized void setGameMode(GameMode gameMode){
        this.gameMode = gameMode;
        notify();
    }

    /**
     * This method sets the start of the game.
     * @param gameStarted {@code true} to start the game.
     */
    @Override
    public synchronized void setGameStarted(boolean gameStarted){
        this.gameStarted = gameStarted;
        notify();
    }

    /**
     * This method sets the number of players for the next game.
     * @param numberOfPlayersForNextGame The number of players for the next game.
     */
    public synchronized void setNumberOfPlayersForNextGame(int numberOfPlayersForNextGame){
        server.setNumberOfPlayersForNextGame(this, numberOfPlayersForNextGame);
        notify();
    }

    /**
     * This method sets the game controller.
     * @param gameController The game controller.
     */
    public synchronized void setGameController(GameController gameController){
        this.gameController = gameController;
        notify();
    }

    /**
     * This method returns the game controller.
     * @return The game controller.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * This method returns the server.
     * @return The server.
     */
    public Server getServer(){
        return server;
    }

    /**
     * This method sets a valid nickname.
     * @param validNickname A valid nickname.
     */
    public synchronized void setValidNickname(boolean validNickname) {
        this.validNickname = validNickname;
        notify();
    }

    /**
     * This method sets the island where to move the student.
     * @param islandToMove The island where to move the student.
     */
    @Override
    public synchronized void setIslandToMove(int islandToMove) {
        this.islandToMove = islandToMove;
        notify();
    }

    /**
     * This method sets the student to be moved.
     * @param studToMove The student to be moved.
     */
    @Override
    public synchronized void setstudToMove(int studToMove) {
        this.studToMove=studToMove;
        notify();
    }

    /**
     * This method sets the position.
     * @param positionChosen The position.
     */
    @Override
    public synchronized void setpos(int positionChosen) {
        this.positionChosen=positionChosen;
        notify();
    }

    /**
     * This method sets the colour.
     * @param colour The colour.
     */
    @Override
    public synchronized void setcolour(int colour) {
        this.colour=colour;
        notify();
    }

    /**
     * This method sets the chosen cloud.
     * @param cloudChosen The chosen cloud.
     */
    @Override
    public synchronized void setCloudChosen(int cloudChosen) {
        this.cloudChosen = cloudChosen;
        notify();
    }

    /**
     * This method sets the chosen assistant card to be played.
     * @param assistantCardChosen The chosen assistant card to be played.
     */
    @Override
    public synchronized void setAssistantCardChosen(int assistantCardChosen) {
        this.assistantCardChosen = assistantCardChosen;
        notify();
    }

    /**
     * This method returns the island where to move the chosen student.
     * @return  The island where to move the chosen student.
     */
    public int getIslandToMove() {
        return islandToMove;
    }

    /**
     * This method returns the movement of mother nature.
     * @return The movement of mother nature.
     */
    public int getMnmovement() {
        return mnmovement;
    }

    /**
     * This method returns the chosen assistant card to be played.
     * @return The chosen assistant card to be played.
     */
    public int getAssistantCardChosen() {
        return assistantCardChosen;
    }

    /**
     * This method returns the chosen student to be moved.
     * @return The chosen student to be moved.
     */
    public int getStudToMove() {
        return studToMove;
    }

    /**
     * This method returns the position where to move the chosen student.
     * @return The position where to move the chosen student.
     */
    public int getPos() {
        return pos;
    }

    /**
     * This method returns the chosen cloud.
     * @return The chosen cloud.
     */
    public int getCloudChosen() {
        return cloudChosen;
    }

    /**
     * This method sets if the player wants to play a card.
     * @param useChCard The parameter used to see if the player wants to play a card.
     */
    public synchronized void setUseCharacterCard(int useChCard) {
        this.useChCard = useChCard;
        notify();
    }

    /**
     * This method returns value that is used to see if the player wants to play a card.
     * @return A value that is used to see if the player wants to play a card.
     */
    public int getUseCharacterCard() {
        return useChCard;
    }

    /**
     * This method sets the card chosen to be played by the player.
     * @param chCardUsed The card chosen to be played by the player.
     */
    public synchronized void setChCardUsed(int chCardUsed) {
        ChCardUsed = chCardUsed;
        notify();
    }

    /**
     * This method returns the card chosen to be played by the player.
     * @return The card chosen to be played by the player.
     */
    public int getChCardUsed() {
        return ChCardUsed;
    }

    /**
     * This method checks if it can be used.
     * @return {@code True} if it can be used, {@code False} if it can't.
     */
    public boolean getCanBeUsed() {
        return canBeUsed;
    }

    /**
     * This method sets if the character card can be used.
     * @param canBeUsed {@code True} if it can be used, {@code False} if it can't.
     */
    public synchronized void setCanBeUsed(boolean canBeUsed) {
        this.canBeUsed = canBeUsed;
        notify();
    }

    /**
     * This method sets the waiting in the lobby.
     * @param waitingInTheLobby The waiting in the lobby.
     */
    public synchronized void setWaitingInTheLobby(boolean waitingInTheLobby) {
        this.waitingInTheLobby = waitingInTheLobby;
        notify();
    }

    /**
     * This method sets the display of the dining room.
     * @param displayDiningRoom The display of the dining room.
     */
    public synchronized void setDisplayDiningRoom(boolean displayDiningRoom) {
        this.displayDiningRoom = displayDiningRoom;
        notify();
    }

    /**
     * This method sets the display of island info.
     * @param displayIslandInfo The display of island info.
     */
    public synchronized void setDisplayIslandInfo(boolean displayIslandInfo) {
        this.displayIslandInfo = displayIslandInfo;
        notify();
    }

    /**
     * This method sets the display of the previously chosen student.
     * @param displayStudentChosenPreviously The previously chosen student.
     */
    public synchronized void setDisplayStudentChosenPreviously(boolean displayStudentChosenPreviously) {
        this.displayStudentChosenPreviously = displayStudentChosenPreviously;
        notify();
    }

    /**
     * This method notifies the results.
     * @param resultNotify The results.
     */
    public synchronized void setResultNotify(boolean resultNotify) {
        this.resultNotify = resultNotify;
        notify();
    }

    /**
     * This method sets if the massage has expired.
     * @param timeoutExpired the expired message.
     */
    public synchronized void setTimeoutExpired(boolean timeoutExpired) {
        this.timeoutExpired = timeoutExpired;
        notify();
    }

    /**
     * This method sets if there are not enough coins.
     * @param notEnoughCoin The coins checker.
     */
    public synchronized void setNotEnoughCoin(boolean notEnoughCoin) {
        this.notEnoughCoin = notEnoughCoin;
        notify();
    }

    /**
     * This method sets the student on the monk card.
     * @param monkStudent The student on the monk card.
     */
    public synchronized void setMonkStudent(int monkStudent) {
        this.monkStudent = monkStudent;
        notify();
    }

    /**
     * This method returns the student chosen from the monk card.
     * @return The student chosen from the monk card.
     */
    public int getMonkStudent() {
        return monkStudent;
    }

    /**
     * This method sets the island chosen after the herald character card.
     * @param heraldIsland The island chosen after the herald character card.
     */
    public synchronized void setHeraldIsland(int heraldIsland) {
        this.heraldIsland=heraldIsland;
        notify();
    }

    /**
     * This method returns the chosen island after the herald character card.
     * @return The chosen island after the herald character card.
     */
    public int getHeraldIsland() {
        return heraldIsland;
    }
}

