package server.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleplayerRoomTest {

    @Test
    void calculateAddedPoints() {
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);
        assertEquals((int) (10 * 5), s.calculateAddedPoints(5));
    }
}