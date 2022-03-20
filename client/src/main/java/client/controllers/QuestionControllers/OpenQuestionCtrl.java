package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
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

public class OpenQuestionCtrl {


    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private OpenQuestion openQuestion;
    private int questionNumber;
    private String userName;
    private String serverId;
    private int pointsInt;
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button answer;
    @FXML
    private Label points;
    @FXML
    private TextField writeAnswer;
    @FXML
    private String correctAnswer;


    //Constructor for the Question Controller
    @Inject
    public OpenQuestionCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void setQuestion(OpenQuestion openQuestion, int questionNumber, String userName, String serverId) {
        this.openQuestion = openQuestion;
        this.questionNumber = questionNumber;
        this.userName = userName;
        this.serverId = serverId;

        this.sampleQuestion.setText((openQuestion == null) ? "" : openQuestion.getQuestion().getKey());

        this.correctAnswer = "100";
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {
        getAnswer().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");
    }

    public void goToNextQuestion() {
        String response = server.getQuestion(this.userName, this.serverId, this.questionNumber + 1);
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        System.out.println("Open question am intrat");
        String next = scanner.next();
        System.out.println(next + questionNumber);
        switch (next) {
            case "OpenQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showOpenQuestionScene(QuestionParsers.openQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showKnowledgeQuestionScene(QuestionParsers.knowledgeQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showComparisonQuestionScene(QuestionParsers.comparisonQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showAlternativeQuestionScene(QuestionParsers.alternativeQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
        }
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) throws InterruptedException {
        Button current = (Button) event.getSource();

        if (writeAnswer.getText().equals(getCorrectAnswer())) {
            pointsInt += 500; //global variable for points so it remembers it
        } else if (parseInt(writeAnswer.getText()) > parseInt(getCorrectAnswer()) * 0.95 && parseInt(writeAnswer.getText()) < parseInt(getCorrectAnswer()) * 1.05) {
            pointsInt += 250;
        }


        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(writeAnswer.getText(), this.getAnswer());
        points.setText(String.valueOf(pointsInt)); //changes the points value

        if (questionNumber < 20) goToNextQuestion();
        else finishGame();
    }

    public void finishGame() {
        server.addSingleplayerLeaderboardEntry(userName, pointsInt);
        sceneCtrl.showSingleplayerLeaderboard();
    }


    //Method that checks whether answer is correct
    public void answerCheck(String answer, Button current) throws InterruptedException {
        if (Objects.equals(writeAnswer.getText(), getCorrectAnswer()) ||
                parseInt(writeAnswer.getText()) > parseInt(getCorrectAnswer()) * 0.95 && parseInt(writeAnswer.getText()) < parseInt(getCorrectAnswer()) * 1.05) {
            current.setStyle("-fx-background-color: #00FF00; ");  //simple CSS for clarity
        } else {
            current.setStyle("-fx-background-color: #d20716;");
        }


    }

    public void goToMainScreen(ActionEvent event) throws IOException {
        sceneCtrl.showMainScreenScene();
    }


    public Button getAnswer() {
        return answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public TextField getWriteAnswer() {
        return writeAnswer;
    }

    public void setWriteAnswer(TextField writeAnswer) {
        this.writeAnswer = writeAnswer;
    }

}
