package client.data;

// uses the singleton design pattern to store the data globally
public class GameConfiguration {

    private static GameConfiguration gameConfiguration = null;

    public enum GameType {
        UNDEFINED,
        SINGLEPLAYER,
        MULTIPLAYER
    }

    private GameType gameType;
    private String questionType;
    private String userName;
    private String roomId;
    private int currentQuestionNumber;
    private int score;
    private boolean hintJokerUsed;
    private boolean doublePointJokerUsed;
    private boolean timeJokerUsed;
    private long timeLeft;

    private int consecutiveUnansweredQuestions;

    private boolean hasConnected;

    public GameConfiguration() {
        this.userName = null;
        this.roomId = null;
        this.currentQuestionNumber = -1;
        this.gameType = GameType.UNDEFINED;
        this.score = 0;
        this.hintJokerUsed = false;
        this.doublePointJokerUsed = false;
        this.timeJokerUsed = false;

        this.consecutiveUnansweredQuestions = 0;
        hasConnected  = false;
    }

    public boolean getConnected() {
        return hasConnected;
    }

    public void connect() {
        hasConnected = true;
    }

    public int getConsecutiveUnansweredQuestions() {
        return consecutiveUnansweredQuestions;
    }

    public void setConsecutiveUnansweredQuestions(int consecutiveUnansweredQuestions) {
        this.consecutiveUnansweredQuestions = consecutiveUnansweredQuestions;
    }

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

    public boolean isDefined() {
        return (this.gameType != GameType.UNDEFINED);
    }

    public boolean isSinglePlayer() {
        return (isDefined() && this.gameType == GameType.SINGLEPLAYER);
    }

    public boolean isMultiPlayer() {
        return (isDefined() && this.gameType == GameType.MULTIPLAYER);
    }

    public void setGameTypeUndefined() {
        this.gameType = GameType.UNDEFINED;
    }

    public void setGameTypeSingleplayer() {
        this.gameType = GameType.SINGLEPLAYER;
    }

    public void setGameTypeMultiPlayer() {
        this.gameType = GameType.MULTIPLAYER;
    }

    public String getGameTypeString() {
        return gameType.toString();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Boolean isHintJokerUsed() {
        return hintJokerUsed;
    }

    public void setHintJokerUsed(boolean hintJokerUsed) {
        this.hintJokerUsed = hintJokerUsed;
    }

    public Boolean isDoublePointJokerUsed() {
        return doublePointJokerUsed;
    }

    public void setDoublePointJokerUsed(boolean doublePointJokerUsed) {
        this.doublePointJokerUsed = doublePointJokerUsed;
    }

    public Boolean isTimeJokerUsed() {
        return timeJokerUsed;
    }

    public void setTimeJokerUsed(boolean timeJokerUsed) {
        this.timeJokerUsed = timeJokerUsed;
    }

    public long getTimeLeft() { return timeLeft; }

    public void setTimeLeft(long timeLeft) { this.timeLeft = timeLeft; }

    public String getQuestionType() { return questionType; }

    public void setQuestionType(String questionType) { this.questionType = questionType; }
}
