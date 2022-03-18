package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class MultiPlayerGame extends Game {

    private enum GameStatus {
        WAITING,
        ONGOING
    }

    private GameStatus gameStatus;
    private HashMap<String, Integer> playerScores;

    public MultiPlayerGame(String gameId, String roomCreator, List<Pair<Question, String>> gameQuestionsWithAnswers) {
        super(gameId, roomCreator, gameQuestionsWithAnswers);
        this.playerScores = new HashMap<>();

        this.gameStatus = GameStatus.WAITING;
    }

    public void setGameStatusOngoing() {
        gameStatus = GameStatus.ONGOING;
    }

    public void addUser(String user) {
        playerScores.put(user, 0);
    }

    public int getPlayerScore(String userName) {
        return playerScores.get(userName);
    }

    public void updatePlayerScore(String userName, int addedScore) {
        playerScores.put(userName, getPlayerScore(userName) + addedScore);
    }
}
