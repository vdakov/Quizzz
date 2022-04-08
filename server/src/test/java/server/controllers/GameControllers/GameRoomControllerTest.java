package server.controllers.GameControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import server.services.GameServices.MultiplayerGameService;
import server.services.GameServices.SingleplayerGameService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameRoomControllerTest {

    @Autowired
    private GameRoomController gameRoomController;

    @MockBean
    private SingleplayerGameService singlePlayerGameService;
    private MultiplayerGameService multiplayerGameService;

    @BeforeEach
    void setUp() {
//        multiplayerGameService = new MultiplayerGameService();

    }

    @Test
    void useDoublePointJoker() {
    }

    @Test
    void startNewGame() {
    }

    @Test
    void getQuestion() {
    }

    @Test
    void getCorrectAnswer() {
    }

    @Test
    void getScore() {
    }

    @Test
    void getTimeClient() {
    }

    @Test
    void postAnswer() {
    }

    @Test
    void useHintJoker() {
    }

    @Test
    void testUseDoublePointJoker() {
    }

    @Test
    void resetDoubledAddedPoints() {
    }

    @Test
    void useTimeJoker() {
    }

    @Test
    void getAddedPoints() {
    }

    @Test
    void calculateAddedPoints() {
    }

    @Test
    void getHintJokerUsed() {
    }

    @Test
    void getDoublePointJokerUsed() {
    }

    @Test
    void getTimeJokerUsed() {
    }

    @Test
    void getTimeLeft() {
    }

    @Test
    void setTimeLeft() {
    }
}