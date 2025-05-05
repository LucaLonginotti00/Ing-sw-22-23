package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to display the dining room.
 */
public class DisplayDiningRoomColourFullRequest extends MessagesToClient{
    Board board;
    int choiceStudent;
    public DisplayDiningRoomColourFullRequest(Board board, int choiceStudent){
        super(true);
        this.board=board;
        this.choiceStudent=choiceStudent;

    }
    @Override
    public void handleMessage(ViewInterface view) {
        view.displayDiningRoomColourFull(board,choiceStudent);}


    @Override
    public String toString(){
        return "Asking to display the dining room";
    }
}
