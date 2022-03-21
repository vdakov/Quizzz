package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameContainerTest {

    private final GameContainer testContainer = new GameContainer("12", 15);

    @Test
    void getGameId() {
        assertEquals(testContainer.getGameId(), "12");
    }

    @Test
    void getNumPlayers() {
        assertEquals(testContainer.getNumPlayers(), 15);
    }

    @Test
    void setGameId() {
        testContainer.setGameId("42hj3kjhk432h");
        assertEquals(testContainer.getGameId(), "42hj3kjhk432h");
    }

    @Test
    void setNumPlayers() {
        testContainer.setNumPlayers(17);
        assertEquals(testContainer.getNumPlayers(), 17);
    }
}