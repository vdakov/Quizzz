package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class MultiplayerRoom extends Room {

    public enum MultiplayerRoomStatus {
        WAITING,
        ONGOING
    }

    private MultiplayerRoomStatus multiplayerRoomStatus;
    private HashMap<String, Integer> playerScores;

    public MultiplayerRoom(String gameId, String roomCreator, List<Pair<Question, String>> gameQuestionsWithAnswers) {
        super(gameId, roomCreator, gameQuestionsWithAnswers);
        this.playerScores = new HashMap<>();

        this.multiplayerRoomStatus = MultiplayerRoomStatus.WAITING;
    }

    public MultiplayerRoomStatus getMultiplayerGameStatus() {
        return multiplayerRoomStatus;
    }

    public void setGameStatusOngoing() {
        multiplayerRoomStatus = multiplayerRoomStatus.ONGOING;
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
