package server.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerRoomTest {

//    @Test
//    void calculateAddedPointsTest() {
//        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);
//        s.setAddedPoint(1);
//        s.setTimeLeft(500);
//        assertEquals((int) (1 * 500 / 100), s.calculateAddedPoints());
//    }

    @Test
    void resetAddedPointTest() {
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);

        assertEquals(1, s.getAddedPoints());
    }


}