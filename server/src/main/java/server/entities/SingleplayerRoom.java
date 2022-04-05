package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class SingleplayerRoom extends Room {

    private int playerScore;
    private boolean hintJokerUsed;
    private boolean doublePointJokerUsed;
    private int addedPoints;

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
    }

    /**
     * Returns the current score of the player
     *
     * @return the score of the player in the room
     */
    public int getPlayerScore() {
        return this.playerScore;
    }

    public int getAddedPoints() { return this.addedPoints; }

    public int calculateAddedPoints(long timeLeft) {
        this.addedPoints = (int) (addedPoints * (timeLeft));
        return addedPoints;
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


    public void resetAddedPointAfterDoublePointJoker() {
        addedPoints = 10;
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
