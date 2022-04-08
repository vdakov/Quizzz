package server.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerRoomTest {

    @Test
    void calculateAddedPoints() {
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);
        s.setAddedPoint(1);
        s.setTimeLeft(500);
        assertEquals((int) (1 * 500 / 100), s.calculateAddedPoints());
    }
}