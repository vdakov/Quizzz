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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class QuestionSceneInsteadOfActivityCtrl {


    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private Action question;
    private int pointsInt;
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button goToMainScreen;
    @FXML
    private Button answerTop, answerBottom, answerCenter;
    @FXML
    private Label points;
    @FXML
    private Label labelAnswerCenter;
    @FXML
    private Label labelAnswerTop;
    @FXML
    private Label labelAnswerBottom;
    @FXML
    private String correctAnswer;


    //Constructor for the Question Controller
    @Inject
    public QuestionSceneInsteadOfActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {

        //resets the colors to white each time
        getAnswerTop().setStyle("-fx-background-color: #b38df7;; -fx-border-color:  #b38df7;");
        getAnswerCenter().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");
        getAnswerBottom().setStyle("-fx-background-color: #ffa382; -fx-border-color:  #ffa382");


        //hardcoded activity- have to eventually make it initialize through a database- Not now tho :)
        this.question = server.getRandomAction();

        //transforms the activity into a question
        this.sampleQuestion.setText("How much does electricity(in kWH) does " +
                question.getTitle().substring(0, 1).toLowerCase(Locale.ROOT) + question.getTitle().substring(1) + " take?");

        long answer =  question.getConsumption();
        long range = Math.round(Math.random() * answer);
        long range2 = Math.round(Math.random() * answer);
        double random = Math.random() * 3;


        //this is a very dumb way to do this, but I am too lazy to implement it another way
        // at this point so too bad :)
        if (random > 2) {
            labelAnswerCenter.setText(String.valueOf(answer));
            labelAnswerBottom.setText(String.valueOf(range));
            labelAnswerTop.setText(String.valueOf(range2));
        } else if (random < 2 && random > 1) {
            labelAnswerCenter.setText(String.valueOf(range));
            labelAnswerBottom.setText(String.valueOf(range2));
            labelAnswerTop.setText(String.valueOf(answer));
        } else {
            labelAnswerCenter.setText(String.valueOf(range));
            labelAnswerBottom.setText(String.valueOf(answer));
            labelAnswerTop.setText(String.valueOf(range2));
        }

        this.correctAnswer = "" + answer;
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) {
        Button current = (Button) event.getSource();

        if (current.getText().equals(getCorrectAnswer())) {
            pointsInt += 500; //global variable for points so it remembers it
        }

        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(labelAnswerCenter.getText(), this.getAnswerCenter());
        answerCheck(labelAnswerTop.getText(), this.getAnswerTop());
        answerCheck(labelAnswerBottom.getText(), this.getAnswerBottom());

        //changes the points value
        points.setText(String.valueOf(pointsInt));

        //sends the server a delete request to ensure the same activity does not appear twice
        server.deleteActivity(question.getId());


    }


    //Method that checks whether answer is correct
    public void answerCheck(String answer, Button current) {


        long mTime = System.currentTimeMillis();
        long end = mTime + 1000;

        // This should be setting the colour and then go to a new question screen but it doesn't work right now
        do {
            if (answer.equals(getCorrectAnswer())) {
                current.setStyle("-fx-background-color: #00FF00; "); //simple CSS for clarity
            } else {
                current.setStyle("-fx-background-color: #d20716; ");
            }
        } while (System.currentTimeMillis() < end);

        this.initialize();

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


    //Getters and setters
    public Button getAnswerTop() { return answerTop; }

    public Button getAnswerBottom() {
        return answerBottom;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Label getLabelAnswerTop() { return labelAnswerTop; }

    public Label getLabelAnswerCenter() { return labelAnswerCenter; }

    public Label getLabelAnswerBottom() { return labelAnswerBottom; }

    public void setLabelAnswerTop(Label labelAnswerTop) { this.labelAnswerTop = labelAnswerTop; }

    public void setQuestion(Action question) { this.question = question; }

    public void setLabelAnswerCenter(Label labelAnswerCenter) { this.labelAnswerCenter = labelAnswerCenter; }

    public void setLabelAnswerBottom(Label labelAnswerBottom) { this.labelAnswerBottom = labelAnswerBottom; }
}
