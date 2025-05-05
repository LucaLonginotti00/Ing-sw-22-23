package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.view.ViewInterface;
import java.util.List;

/**
 * Message to choose an island where to move students.
 */
public class ChooseIslandRequest extends MessagesToClient{
    List<Island> islands;
    Student stud_to_island;
    public ChooseIslandRequest(List<Island> islands, Student stud_to_island){
        super(true);
        this.islands=islands;
        this.stud_to_island=stud_to_island;
    }
    @Override
    public void handleMessage(ViewInterface view) {
        view.MoveStudentToIslandRequest(islands,stud_to_island);
    }

    @Override
    public String toString(){
        return "Asking to which island move the students";
    }
}
