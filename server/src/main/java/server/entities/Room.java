package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Room {

    private final String                       roomId;
    private final String                       roomCreator;
    private final List<Pair<Question, String>> roomQuestionsWithAnswers;

    /**
     * Constructor for a new game room
     *
     * @param roomId                    the id of the new game room
     * @param roomCreator               the creator of the new room
     * @param roomQuestionsWithAnswers  the questions for the current room
     */
    public Room(String roomId, String roomCreator, List<Pair<Question, String>> roomQuestionsWithAnswers) {
        this.roomId = roomId;
        this.roomCreator = roomCreator;
        this.roomQuestionsWithAnswers = roomQuestionsWithAnswers;
    }

    /**
     * Returns the id of the current instance of the room
     *
     * @return a String representing the id
     */
    public String getGameId() {
        return this.roomId;
    }

    /**
     * Returns the name of the user that created the room
     *
     * @return the string representing the user that created the room
     */
    public String getRoomCreator() {
        return roomCreator;
    }

    /**
     * Returns the n th question or throws an error
     *
     * @param  number the number of the question we want
     * @return the Question at the desired number
     * @throws IllegalArgumentException when the question list with answers size is smaller than the parameter
     */
    public Question getQuestion(int number) throws IllegalArgumentException {
        if (this.roomQuestionsWithAnswers.size() < number) {
            throw new IllegalArgumentException("The wanted size is too big: getQuestion");
        }

        return this.roomQuestionsWithAnswers.get(number).getKey();
    }

    /**
     * Returns the n th answer or throws an error
     *
     * @param  number the number of the answer we want
     * @return the answer at the desired question number
     * @throws IllegalArgumentException when the question list with answers size is smaller than the parameter
     */
    public String getQuestionAnswer(int number) throws IllegalArgumentException {
        if (this.roomQuestionsWithAnswers.size() < number) {
            throw new IllegalArgumentException("The wanted size is too big: getAnswer");
        }

        return this.roomQuestionsWithAnswers.get(number).getValue();
    }
}
