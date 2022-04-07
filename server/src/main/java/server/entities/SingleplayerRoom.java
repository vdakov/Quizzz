package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;

public class SingleplayerRoom extends Room {

    private int playerScore;
    private boolean hintJokerUsed;
    private boolean doublePointJokerUsed;
    private int addedPoints;
    private int timeLeft;

    /**
     * Constructor for a singleplayer room
     *
     * @param roomId                    the id of the new game room
     * @param roomCreator               the creator of the new room
     * @param roomQuestionsWithAnswers  the questions for the current room
     */
    public SingleplayerRoom(String roomId, String roomCreator, List<Pair<Question, String>> roomQuestionsWithAnswers) {
        super(roomId, roomCreator, roomQuestionsWithAnswers);
        this.playerScore = 0;
        hintJokerUsed = false;
        doublePointJokerUsed = false;
        this.addedPoints = 10;
        this.timeLeft = 0;
    }

    /**
     * Returns the current score of the player
     * @return the score of the player in the room
     */
    public int getPlayerScore() {
        return this.playerScore;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Integer getTimeLeft() {
        return this.timeLeft;
    }

    public int getAddedPoints() { return this.addedPoints; }

    /**
     * Calculates the added point of the specific question using time left
     * @param timeLeft the amount of time left to user, fast answer gives more points
     * @return the new added point value
     */
    public int calculateAddedPoints(int timeLeft) {
        this.addedPoints = (int) (addedPoints * (timeLeft));
        return addedPoints;
    }

    /**
     * Resets the points added as 10, to prevent the points getting doubled everytime after the double point joker is used
     */
    public void resetAddedPointAfterDoublePointJoker() {
        addedPoints = 10;
    }

    /**
     * Returns true if Hint Joker was used this game
     */
    public Boolean getHintJokerUsed() { return hintJokerUsed; }

    /**
     * Records hint joker used
     */
    public void useHintJoker() { hintJokerUsed = true; }

    /**
     * Returns true if DoublePoint Joker was used this game
     */
    public boolean getDoublePointJokerUsed() { return doublePointJokerUsed; }

    /**
     * Records doublePoint joker used
     */
    public void useDoublePointJoker() {
        this.addedPoints = addedPoints * 2;
        doublePointJokerUsed = true;
    }




    /**
     * Sets the score for the player to the given score
     *
     * @param playerScore the new score of the player
     */
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    /**
     * Adds to the score of the current player the given score
     *
     * @param additionalPoints the number of points the player will add
     */
    public void updatePlayerScore(int additionalPoints) {
        this.playerScore += additionalPoints;
    }
}
