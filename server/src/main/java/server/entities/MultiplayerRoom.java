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

    private MultiplayerRoomStatus    multiplayerRoomStatus;
    private HashMap<String, Integer> playerScores;

    /**
     * Constructor for a multiplayer game
     *
     * @param roomId the id of the new game room
     * @param roomCreator the creator of the new room
     * @param roomQuestionsWithAnswers  the questions for the current room
     */
    public MultiplayerRoom(String roomId, String roomCreator, List<Pair<Question, String>> roomQuestionsWithAnswers) {
        super(roomId, roomCreator, roomQuestionsWithAnswers);

        this.playerScores = new HashMap<>();
        this.multiplayerRoomStatus = MultiplayerRoomStatus.WAITING;
    }

    /**
     * Returns the status of the current multiplayer game
     *
     * @return the status of the current multiplayer game
     */
    public MultiplayerRoomStatus getMultiplayerGameStatus() {
        return multiplayerRoomStatus;
    }

    /**
     * Sets the current game status as ongoing
     */
    public void setGameStatusOngoing() {
        multiplayerRoomStatus = multiplayerRoomStatus.ONGOING;
    }

    /**
     * Adds a user to the score leaderboard
     *
     * @param username the username of the player that joins the game
     */
    public void addPlayer(String username) {
        playerScores.put(username, 0);
    }

    /**
     * Removes a user from the score leaderboard
     *
     * @param username the username of the player that leaves the game
     */
    public void removePlayer(String username) {
        playerScores.remove(username);
    }

    /**
     * Returns the score of a player
     *
     * @param username the username of the player
     * @return the score of th
     */
    public Integer getPlayerScore(String username) {
        return playerScores.get(username);
    }

    /**
     * Updates the score of the player
     *
     * @param username the username of the player that won points
     * @param addedScore the score that the player has earned
     */
    public void updatePlayerScore(String username, int addedScore) {
        playerScores.put(username, getPlayerScore(username) + addedScore);
    }
}
