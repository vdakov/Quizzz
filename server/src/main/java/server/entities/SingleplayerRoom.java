package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

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
        this.addedPoints = 0;
        this.timeLeft = 0;
    }

    /**
     * Calculates the added point of the specific question based on time left
     * @param partialPoint for the opened question, there is a partial point for some range of answer
     * @return  If the user used double point joker, the added point is set as 2 and else it is set as 0.
     *          Now it checks whether the user earns partial point or the full point. Then, it returns the calculated added points
     */
    public int calculateAddedPoints(boolean partialPoint) {
        if ( this.addedPoints == 2 ) {
            if (partialPoint) {
                this.addedPoints = (int) (2 * (this.timeLeft) / 100) / 2;
            } else {
                this.addedPoints = (int) (2 * (this.timeLeft) / 100);
            }
        } else {
            if (partialPoint) {
                this.addedPoints = (int) (1 * (this.timeLeft) / 100) / 2;
            } else {
                this.addedPoints = (int) (1 * (this.timeLeft) / 100);
            }
        }
        return addedPoints;
    }

    /**
     * Updates the score by adding added point to the player's current total score
     */
    public void updatePlayerScore() {
        this.playerScore += this.addedPoints;
    }

    /**
     * Returns the current score of the player
     * @return the score of the player in the room
     */
    public int getPlayerScore() {
        return this.playerScore;
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
     * Get the time left after the user input the answer
     * @return time left in milliseconds
     */
    public Integer getTimeLeft() {
        return this.timeLeft;
    }

    /**
     * Sets the time left after the user input the answer
     * @param timeLeft time left in milliseconds
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Get the amount of points that user earned in that question
     * @return the point earned in that question
     */
    public int getAddedPoints() { return this.addedPoints; }

    /**
     * Set the amount of point that user earned in that question
     * @param addedPoint the point earned in that question
     */
    public void setAddedPoint(int addedPoint) { this.addedPoints = addedPoint; }

    /**
     * Checks whether the user used the hint joker before
     * @return true if Hint Joker was used this game
     */
    public Boolean getHintJokerUsed() { return hintJokerUsed; }

    /**
     * Uses the hint joker, so the boolean value hint joker is marked as true
     */
    public void useHintJoker() { hintJokerUsed = true; }


    /**
     * Checks whether the user used the DoublePoint joker before
     * @return true if DoublePoint Joker was used this game
     */
    public boolean getDoublePointJokerUsed() { return doublePointJokerUsed; }

    /**
     * Uses the double point joker, so the boolean value double point joker is marked as true,
     * and the added point is set as 2, which will be used at calculateAddedPoint to determine whether double point joker is used in this question
     */
    public void useDoublePointJoker() {
        this.addedPoints = 2;
        doublePointJokerUsed = true;
    }
}
