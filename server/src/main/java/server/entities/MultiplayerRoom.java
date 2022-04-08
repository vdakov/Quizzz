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
    private final HashMap<String, Integer> playerAddedPoints;
    private final HashMap<String, Integer> timeLeft;
    
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
        for (int i = 0; i < 20; i++) {
            HashMap<String, Integer> hashMap = new HashMap<>();
            playerTime.add(hashMap);
        }
        this.playerAddedPoints = new HashMap<>();
        this.timeLeft = new HashMap<>();
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
        for (int i = 0; i < 20; i++) {
            playerTime.get(i).put(username, 10000);
        }
        playerAddedPoints.put(username, 0);
        timeLeft.put(username, 0);
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
        for (int i = 0; i < 20; i++) {
            playerTime.get(i).remove(username);
        }
        playerAddedPoints.remove(username);
        timeLeft.remove(username);
    }

    /**
     * Calculates the added point of the specific question using time left
     * @param   username the username of the player who earned added points
     * @return the new added point value
     */
    public int calculateAddedPoints(String username, boolean partialPoint) {
        int newAddedPoints = 0;
        if ( getAddedPoints(username) == 2 ) {
            if (partialPoint) {
                newAddedPoints = (int) (2 * ((this.getTimeLeft(username) / 100) / 2));
            } else {
                newAddedPoints = (int) (2 * (this.getTimeLeft(username) / 100));
            }
        } else {
            if (partialPoint) {
                newAddedPoints = (int) (1 * ((this.getTimeLeft(username) / 100) / 2));
            } else {
                newAddedPoints = (int) (1 * (this.getTimeLeft(username) / 100));
            }
        }
        playerAddedPoints.put(username, newAddedPoints);
        return newAddedPoints;
    }

    /**
     * Updates the score of the player
     *
     * @param username the username of the player that won points
     */
    public void updatePlayerScore(String username) {
        playerScores.put(username, getPlayerScore(username) + this.getAddedPoints(username));
    }

    /**
     * get the time for a specific player for specific question
     * @param username   the username of the player
     * @param number
     * @return
     */
    public Integer getTimeClient(String username, int number) {
        System.out.println("Getting time for " + username + " at question number: " + number);
        System.out.println("The time is: " + playerTime.get(number - 1).get(username));
        return playerTime.get(number - 1).get(username);
    }

    /**
     * Returns the score of a player
     *
     * @param username the username of the player
     * @return the current score of the player
     */
    public Integer getPlayerScore(String username) {
        System.out.println(playerScores.toString());

        return playerScores.get(username);
    }

    /**
     * Sets the score for the player to the given score
     * @param username the username of the player
     * @param playerScore the new score of the player
     */
    public void setPlayerScores(String username, Integer playerScore) {
        playerScores.put(username, playerScore);
    }

    /**
     * Get the time left after the user input the answer
     * @param username the username of the player
     * @return time left in milliseconds
     */
    public Integer getTimeLeft(String username) {
        return this.timeLeft.get(username);
    }

    /**
     * Sets the time left after the user input the answer
     * @param username the username of the player
     * @param timeLeft time left in milliseconds
     */
    public void setTimeLeft(String username, int timeLeft) {
        this.timeLeft.put(username, timeLeft);
    }

    /**
     * Get the amount of points that user earned in that question
     * @param username the username of the player
     * @return the point earned in that question
     */
    public int getAddedPoints(String username) { return playerAddedPoints.get(username); }

    /**
     * Set the amount of point that user earned in that question
     * @param username the username of the player
     * @param addedPoint the point earned in that question
     */
    public void setAddedPoints(String username, int addedPoint) { this.playerAddedPoints.put(username, addedPoint); }

    /**
     * Get the number of player in this multiplayer room
     * @return the number of players
     */
    public int getNumPlayers() {
        return playerScores.size();
    }

    /**
     * Checks whether the user used the hint joker before
     * @param username the username of the player
     * @return true if Hint Joker was used this game
     */
    public Boolean getHintJokerUsed(String username) { return playerHintJokerUsed.get(username); }

    /**
     * Checks whether the user used the double point joker before
     * @param username the username of the player
     * @return true if DoublePoint Joker was used this game
     */
    public Boolean getDoublePointJokerUsed(String username) { return playerDoublePointJokerUsed.get(username); }

    /**
     * Checks whether the user used the time joker before
     * @param username the username of the player
     * @return true if time Joker was used this game
     */
    public Boolean getTimeJokerUsed(String username) { return playerTimeJokerUsed.get(username); }

    /**
     * Uses the hint joker, so the boolean value hint joker is marked as true
     * @param username the username of the player
     */
    public void useHintJoker(String username) { playerHintJokerUsed.put(username, true); }

    /**
     * Uses the double point joker, so the boolean value double point joker is marked as true,
     * and the added point is set as 2, which will be used at calculateAddedPoint to determine whether double point joker is used in this question
     * @param username the username of the player
     */
    public void useDoublePointJoker(String username) {
        playerAddedPoints.put(username, 2);
        playerDoublePointJokerUsed.put(username, true);
    }

    /**
     * Uses the time joker, so the boolean value hint joker is marked as true
     * @param username the username of the player
     * @param number
     */
    public void useTimeJoker(String username, int number) {
        playerTimeJokerUsed.put(username, true);

        if (number >= 20) { return; }

        HashMap<String, Integer> question = playerTime.get(number);
        for (String key : question.keySet()) {
            if (!key.equals(username)) {
                System.out.println("Username limited: " + username + " in question: " + (number + 1)) ;
                question.replace(key, 5000);
            }
        }
    }

}
