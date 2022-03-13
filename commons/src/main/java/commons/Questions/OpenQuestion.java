package commons.Questions;

import org.apache.commons.lang3.tuple.Pair;

public class OpenQuestion extends Question {

    /**
     * Instantiating the question statement for an open question
     *
     * @param question the String representing the question statement
     */
    public OpenQuestion(Pair<String, String> question) {
        super(question);
    }
}
