package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class MultiplayerRoom extends Room {

    private final HashMap<String, Integer> playerScores;
    private final HashMap<String, Boolean> playerHintJokerUsed;
    private final HashMap<String, Boolean> playerDoublePointJokerUsed;
    private final HashMap<String, Boolean> playerTimeJokerUsed;
    private final HashMap<String, Integer> playerAddedPoints;

    /**
     * Constructor for a multiplayer room
     *
     * @param roomId the id of the new game room
     * @param roomCreator the creator of the new room
     * @param roomQuestionsWithAnswers  the questions for the current room
     */
    public MultiplayerRoom(String roomId, String roomCreator, List<Pair<Question, String>> roomQuestionsWithAnswers) {
        super(roomId, roomCreator, roomQuestionsWithAnswers);

        this.playerScores = new HashMap<>();
        this.playerHintJokerUsed = new HashMap<>();
        this.playerDoublePointJokerUsed = new HashMap<>();
        this.playerTimeJokerUsed = new HashMap<>();
        this.playerAddedPoints = new HashMap<>();
    }

    /**
     * Adds a user to the score leaderboard
     *
     * @param username the username of the player that joins the room
     */
    public void addPlayer(String username) {
        playerScores.put(username, 0);
        playerHintJokerUsed.put(username, false);
        playerDoublePointJokerUsed.put(username, false);
        playerTimeJokerUsed.put(username, false);
        playerAddedPoints.put(username, 10);
    }

    /**
     * Removes a user from the score leaderboard
     *
     * @param username the username of the player that leaves the room
     */
    public void removePlayer(String username) {
        playerScores.remove(username);
        playerHintJokerUsed.remove(username);
        playerDoublePointJokerUsed.remove(username);
        playerTimeJokerUsed.remove(username);
        playerAddedPoints.remove(username);
    }

    /**
     * Returns the score of a player
     *
     * @param username the username of the player
     * @return the score of th
     */
    public Integer getPlayerScore(String username) {
        System.out.println(playerScores.toString());

        return playerScores.get(username);
    }

    public Boolean getHintJokerUsed(String username) { return playerHintJokerUsed.get(username); }
    public Boolean getDoublePointJokerUsed(String username) { return playerDoublePointJokerUsed.get(username); }
    public Boolean getTimeJokerUsed(String username) { return playerTimeJokerUsed.get(username); }

    public void useHintJoker(String username) { playerHintJokerUsed.put(username, true); }
    public void useDoublePointJoker(String username) {
        playerAddedPoints.put(username, this.getAddedPoints(username) * 2);
        playerDoublePointJokerUsed.put(username, true);
    }
    public void useTimeJoker(String username) { playerTimeJokerUsed.put(username, true); }

    public int getAddedPoints(String username) { return playerAddedPoints.get(username); }

    /**
     * Calculates the added point of the specific question using time left
     * @param username
     * @param timeLeft the amount of time left to user, fast answer gives more points
     * @return the new added point value
     */
    public int calculateAddedPoints(String username, long timeLeft) {
        int newAddedPoints = (int) (getAddedPoints(username) * (timeLeft));
        playerAddedPoints.put(username, newAddedPoints);
        return newAddedPoints;
    }

    /**
     * Resets the points added as 10, to prevent the points getting doubled everytime after the double point joker is used
     */
    public void resetAddedPointAfterDoublePointJoker(String username) {
        playerAddedPoints.put(username, 10);
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

    public int getNumPlayers() {
        return playerScores.size();
    }
}
