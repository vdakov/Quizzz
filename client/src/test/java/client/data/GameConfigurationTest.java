package client.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigurationTest {

    private final GameConfiguration gameConfiguration = new GameConfiguration();

    @Test
    void getConsecutiveUnansweredQuestions() {
        assertEquals(0, gameConfiguration.getConsecutiveUnansweredQuestions());
    }

    @Test
    void setConsecutiveUnansweredQuestions() {
        gameConfiguration.setConsecutiveUnansweredQuestions(2);
        assertEquals(2, gameConfiguration.getConsecutiveUnansweredQuestions());
    }


    @Test
    void getUserName() {
        assertEquals(null, gameConfiguration.getUserName());
    }

    @Test
    void setUserName() {
        gameConfiguration.setUserName("delia");
        assertEquals("delia", gameConfiguration.getUserName());
    }

    @Test
    void getRoomId() {
        assertEquals(null, gameConfiguration.getRoomId());
    }

    @Test
    void setRoomId() {
        gameConfiguration.setRoomId("22");
        assertEquals("22", gameConfiguration.getRoomId());
        ;
    }

    @Test
    void getCurrentQuestionNumber() {
        assertEquals(-1, gameConfiguration.getCurrentQuestionNumber());
    }

    @Test
    void setCurrentQuestionNumber() {
        gameConfiguration.setCurrentQuestionNumber(2);
        assertEquals(2, gameConfiguration.getCurrentQuestionNumber());
    }

    @Test
    void isDefined() {
        assertEquals(false, gameConfiguration.isDefined());
    }

    @Test
    void isSinglePlayer() {
        assertEquals(false, gameConfiguration.isSinglePlayer());
    }

    @Test
    void isMultiPlayer() {
        assertEquals(false, gameConfiguration.isMultiPlayer());
    }

    @Test
    void setGameTypeUndefined() {
        gameConfiguration.setGameTypeUndefined();
        assertEquals("UNDEFINED", gameConfiguration.getGameTypeString());
    }

    @Test
    void setGameTypeSingleplayer() {
        gameConfiguration.setGameTypeSingleplayer();
        assertEquals("SINGLEPLAYER", gameConfiguration.getGameTypeString());
    }

    @Test
    void setGameTypeMultiPlayer() {
        gameConfiguration.setGameTypeMultiPlayer();
        assertEquals("MULTIPLAYER", gameConfiguration.getGameTypeString());
    }

    @Test
    void getGameTypeString() {
        assertEquals("UNDEFINED", gameConfiguration.getGameTypeString());
    }

    @Test
    void setScore() {
        gameConfiguration.setScore(500);
        assertEquals(500, gameConfiguration.getScore());
    }

    @Test
    void getScore() {
        assertEquals(0, gameConfiguration.getScore());
    }

    @Test
    void isHintJokerUsed() {
        assertEquals(false, gameConfiguration.isHintJokerUsed());
    }

    @Test
    void setHintJokerUsed() {
        gameConfiguration.setHintJokerUsed(true);
        assertEquals(true, gameConfiguration.isHintJokerUsed());
    }

    @Test
    void isDoublePointJokerUsed() {
        assertEquals(false, gameConfiguration.isDoublePointJokerUsed());
    }

    @Test
    void setDoublePointJokerUsed() {
        gameConfiguration.setDoublePointJokerUsed(true);
        assertEquals(true, gameConfiguration.isDoublePointJokerUsed());

    }

    @Test
    void isTimeJokerUsed() {
        assertEquals(false, gameConfiguration.isTimeJokerUsed());
    }

    @Test
    void setTimeJokerUsed() {
        gameConfiguration.setTimeJokerUsed(false);
        assertEquals(false, gameConfiguration.isTimeJokerUsed());
    }

    @Test
    void getTimeLeft() {
        assertEquals(0, gameConfiguration.getTimeLeft());
    }

    @Test
    void setTimeLeft() {
        gameConfiguration.setTimeLeft(8);
        assertEquals(8, gameConfiguration.getTimeLeft());
    }
}