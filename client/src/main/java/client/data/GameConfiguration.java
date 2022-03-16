package client.data;

import commons.Questions.Question;

// uses the singleton design pattern to store the data globally
public class GameConfiguration {

    private static GameConfiguration gameConfiguration = null;

    private String userName;
    private String roomId;
    private int currentQuestionNumber;
    private Question currentQuestion;

    public GameConfiguration() {
        this.userName              = null;
        this.roomId                = null;
        this.currentQuestionNumber = 0;
        this.currentQuestion = null;
    }

    public static GameConfiguration getConfiguration() {
        if (gameConfiguration == null) {
            gameConfiguration = new GameConfiguration();
        }
        return gameConfiguration;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public void setCurrentQuestionNumber(int currentQuestionNumber) {
        this.currentQuestionNumber = currentQuestionNumber;
    }
}
