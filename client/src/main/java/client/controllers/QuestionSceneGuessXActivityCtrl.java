package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;
import commons.Actions.Action;
import commons.Questions.OpenQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javax.xml.bind.DatatypeConverter.parseInt;

public class QuestionSceneGuessXActivityCtrl {


    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private OpenQuestion openQuestion;
    private int questionNumber;
    private Action question;
    private int pointsInt;
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button goToMainScreen;
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
    public QuestionSceneGuessXActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void setQuestion(OpenQuestion openQuestion, int questionNumber) {
        this.openQuestion = openQuestion;
        this.questionNumber = questionNumber;

        this.sampleQuestion.setText((openQuestion == null) ? "" : openQuestion.getQuestion().getKey());

        this.correctAnswer = "100";
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {
        getAnswer().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");

    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) throws InterruptedException {
        Button current = (Button) event.getSource();

        if (writeAnswer.getText().equals(getCorrectAnswer())) {
            pointsInt += 500; //global variable for points so it remembers it
        }
        else
            if (parseInt(writeAnswer.getText()) > parseInt(getCorrectAnswer()) * 0.95 &&  parseInt(writeAnswer.getText()) < parseInt(getCorrectAnswer()) * 1.05) {
                pointsInt += 250;
            }


        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(writeAnswer.getText(), this.getAnswer());


        //changes the points value
        points.setText(String.valueOf(pointsInt));
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


    private Scene scene;
    private Stage stage;
    private Parent root;

    public void goToMainScreen (ActionEvent event) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../../scenes/MainScreenScene.fxml")));
        stage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public Button getAnswer() { return answer; }

    public String getCorrectAnswer() { return correctAnswer; }

    public TextField getWriteAnswer() { return writeAnswer; }

    public void setWriteAnswer(TextField writeAnswer) { this.writeAnswer = writeAnswer; }

    public void setQuestion(Action question) { this.question = question; }


}
