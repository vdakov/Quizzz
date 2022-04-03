package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiplayerRoom extends Room {

    private final HashMap<String, Integer> playerScores;
    private final HashMap<String, Boolean> playerHintJokerUsed;
    private final HashMap<String, Boolean> playerDoublePointJokerUsed;
    private final HashMap<String, Boolean> playerTimeJokerUsed;
    private final List<HashMap<String, Integer>> playerTime;

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
        this.playerTime = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            HashMap<String, Integer> hashMap = new HashMap<>();
            playerTime.add(hashMap);
        }
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
        for(int i = 0; i < 20; i++){
            playerTime.get(i).put(username, 10);
        }

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
        for(int i = 0; i < 20; i++){
            playerTime.get(i).remove(username);
        }
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

    // get the time for a spwcific player for specific question
    public Integer getTimeClient(String username, int number) { return playerTime.get(number).get(username); }

    public Boolean getHintJokerUsed(String username) { return playerHintJokerUsed.get(username); }
    public Boolean getDoublePointJokerUsed(String username) { return playerDoublePointJokerUsed.get(username); }
    public Boolean getTimeJokerUsed(String username) { return playerTimeJokerUsed.get(username); }

    public void useHintJoker(String username) { playerHintJokerUsed.put(username, true); }
    public void useDoublePointJoker(String username) { playerDoublePointJokerUsed.put(username, true); }
    public void useTimeJoker(String username) { playerTimeJokerUsed.put(username, true); }

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
