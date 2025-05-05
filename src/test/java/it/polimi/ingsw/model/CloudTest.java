package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the class {@link it.polimi.ingsw.model.Cloud}.
 */
class CloudTest {

    @Test
    public void testCloud1(){
        Cloud cloud = new Cloud(1);
        cloud.setArr_students(0);
        cloud.setArr_students(3);
        cloud.setArr_students(4);

        assertArrayEquals(new int[]{1,0,0,1,1}, cloud.getArr_students());
    }

    @Test
    public void testCloud2(){
        Cloud cloud = new Cloud(1);

        assertEquals(1, cloud.getCloud_ID());
    }
}