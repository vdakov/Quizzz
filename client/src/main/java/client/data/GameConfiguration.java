package client.data;

// uses the singleton design pattern to store the data globally
public class GameConfiguration {

    private static GameConfiguration gameConfiguration = null;

    private String userName;
    private String roomId;
    private int currentQuestionNumber;

    public GameConfiguration() {
        this.userName              = null;
        this.roomId                = null;
        this.currentQuestionNumber = -1;
    }git a

    public static GameConfiguration getConfiguration() {
        if (gameConfiguration == null) {
            gameConfiguration = new GameConfiguration();
        }
        return gameConfiguration;
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
