package commons.Questions;

import org.apache.commons.lang3.tuple.Pair;

public abstract class Question {


    private final Pair<String, String> question;

    /**
     * Constructor for instantiating a question with the question statement
     *
     * @param question the String representing the question statement
     */
    public Question(Pair<String, String> question) {
        this.question = question;
    }

    /**
     * Getter for the question statement
     *
     * @return the question statement
     */
    public Pair<String, String> getQuestion() {
        return this.question;
    }

    /**
     * Generates a human-friendly String of the current instance of the object
     *
     * @return the question statement in a human-friendly way
     */
    public String toString() {
        return this.question.getKey();
    }

    public String toJsonString() {
        return (question.getKey() + ", " + question.getValue());
    }
}
