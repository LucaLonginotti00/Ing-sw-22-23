package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.message.toServer.*;

/**
 * This class represent the board in the CLI.
 */
public class BoardCLI {

    /**
     * Constructor of the class.
     * @param client The client.
     * @param board The board.
     */
    public static void studentToMoveRequest(Client client, Board board) {
        boolean validChoice = true;
        int choiceStudent;
        System.out.println("choose the number of one of the students to move:\n");
        displayEntranceStudents(board);
        do {
            choiceStudent = InputParser.getInt();
            if (choiceStudent < board.getMaxEntranceStudents()) {
                validChoice = false;
            } else {
                System.out.println("Number not valid,please choose a number from the list");
            }
        } while (validChoice);

        client.sendMessageToServer(new StudentToMoveReply(choiceStudent));

    }


    /**
     * This method requests the position to move the students.
     * @param client The client.
     * @param board The board.
     * @param choiceStudent The student chosen.
     */
    public static void positionToMoveRequest(Client client, Board board, int choiceStudent) {
        boolean validChoice = true;
        int choicePosition;


        if (board.getArrEntranceStudents()[choiceStudent].getIsChosen() == false) {
            System.out.println("where do you want to move the student?:\n");
            System.out.println("Dining Room[0] Island[1]");
            do {
                choicePosition = InputParser.getInt();
                if (choicePosition < 2) {
                    validChoice = false;
                } else {
                    System.out.println("Number not valid,please ");
                }

            } while (validChoice);
            client.sendMessageToServer(new PositionToMoveReply(choicePosition));

        }
    }

    /**
     * This method displays the dining room.
     * @param client The client.
     * @param board The board.
     * @param choiceStudent The students.
     */
    public static void displayDiningRoomColourFull(Client client, Board board, int choiceStudent) {
        System.out.println("table of colour:"+board.getArrEntranceStudents()[choiceStudent].getEnumColour() +" is full, please choose another student");
        client.sendMessageToServer(new DisplayDiningRoomColourFullReply());
    }

    /**
     * This method displays the students chosen previously.
     * @param client The client.
     * @param board The board.
     * @param choiceStudent The students.
     */
    public static void displayStudentChosenPreviously(Client client, Board board, int choiceStudent) {
        System.out.println("Student chosen previously,please choose another student\n");

        client.sendMessageToServer(new DisplayStudentChosenPreviouslyReply());
    }

    /**
     * This method displays the entrance of students.
     * @param board The board.
     */
    public static void displayEntranceStudents( Board board) {
        for (int i = 0; i < board.getMaxEntranceStudents() - 1; i++) {
            System.out.println(board.getArrEntranceStudents()[i].getEnumColour() + "[" + (i) + "] ");
        }
        System.out.println(board.getArrEntranceStudents()[board.getMaxEntranceStudents() - 1].getEnumColour() + "[" + (board.getMaxEntranceStudents() - 1) + "]\n");
    }
}