package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the class {@link it.polimi.ingsw.model.Student}.
 */
class StudentTest {

    @Test
    public void testStudentsColor1(){
        Student student = new Student(DiskColour.YELLOW);

        assertEquals(0, student.getColour());
    }

    @Test
    public void testStudentsTrue(){
        Student student = new Student(DiskColour.RED);
        student.Chosen();

        assertTrue(student.getIsChosen());
    }

    @Test
    public void testStudentsColor2(){
        Student student = new Student(DiskColour.GREEN);

        assertSame(DiskColour.GREEN, student.getEnumColour());
    }
}