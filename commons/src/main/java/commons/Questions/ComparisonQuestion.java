package commons.Questions;

public class ComparisonQuestion extends Question {

    private String firstChoice;
    private String secondChoice;
    private String thirdChoice;

    public ComparisonQuestion(String firstChoice, String secondChoice, String thirdChoice) {
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
        this.thirdChoice = thirdChoice;
    }
}
