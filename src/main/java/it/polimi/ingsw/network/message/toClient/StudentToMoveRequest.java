package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to ask the student to be moved.
 */
public class StudentToMoveRequest extends MessagesToClient{
    Board board;
    public StudentToMoveRequest(Board board){
        super(true);
        this.board=board;
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayStudentToMoveRequest(board);
    }

    @Override
    public String toString(){
        return "Asking which student to move";
    }
}
