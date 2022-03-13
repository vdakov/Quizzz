package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;
import commons.Actions.Action;
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
import java.util.Locale;
import java.util.Objects;

import static javax.xml.bind.DatatypeConverter.parseInt;

public class QuestionSceneGuessXActivityCtrl {


    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
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

    //Initializes the sample question screen through hardcoding
    public void initialize() {

        //resets the colors
        getAnswer().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");


        //hardcoded activity- have to eventually make it initialize through a database- Not now tho :)
        this.question = server.getRandomAction();

        //transforms the activity into a question
        this.sampleQuestion.setText("How much does electricity(in kWH) does " +
                question.getTitle().substring(0, 1).toLowerCase(Locale.ROOT) + question.getTitle().substring(1) + " take?");

        int answer = question.getConsumption();
        this.correctAnswer = "" + answer;
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) throws InterruptedException {
        Button current = (Button) event.getSource();

        if (writeAnswer.getText().equals(getCorrectAnswer())) {
            pointsInt += 500; //global variable for points so it remembers it
        }
        else
            if (parseInt(writeAnswer.getText()) > parseInt(getCorrectAnswer()) - 10 &&  parseInt(writeAnswer.getText()) < parseInt(getCorrectAnswer()) + 10) {
                pointsInt += 250;
            }


        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(writeAnswer.getText(), this.getAnswer());


        //changes the points value
        points.setText(String.valueOf(pointsInt));

        //sends the server a delete request to ensure the same activity does not appear twice
        server.deleteActivity(question.getId());


    }


    //Method that checks whether answer is correct
    //
    public void answerCheck(String answer, Button current) throws InterruptedException {

        if (writeAnswer.getText() == getCorrectAnswer()) {
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
