package server.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerRoomTest {

    @Test
    void calculateAddedPointsTestNoPointJokerNoPartialPoint() {
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);
        s.setAddedPoint(0);
        s.setTimeLeft(10000);
        assertEquals((int) (1 * 10000 / 100), s.calculateAddedPoints(false));
    }

    @Test
    void calculateAddedPointsTestUsePointJokerUsePartialPoint() {
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);
        s.useDoublePointJoker();
        s.setTimeLeft(10000);
        assertEquals((int) ((2 * 10000 / 100) / 2), s.calculateAddedPoints(true));
    }

    @Test
    void resetAddedPointTest() {
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);
        assertEquals(0, s.getAddedPoints());
    }


}