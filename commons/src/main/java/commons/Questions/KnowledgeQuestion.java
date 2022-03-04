package commons.Questions;

public class KnowledgeQuestion extends Question {

    private String question;
    private String firstOption;
    private String secondOption;
    private String thirdOption;

    public KnowledgeQuestion(String question, String firstOption, String secondOption, String thirdOption) {
        this.question = question;
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.thirdOption = thirdOption;
    }

}
