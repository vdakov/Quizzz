package commons.Questions;

import java.util.List;

public class KnowledgeQuestion extends Question {

    private String question;
    private List<String> options;

    public KnowledgeQuestion(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }
}
