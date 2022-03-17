package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import client.logic.QuestionParsers;
import com.google.inject.Inject;
import commons.Questions.ComparisonQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Scanner;

public class ComparisonQuestionActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private int questionNumber;
    private ComparisonQuestion comparisonQuestion;
    private String userName;
    private String serverId;
    private int pointsInt;
    private int addedPointsInt;
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button goToMainScreen;
    @FXML
    private Button answerLeft, answerCenter, answerRight;
    @FXML
    private Label points;
    @FXML
    private Label addedPoints;
    @FXML
    private Label questionNumberLabel;
    //Constructor for the Question Controller
    @Inject
    public ComparisonQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void setQuestion(ComparisonQuestion comparisonQuestion) {
        this.comparisonQuestion = comparisonQuestion;

        this.sampleQuestion.setText((comparisonQuestion == null) ? "" : comparisonQuestion.getQuestion().getKey());
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {
        points.setText(String.valueOf(pointsInt));
        //resets the colors to white each time
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) {
        Button current = (Button) event.getSource();
        System.out.println("Comparison question am intrat");

        addedPointsInt=0;
        if (current.getText().equals("10")) {
            addedPointsInt = 500; //global variable for points so it remembers it
        }

        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(answerCenter.getText(), this.getAnswerCenter());
        answerCheck(answerLeft.getText(), this.getAnswerTop());
        answerCheck(answerRight.getText(), this.getAnswerBottom());

        //changes the points value
        addedPoints.setText("+"+String.valueOf(addedPointsInt));

//        goToNextQuestion();
        //sends the server a delete request to ensure the same activity does not appear twice

    }
    public void goNext(ActionEvent event) throws IOException {
        pointsInt += addedPointsInt;
        goToNextQuestion();
    }

    //Method that checks whether answer is correct
    public void answerCheck(String answer, Button current) {


        long mTime = System.currentTimeMillis();
        long end = mTime + 1000;

        // This should be setting the colour and then go to a new question screen but it doesn't work right now
        do {
            if (answer.equals("10")) {
                current.setStyle("-fx-background-color: #00FF00; "); //simple CSS for clarity
            } else {
                current.setStyle("-fx-background-color: #d20716; ");
            }
        } while (System.currentTimeMillis() < end);

        this.initialize();

    }

    public void goToNextQuestion() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        gameConfiguration.setCurrentQuestionNumber(gameConfiguration.getCurrentQuestionNumber() + 1);
        String response = server.getQuestion();
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        switch (scanner.next()) {
            case "OpenQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionGuessXScene(QuestionParsers.openQuestionParser(scanner.next()));
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionHowMuchScene(QuestionParsers.knowledgeQuestionParser(scanner.next()));
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionWhatIsScene(QuestionParsers.comparisonQuestionParser(scanner.next()));
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionInsteadOfScene(QuestionParsers.alternativeQuestionParser(scanner.next()));
                break;
            }
        }
    }

    public void goToMainScreen (ActionEvent event) throws IOException {

    }


    //Getters and setters
    public Button getAnswerTop() { return answerLeft; }

    public Button getAnswerBottom() {
        return answerRight;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }

}
