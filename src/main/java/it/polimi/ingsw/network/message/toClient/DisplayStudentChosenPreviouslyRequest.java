package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.view.ViewInterface;


/**
 * Message to request the previously chosen students.
 */
public class DisplayStudentChosenPreviouslyRequest extends MessagesToClient {
    Board board;
    int choiceStudent;
    public DisplayStudentChosenPreviouslyRequest(Board board, int choiceStudent){
        super(true);
        this.board=board;
        this.choiceStudent=choiceStudent;
    }
    @Override
    public void handleMessage(ViewInterface view) {
        view.displayStudentChosenPreviously(board,choiceStudent);
    }

    @Override
    public String toString(){
        return "Asking to display the previously chosen students";
    }

}
