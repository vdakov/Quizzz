package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;
import commons.Actions.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Locale;


public class QuestionActivityCtrl {
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private Action question;
    private int pointsInt;
    private int pointsGainedInt;
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button exitButton;
    @FXML
    private Button answerLeft, answerRight, answerCenter;
    @FXML
    private Label points;
    @FXML
    private Label pointsGained;
    @FXML
    private String correctAnswer;
    @FXML
    private Label showCorrectAnswer;


    //Constructor for the Question Controller
    @Inject
    public QuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {

        //resets the colors to white each time
        getAnswerCenter().setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000;");
        getAnswerLeft().setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000");
        getAnswerRight().setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000");


        //hardcoded activity- have to eventually make it initialize through a database- Not now tho :)
        this.question = server.getRandomAction();

        //transforms the activity into a question
        this.sampleQuestion.setText("How much does electricity(in kWH) does " +
                question.getTitle().substring(0, 1).toLowerCase(Locale.ROOT) + question.getTitle().substring(1) + " take?");

        int answer = question.getConsumption();
        long range = Math.round(Math.random() * answer);
        long range2 = Math.round(Math.random() * answer);
        double random = Math.random() * 3;


        //this is a very dumb way to do this, but I am too lazy to implement it another way
        // at this point so too bad :)
        if (random > 2) {
            answerCenter.setText(String.valueOf(answer));
            answerRight.setText(String.valueOf(range));
            answerLeft.setText(String.valueOf(range2));
        } else if (random < 2 && random > 1) {
            answerCenter.setText(String.valueOf(range));
            answerRight.setText(String.valueOf(range2));
            answerLeft.setText(String.valueOf(answer));
        } else {
            answerCenter.setText(String.valueOf(range));
            answerRight.setText(String.valueOf(answer));
            answerLeft.setText(String.valueOf(range2));
        }
        this.correctAnswer = "" + answer;
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) {
        Button current = (Button) event.getSource();

        pointsGainedInt = 0;
        if (current.getText().equals(getCorrectAnswer())) {
            pointsGainedInt = 500;
        }

        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(answerCenter.getText(), this.getAnswerCenter());
        answerCheck(answerLeft.getText(), this.getAnswerLeft());
        answerCheck(answerRight.getText(), this.getAnswerRight());

        //changes the points value
        pointsGained.setText( "+" +String.valueOf(pointsGainedInt));


        //sends the server a delete request to ensure the same activity does not appear twice
        server.deleteActivity(question.getId());


    }

    //Event for when the "NEXT" button is pressed on the question screen
    public void next(ActionEvent event) {
        //changes the points value
        pointsInt += pointsGainedInt;
        points.setText(String.valueOf("Current Points: " + pointsInt));
        pointsGained.setText("");

        this.initialize();
    }

    //Event when exit button is pressed
    public void exit(ActionEvent event) {
        Stage current = (Stage) ((Button) event.getSource()).getScene().getWindow();
        current.close();
    }

    //Method that checks whether answer is correct
    public void answerCheck(String answer, Button current) {
        if (answer.equals(getCorrectAnswer())) {
            current.setText("CORRECT");
            current.setStyle("-fx-background-color: #00FF00; "); //simple CSS for clarity
        } else {
            current.setText("FALSE");
            current.setStyle("-fx-background-color: #d20716; ");
        }
    }

    //Getters and setters
    public Button getAnswerLeft() {
        return answerLeft;
    }

    public Button getAnswerRight() {
        return answerRight;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }


}
