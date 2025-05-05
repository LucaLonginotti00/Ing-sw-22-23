package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the class {@link it.polimi.ingsw.model.Board}.
 */
class BoardTest {
    //Game with two players
    Board board = new Board(2, 1, TowerColour.STARTER);
    //Game with three players
    Board board3 = new Board(3,1, TowerColour.STARTER);


    private Student[] array = new Student[7];

    @Test
    void testAdd_profTrue() {
        boolean[] arrProfessors = board.getArrProfessors();
        DiskColour color = DiskColour.RED;
        board.add_prof(color);
        assertTrue(arrProfessors[DiskColour.RED.getTranslateColour()]);
    }

    @Test
    void testAdd_studentTrue() {
        int[] arrPositionStudents = board.getArrPositionStudents();
        DiskColour color = DiskColour.RED;
        arrPositionStudents[DiskColour.RED.getTranslateColour()] = 2;
        board.add_student(color);
        assertEquals(3, arrPositionStudents[DiskColour.RED.getTranslateColour()]);
    }

    @Test
    void testCoinsEarned() {
        int[]arrPositionStudents = {3,6,9,3,3};
        boolean[][] track_coins = {{true,false,false},
                {false,false,false},
                {false,false,false},
                {false,false,false},{false,false,false}};


        board.setTrackCoins(track_coins);
        assertArrayEquals(track_coins,board.getTrackCoins());

        board.setArrPositionStudents(arrPositionStudents);

        assertEquals(4,board.coinsEarned());

    }

    @Test
    void testN_towers() {
        board.setN_towers(6);
        assertEquals(6,board.getN_towers());
    }

    @Test
    void testArrProfessors() {
        boolean[] arr1 = {true, true};
        boolean[] arr2 = {true, true};
        board.setArrProfessors(arr1);
        assertArrayEquals(arr2, board.getArrProfessors());
    }

    @Test
    void testArrEntranceStudents() {
        array[0] = new Student(DiskColour.RED);
        array[1] = new Student(DiskColour.BLUE);
        for (int i = 0; i < 7; i++) {
            array[0] = new Student(DiskColour.RED);
        }
        for (int i = 0; i < 7; i++) {
            board.setArrEntranceStudents(array[i],i);
        }

        assertArrayEquals(array,board.getArrEntranceStudents());
    }

    @Test
    void testSetTower() {
        board.setTower(4);
        assertEquals(4,board.getTower());
    }

}


