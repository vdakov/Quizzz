package commons.Questions;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class AlternativeQuestion extends Question {

    private List<Pair<String, String>> options;

    /**
     * Constructor for instantiating a question with the question statement and the
     * possible answers
     *
     * @param question the String representing the question statement
     * @param options the List of possible answers for the question
     */
    public AlternativeQuestion(Pair<String, String> question, List<Pair<String, String>> options) {
        super(question);
        this.options = options;
    }

    /**
     * Getter for the possible answers to the question
     *
     * @return the list of possible answers to the question
     */
    public List<Pair<String, String>> getOptions() {
        return options;
    }

    /**
     * Generates a human-friendly String of the current instance of the object
     *
     * @return the question statement in a human-friendly way
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alternative question statement: ").append(super.toString()).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append("   Choice ").append(i).append(": ").append(options.get(i).getKey()).append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public String toJsonString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toJsonString());
        for (Pair<String, String> option : options) {
            sb.append("; ").append(option.getKey()).append("; ").append(option.getValue());
        }
        return sb.toString();
    }
}
