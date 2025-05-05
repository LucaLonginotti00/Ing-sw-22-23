package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.toServer.*;;
import it.polimi.ingsw.view.View;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * This class represents the CLI main.
 */
public class CLI implements View {
    private Client client;
    private Thread inputObserverOutOfTurn;

    public static void main(String[] args) {
        CLI cli= new CLI();
        cli.setUpGame();
    }

    /**
     * This method sets up a game.
     */
    private void setUpGame(){
        boolean error = true;
        boolean firstTry = true;
        Logo.print();
        while (error) {
            client = StartGame.InitialConnection(this, firstTry);
            try {
                client.start();
                error = false;
            } catch (IOException e) {
                firstTry = false;
            }
        }
    }

    /**
     * This method display a message.
     * @param message The message.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * This method display a nickname request.
     * @param retry {@code True} if it's not the first time that the nickname is asked.
     */
    public void displayNicknameRequest(boolean retry) {

        StartGame.displayNicknameRequest(client,retry);
    }

    /**
     * This method returns the condition on an integer range.
     * @param min The min.
     * @param max The max.
     * @return The condition on an integer range.
     */
    public static Predicate<Integer> conditionOnIntegerRange(int min, int max){
        return p -> p >= min && p <= max;
    }

    /**
     * This method displays the game mode requests.
     */
    public void displayGameModeRequest() {
        StartGame.displayGameModeRequest(client);
    }

    /**
     * This method displays the number of players requests.
     */
    public void displayNumberOfPlayersRequest(){
        StartGame.NumberOfPlayersRequest(client);
    }

    /**
     * This method displays the waiting message.
     */
    public void displayWaitingMessage(){
        StartGame.displayWaitingMessage(client);

    }

    /**
     * This method displays the movement of mother nature requests.
     * @param Mn_pos The position of mother nature.
     * @param Current_Player The current player.
     */
    public void motherNatureMovementRequest(int Mn_pos, Player Current_Player){
        MnCLI.motherNatureMovementRequest(client,Mn_pos,Current_Player);
    }

    /**
     * This method displays the island info.
     * @param island The island.
     * @param islandID The ID of the island.
     */
    public void displayIslandInfo(Island island, int islandID){
        IslandCLI.displayIslandInfo(client,island,islandID);
    }

    /**
     * This method displays move student to island requests.
     * @param islands The list of islands.
     * @param stud_to_island The student to the island.
     */
    public void MoveStudentToIslandRequest(List<Island> islands, Student stud_to_island){
        IslandCLI.MoveStudentToIslandRequest(client,islands,stud_to_island);
    }

    /**
     * This method displays the colour requests.
     */
    public void displayColourRequest(){
        ColourCLI.chooseColourRequest(client);
    }

    /**
     * This method displays the students to move requests.
     * @param board The board.
     */
    public void displayStudentToMoveRequest(Board board){
        BoardCLI.studentToMoveRequest(client,board);
    }

    /**
     * This method displays the number of players requests.
     * @param board The board.
     * @param choiceStudent The student.
     */
    public void displayWhereToMoveStudents(Board board, int choiceStudent){
        BoardCLI.positionToMoveRequest(client,board,choiceStudent);
    }

    /**
     * This method displays the dining room with colours.
     * @param board The board.
     * @param choiceStudent The student.
     */
    public void displayDiningRoomColourFull( Board board,int choiceStudent){
        BoardCLI.displayDiningRoomColourFull(client,board,choiceStudent);
    }

    /**
     * This method displays the student chosen previously.
     * @param board The board.
     * @param choiceStudent The student.
     */
    public void displayStudentChosenPreviously( Board board,int choiceStudent){
        BoardCLI.displayStudentChosenPreviously(client,board,choiceStudent);
    }

    /**
     * This method displays the entrance of students.
     * @param board The board.
     */
    public void displayEntranceStudents(Board board){
        BoardCLI.displayEntranceStudents(board);
    }

    /**
     * This method notifies that the time to send a response is over.
     */
    public void displayTimeoutCloseConnection(){
        System.out.println("Timeout expired, you will be now disconnected");
        closeConnection();
        client.sendMessageToServer(new TimeoutExpiredReply());
    }

    /**
     * This method displays the request of the choice for the assistant card.
     * @param player The player.
     * @param GT The game table.
     */
    public void displayChooseAssistantCardRequest( Player player,GameTable GT){
        AssistantCLI.chooseAssistantCard(client,player,GT);
    }

    /**
     * This method request a position.
     * @param upperLimit The max moves available.
     */
    public void choosePositionRequest(int upperLimit){
        PositionCLI.choosePositionRequest(client,upperLimit);
    }

    /**
     * This method displays the request of the choice for a cloud.
     * @param clouds The list of clouds.
     */
    public void displayChooseCloudRequest( List<Cloud> clouds){
        CloudCLI.chooseCloud(client,clouds);
    }

    /**
     * This method close the connection.
     */
    public void closeConnection(){
        client.closeSocket();
    }

    /**
     * This method handle the closing of connections.
     * @param wasConnected Boolean to check if there was a connection.
     */
    public void handleCloseConnection(boolean wasConnected) {
        displayUnreachableServer(wasConnected);
        if (inputObserverOutOfTurn != null && inputObserverOutOfTurn.isAlive())
            inputObserverOutOfTurn.interrupt();
    }

    /**
     * This method displays the message that the server is unreachable.
     * @param wasConnected Boolean to check if the client was connected.
     */
    private void displayUnreachableServer(boolean wasConnected){
        System.out.println("Game id Ended\n\n");
    }

    /**
     * This method display a player disconnection message.
     * @param name The name.
     */
    public void displayDisconnection(String name){
        System.out.println(name+ " has quit the game.\n");
        client.closeSocket();
    }


//called when the condition for the endgame are met,calculate and send the result to each player

    /**
     * This method shows the results of the game.
     * @param islands The list pf islands.
     * @param players The list of players.
     * @param boards The array of boards.
     */
    public void displayResults( List<Island> islands, List<Player> players,Board[] boards) {
        EndGameCLI.displayResults(client,islands,players,boards);
    }

    /**
     * This method ask if the player wants to use a character card.
     */
    public void UseCharacterCard(){
        int choice;
        System.out.println("Do you want to use a character card? no[0] or yes[1]\n");
        choice=InputParser.getInt();

        while(choice<0 || choice>1){
            System.out.println("Number not allowed,please choose another number");
            choice=InputParser.getInt();
        }

        client.sendMessageToServer(new UseCharacterCardReply(choice));
    }

    /**
     * This method displays the request the Character card that the player wants to use.
     * @param player The player.
     * @param cc The array of character cards.
     */
    public void ChooseCharacterCard(Player player,CharacterCard[] cc){
        int choice;
        Scanner sc=new Scanner(System.in);
        boolean poor=true;
        System.out.println("choose a character card character card from the one below: \n");
        for(int i=0;i<3;i++){
            System.out.println( "["+i+"]\n");
            System.out.println( "   ID: "+cc[i].getID_code()+"\n");
            System.out.println( "   Name: "+cc[i].getName()+"\n");
            System.out.println( "   Cost: "+cc[i].getCost()+"\n");
            if(player.getCoin()>=cc[i].getCost()){
                poor=false;
            }
        }
        if(poor==false){
            choice= sc.nextInt();

            while((choice<0 || choice>2) || player.getCoin()<cc[choice].getCost()){
                System.out.println("Number not allowed,please choose another number\n");
                choice= sc.nextInt();
            }
            client.sendMessageToServer(new ChooseCharacterCardReply(choice));
        }
        else{
            System.out.println("It seem you have not enough coin..\n");
            client.sendMessageToServer(new NotenoughCoinReply());
        }
    }

    /**
     * This method shows the students on a character card.
     * @param students The students.
     */
    public void showStudent(Student[] students){
        AssistantCLI.ShowStudent(client,students);
    }


    /**
     * This method shows the islands to choose.
     * @param islands The islands to choose.
     */
    public void heraldIsland(List<Island> islands) {
        AssistantCLI.HeraldIsland(client,islands);
    }
}
