package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.GameMode;
import java.io.Serializable;

/**
 * This interface contains the methods used by the client handler.
 */
public interface ClientHandlerInterface {

    /**
     * This method sends a message to the client.
     * @param message The message to be sent.
     */
    void sendMessageToClient(Serializable message);

    /**
     * This method returns the game mode selected.
     * @return The game mode selected.
     */
    GameMode getGameMode();

    /**
     * This method returns the nickname.
     * @return The nickname.
     */
    String getNickname();

    /**
     * This method checks if mother nature can be moved.
     * @return {@code True} if mother nature can be moved, {@code False} if not.
     */
    boolean checkMnMovThisTurn();

    /**
     * This method sets the student to be moved.
     * @param stud The student to be moved.
     */
    void setstudToMove(int stud);

    /**
     * This method sets the island where to move the student.
     * @param isl The island where to move the student.
     */
    void setIslandToMove(int isl);

    /**
     * This method sets the position where to move the student.
     * @param pos The position where to move the student.
     */
    void setposToMove(int pos);

    /**
     * This method sets the position.
     * @param pos The position.
     */
    void setpos(int pos);

    /**
     * This method sets the colour.
     * @param colour The colour.
     */
    void setcolour(int colour);

    /**
     * This method sets the nickname.
     * @param nickname The nickname.
     */
    void setNickname(String nickname);

    /**
     * This method sets the movement of mother nature.
     * @param mnmovement The movement of mother nature.
     */
    void setMnmovement(int mnmovement);

    /**
     * This method sets the game mode.
     * @param gameMode The game mode.
     */
    void setGameMode(GameMode gameMode);

    /**
     * This method sets the start of the game.
     * @param gameStarted {@code true} to start the game.
     */
    void setGameStarted(boolean gameStarted);

    /**
     * This method sets the number of players for the next game.
     * @param numberOfPlayersForNextGame The number of players for the next game.
     */
    void setNumberOfPlayersForNextGame(int numberOfPlayersForNextGame);

    /**
     * This method starts the timer.
     */
    void startTimer();

    /**
     * This method sets the chosen assistant card to be played.
     * @param assistantCardChosen The chosen assistant card to be played.
     */
    void setAssistantCardChosen(int assistantCardChosen);

    /**
     * This method sets the chosen cloud.
     * @param cloudChosen The chosen cloud.
     */
    void setCloudChosen(int cloudChosen);

    /**
     * This method returns the island where to move the chosen student.
     * @return  The island where to move the chosen student.
     */
    int getIslandToMove();

    /**
     * This method returns the movement of mother nature.
     * @return The movement of mother nature.
     */
    int getMnmovement();

    /**
     * This method returns the chosen assistant card to be played.
     * @return The chosen assistant card to be played.
     */
    int getAssistantCardChosen();

    /**
     * This method returns the chosen student to be moved.
     * @return The chosen student to be moved.
     */
    int getStudToMove();

    /**
     * This method returns the position where to move the chosen student.
     * @return The position where to move the chosen student.
     */
    int getPos();

    /**
     * This method returns the chosen cloud.
     * @return The chosen cloud.
     */
    int getCloudChosen();

    /**
     * This method sets if the player wants to play a card.
     * @param useChCard The parameter used to see if the player wants to play a card.
     */
    void setUseCharacterCard(int useChCard);

    /**
     * This method returns value that is used to see if the player wants to play a card.
     * @return A value that is used to see if the player wants to play a card.
     */
    int getUseCharacterCard();

    /**
     * This method sets the card chosen to be played by the player.
     * @param chCardUsed The card chosen to be played by the player.
     */
    void setChCardUsed(int chCardUsed);

    /**
     * This method returns the card chosen to be played by the player.
     * @return The card chosen to be played by the player.
     */
    int getChCardUsed();

    /**
     * This method checks if it can be used.
     * @return {@code True} if it can be used, {@code False} if it can't.
     */
    boolean getCanBeUsed();

    /**
     * This method sets if the character card can be used.
     * @param canBeUsed {@code True} if it can be used, {@code False} if it can't.
     */
    void setCanBeUsed(boolean canBeUsed);

    /**
     * This method returns the game controller.
     * @return The game controller.
     */
    GameController getGameController();

    /**
     * This method returns the student chosen from the monk card.
     * @return The student chosen from the monk card.
     */
    int getMonkStudent();

    /**
     * This method sets the student on the monk card.
     * @param monkStudent The student on the monk card.
     */
    void setMonkStudent(int monkStudent);

    /**
     * This method sets the waiting in the lobby.
     * @param waitingInTheLobby The waiting in the lobby.
     */
    void setWaitingInTheLobby(boolean waitingInTheLobby);

    /**
     * This method sets the display of the dining room.
     * @param displayDiningRoom The display of the dining room.
     */
    void setDisplayDiningRoom(boolean displayDiningRoom);

    /**
     * This method sets the display of island info.
     * @param displayIslandInfo The display of island info.
     */
    void setDisplayIslandInfo(boolean displayIslandInfo);

    /**
     * This method sets the display of the previously chosen student.
     * @param displayStudentChosenPreviously The previously chosen student.
     */
    void setDisplayStudentChosenPreviously(boolean displayStudentChosenPreviously);

    /**
     * This method notifies the results.
     * @param resultNotify The results.
     */
    void setResultNotify(boolean resultNotify);

    /**
     * This method sets if the massage has expired.
     * @param timeoutExpired the expired message.
     */
    void setTimeoutExpired(boolean timeoutExpired);

    /**
     * This method sets if there are not enough coins.
     * @param notEnoughCoin The coins checker.
     */
    void setNotEnoughCoin(boolean notEnoughCoin);

    /**
     * This method sets the island chosen after the herald character card.
     * @param heraldIsland The island chosen after the herald character card.
     */
    void setHeraldIsland(int heraldIsland);

    /**
     * This method returns the chosen island after the herald character card.
     * @return The chosen island after the herald character card.
     */
    int getHeraldIsland();
}
