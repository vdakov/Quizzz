package commons.Questions;

public abstract class Question {

    private final String question;

    /**
     * Constructor for instantiating a question with the question statement
     *
     * @param question the String representing the question statement
     */
    public Question(String question) {
        this.question = question;
    }

    /**
     * Getter for the question statement
     *
     * @return the question statement
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Generates a human-friendly String of the current instance of the object
     *
     * @return the question statement in a human-friendly way
     */
    public String toString() {
        return this.question;
    }
}
