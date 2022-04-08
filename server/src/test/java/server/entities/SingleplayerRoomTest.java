package server.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerRoomTest {

    private SingleplayerRoom s = new SingleplayerRoom("1", "1", null);

    @Test
    void calculateAddedPointsTestNoPointJokerNoPartialPoint() {
        s.setTimeLeft(10000);
        assertEquals((int) (1 * 10000 / 100), s.calculateAddedPoints(false));
    }

    @Test
    void calculateAddedPointsTestUsePointJokerUsePartialPoint() {
        s.useDoublePointJoker();
        s.setTimeLeft(10000);
        assertEquals((int) ((2 * 10000 / 100) / 2), s.calculateAddedPoints(true));
    }

    @Test
    void testUpdatePlayerScore() {
        s.setPlayerScore(100);
        s.setTimeLeft(10000);
        s.calculateAddedPoints(false);
        s.updatePlayerScore();
        assertEquals(100 + (int) (1 * 10000 / 100), s.getPlayerScore());
    }




}