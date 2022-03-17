package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import client.logic.QuestionParsers;
import com.google.inject.Inject;
import commons.Questions.OpenQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import static javax.xml.bind.DatatypeConverter.parseInt;

public class OpenQuestionActivityCtrl {


    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private OpenQuestion openQuestion;
    private int questionNumber;
    private String userName;
    private String serverId;
    private int pointsInt;
    private int addedPointsInt;
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button goToMainScreen;
    @FXML
    private Button answer;
    @FXML
    private Label points;
    @FXML
    private Label addedPoints;
    @FXML
    private Label questionNumberLabel;
    @FXML
    private TextField writeAnswer;
    @FXML
    private String correctAnswer;
    @FXML
    private Label correctAnswerLabel;


    //Constructor for the Question Controller
    @Inject
    public OpenQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void setQuestion(OpenQuestion openQuestion) {
        this.openQuestion = openQuestion;

        this.sampleQuestion.setText((openQuestion == null) ? "" : openQuestion.getQuestion().getKey());

        this.correctAnswer = "100";
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {

        points.setText(String.valueOf(pointsInt));

        getAnswer().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");

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


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) throws InterruptedException {
        Button current = (Button) event.getSource();

        addedPointsInt=0;
        if (writeAnswer.getText().equals(getCorrectAnswer())) {
            addedPointsInt = 500; //global variable for points so it remembers it
        }
        else
            if (parseInt(writeAnswer.getText()) > parseInt(getCorrectAnswer()) * 0.95 &&  parseInt(writeAnswer.getText()) < parseInt(getCorrectAnswer()) * 1.05) {
                addedPointsInt = 250;
            }


        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(writeAnswer.getText(), this.getAnswer());

        //show correct answer
        correctAnswerLabel.setText(correctAnswer);

        //changes the points value
        addedPoints.setText("+"+String.valueOf(addedPointsInt));

//        goToNextQuestion();
    }

    public void goNext(ActionEvent event) throws IOException {
        goToNextQuestion();
    }


    //Method that checks whether answer is correct
    //
    public void answerCheck(String answer, Button current) throws InterruptedException {

        if (Objects.equals(writeAnswer.getText(), getCorrectAnswer()) ||
                parseInt(writeAnswer.getText()) > parseInt(getCorrectAnswer()) * 0.95 &&  parseInt(writeAnswer.getText()) < parseInt(getCorrectAnswer()) * 1.05) {
            current.setStyle("-fx-background-color: #00FF00; ");  //simple CSS for clarity
        } else {
            current.setStyle("-fx-background-color: #d20716;");
        }


    }

    public void goToMainScreen (ActionEvent event) throws IOException {
    }



    public Button getAnswer() { return answer; }

    public String getCorrectAnswer() { return correctAnswer; }

    public TextField getWriteAnswer() { return writeAnswer; }

    public void setWriteAnswer(TextField writeAnswer) { this.writeAnswer = writeAnswer; }

}
