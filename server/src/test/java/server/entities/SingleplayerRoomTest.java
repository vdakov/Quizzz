package server.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerRoomTest {

    private SingleplayerRoom s = new SingleplayerRoom("1", "1", null);

    @Test
    void testTimeLeft() {
        s.setTimeLeft(10000);
        assertEquals(10000, s.getTimeLeft());
    }

    @Test
    void calculateAddedPointsTestNoPointJokerNoPartialPoint() {
        s.setTimeLeft(10000);
        assertEquals((int) (1 * 10000 / 100), s.calculateAddedPoints(false));
    }

    @Test
    void calculateAddedPointsTestUsePointJokerUsePartialPoint() {
        s.useDoublePointJoker();
        assertEquals(true, s.getDoublePointJokerUsed());
        s.setTimeLeft(10000);
        assertEquals((int) ((2 * 10000 / 100) / 2), s.calculateAddedPoints(true));
    }

    @Test
    void calculateAddedPointsTestNoPointJokerUsePartialPoint() {
        s.setTimeLeft(10000);
        assertEquals((int) ((1 * 10000 / 100) / 2), s.calculateAddedPoints(true));
    }

    @Test
    void calculateAddedPointsTestUsePointJokerNoPartialPoint() {
        s.useDoublePointJoker();
        s.setTimeLeft(10000);
        assertEquals(10000, s.getTimeLeft());
        assertEquals((int) (2 * 10000 / 100), s.calculateAddedPoints(false));
    }

    @Test
    void testUpdatePlayerScore() {
        s.setPlayerScore(100);
        assertEquals(100, s.getPlayerScore());
        s.setAddedPoint(100);
        assertEquals(100, s.getAddedPoints());
        s.updatePlayerScore();
        assertEquals(100 + 100, s.getPlayerScore());
    }

    @Test
    void testHintJoker() {
        assertEquals(false, s.getHintJokerUsed());
        s.useHintJoker();
        assertEquals(true, s.getHintJokerUsed());
    }



    @Test
    void resetAddedPointTest() {
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);

        assertEquals(0 , s.getAddedPoints());
    }


}