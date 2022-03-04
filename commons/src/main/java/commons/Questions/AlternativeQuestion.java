package commons.Questions;

public class AlternativeQuestion extends Question {

    private String question;
    private String firstOption;
    private String secondOption;
    private String thirdOption;

    public AlternativeQuestion(String question, String firstOption, String secondOption, String thirdOption) {
        this.question = question;
        this.firstOption = firstOption;
        this.secondOption = secondOption;
        this.thirdOption = thirdOption;
    }
}
