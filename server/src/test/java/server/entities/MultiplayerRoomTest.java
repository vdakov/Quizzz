package server.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MultiplayerRoomTest {

    private MultiplayerRoom m = new MultiplayerRoom("1", "1", null);

    @BeforeEach
    void setUp() {
    m.addPlayer("userName");
    }

    @Test
    void testAddAndRemovePlayer() {
        setUp();
        m.addPlayer("userName2");
        assertEquals(2, m.getNumPlayers());
        m.removePlayer("userName2");
        assertEquals(1, m.getNumPlayers());

    }

    @Test
    void testTimeLeft() {
        m.setTimeLeft("userName", 10000);
        assertEquals(10000, m.getTimeLeft("userName"));
    }

    @Test
    void calculateAddedPointsTestNoPointJokerNoPartialPoint() {
        m.setTimeLeft("userName", 10000);
        assertEquals((int) (1 * 10000 / 100), m .calculateAddedPoints("userName", false));
    }

    @Test
    void calculateAddedPointsTestUsePointJokerUsePartialPoint() {
        m.useDoublePointJoker("userName");
        assertEquals(true, m.getDoublePointJokerUsed("userName"));
        m.setTimeLeft("userName", 10000);
        assertEquals((int) ((2 * 10000 / 100) / 2), m.calculateAddedPoints("userName", true));
    }

    @Test
    void calculateAddedPointsTestNoPointJokerUsePartialPoint() {
        m.setTimeLeft("userName", 10000);
        assertEquals((int) ((1 * 10000 / 100) / 2), m.calculateAddedPoints("userName", true));
    }

    @Test
    void calculateAddedPointsTestUsePointJokerNoPartialPoint() {
        m.useDoublePointJoker("userName");
        m.setTimeLeft("userName", 10000);
        assertEquals((int) (2 * 10000 / 100), m.calculateAddedPoints("userName", false));
    }

    @Test
    void testUpdatePlayerScore() {
        m.setPlayerScores("userName", 100);
        m.setAddedPoints("userName", 100);
        assertEquals(100, m.getAddedPoints("userName"));
        m.updatePlayerScore("userName");
        assertEquals(100 + 100, m.getPlayerScore("userName"));
    }

    @Test
    void testHintJoker() {
        assertEquals(false, m.getHintJokerUsed("userName"));
        m.useHintJoker("userName");
        assertEquals(true, m.getHintJokerUsed("userName"));
    }
}