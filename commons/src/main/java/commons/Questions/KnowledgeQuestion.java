package commons.Questions;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class KnowledgeQuestion extends Question {

    private final List<String> options;

    /**
     * Constructor for instantiating a question with the question statement and the
     * possible answers
     *
     * @param question the String representing the question statement
     * @param options  the List of possible answers for the question
     */
    public KnowledgeQuestion(Pair<String, String> question, List<String> options) {
        super(question);
        this.options = options;
    }

    /**
     * Getter for the possible answers to the question
     *
     * @return the list of possible answers to the question
     */
    public List<String> getOptions() {
        return options;
    }

    /**
     * Generates a human-friendly String of the current instance of the object
     *
     * @return the question statement in a human-friendly way
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Knowledge question statement: ").append(super.toString()).append("\n");
        for (int i = 0; i < options.size(); i++) {
            sb.append("   Choice ").append(i).append(": ").append(options.get(i)).append("\n");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }



}
