package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import java.util.List;

/**
 * This interface contains the methods to handle the request sent to/by the client.
 */
public interface ViewInterface {

    /**
     * This method handle the closing of connections.
     * @param was_connected Boolean to check if there was a connection.
     */
    void handleCloseConnection(boolean was_connected);

    /**
     * This method notifies that the time to send a response is over.
     */
    void displayTimeoutCloseConnection();

    /**
     * This method asks the name.
     * @param is_retry {@code True} if it's not the first time that the nickname is asked.
     */
    void displayNicknameRequest(boolean is_retry);

    /**
     * This method displays the request of the game mode.
     */
    void displayGameModeRequest();

    /**
     * This method asks the desired number of players for the game.
     */
    void displayNumberOfPlayersRequest();

    /**
     * This method asks the desired Student from the monk.
     */
    void showStudent(Student[] students);

    /**
     * This method asks the desired island where to calculate influence.
     */
    void heraldIsland(List<Island> islands);

    /**
     * This method notifies the waiting before starting the game.
     */
    void displayWaitingMessage();

    /**
     * This method displays a message.
     * @param message The message.
     */
    void displayMessage(String message);

    /**
     * This method displays the request of the mother nature movement.
     * @param Mn_pos The position of mother nature.
     * @param Current_Player The current player.
     */
    void motherNatureMovementRequest(int Mn_pos, Player Current_Player);

    /**
     * This method displays the request of the students to move.
     * @param board The board.
     */
    void displayStudentToMoveRequest(Board board);

    /**
     * This method displays the request of the choice for students movement.
     * @param board The board.
     * @param choiceStudent The student.
     */
    void displayWhereToMoveStudents(Board board, int choiceStudent);

    /**
     * This method request in which island the student must be moved.
     * @param islands The list of islands.
     * @param stud_to_island The student to the island.
     */
    void MoveStudentToIslandRequest(List<Island> islands, Student stud_to_island);

    /**
     * This method displays the request of the choice for the assistant card.
     * @param player The player.
     * @param GT The game table.
     */
    void displayChooseAssistantCardRequest(Player player, GameTable GT);

    /**
     * This method displays the request of the choice for a cloud.
     * @param clouds The list of clouds.
     */
    void displayChooseCloudRequest(List<Cloud> clouds);

    /**
     * This method displays the Island info.
     * @param island The island.
     * @param islandID The ID of the island.
     */
    void displayIslandInfo(Island island, int islandID);

    /**
     * This method displays the dining Room table of the colour "x" is full message.
     * @param board The board.
     * @param choiceStudent The student.
     */
    void displayDiningRoomColourFull( Board board,int choiceStudent);

    /**
     * This method displays the student chosen previously message.
     * @param board The board.
     * @param choiceStudent The student.
     */
    void displayStudentChosenPreviously( Board board,int choiceStudent);

    /**
     * This method displays the entrance students.
     * @param board The board.
     */
    void displayEntranceStudents(Board board);

    /**
     * This method request a position.
     * @param upperLimit The max moves available.
     */
    void choosePositionRequest(int upperLimit);

    /**
     * This method close the connection.
     */
    void closeConnection();

    /**
     * This method display a player disconnection message.
     * @param name The name.
     */
    void displayDisconnection(String name);

    /**
     * This method ask if the player wants to use a character card.
     */
    void UseCharacterCard();

    /**
     * This method displays the request the Character card that the player wants to use.
     * @param player The player.
     * @param cc The array of character cards.
     */
    void ChooseCharacterCard(Player player,CharacterCard[] cc);

    /**
     * This method shows the results of the game.
     * @param islands The list pf islands.
     * @param players The list of players.
     * @param boards The array of boards.
     */
    void displayResults( List<Island> islands, List<Player> players,Board[] boards);

    /**
     * This method displays the request of the colour of disk's student prompt to use two character cards.
     */
    void displayColourRequest();

}
