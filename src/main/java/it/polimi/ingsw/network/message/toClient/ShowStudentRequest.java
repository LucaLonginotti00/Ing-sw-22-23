package it.polimi.ingsw.network.message.toClient;

import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.view.ViewInterface;

/**
 * Message to request the show of the student.
 */
public class ShowStudentRequest extends MessagesToClient{

    Student[] students;

    public ShowStudentRequest(Student[] students){
        super(true);
        this.students=students;
    }

    @Override
    public void handleMessage(ViewInterface view) {
        view.showStudent(students);
    }


    @Override
    public String toString(){
        return "Received ask";
    }
}
