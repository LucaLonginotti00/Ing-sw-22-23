package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to choose if move student to board or island.
 */
public class ChooseIslandOrBoardRequest extends MessagesToClient{
    Board board;
    int choiceStudent;

    public ChooseIslandOrBoardRequest(Board board, int choiceStudent) {
        super(true);
        this.board=board;
        this.choiceStudent=choiceStudent;
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.displayWhereToMoveStudents(board,choiceStudent);
    }

    @Override
    public String toString(){
        return "Asking where to move the students";
    }
}
