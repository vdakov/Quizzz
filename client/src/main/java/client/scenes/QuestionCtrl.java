package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import commons.Activity;


public class QuestionCtrl {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Activity question;

    @Inject
    public QuestionCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void initialize() {
        this.question = new Activity("Riding your electric scooter to university (10km)",
                "https://dx.doi.org/10.1016/j.apenergy.2013.10.043", 330);


        this.sampleQuestion.setText("How much does electricity(in kWH does " + question.getTitle() + " take?");
        int answer = question.getConsumption();
        long range = Math.round(Math.random() * answer);
        long range2 = Math.round(Math.random() * answer);
        double random = Math.random() * 3;


        //this is dumb. too bad :)
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
        this.correctAnswer = String.valueOf(answer);
    }


    private int pointsInt;
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button exitButton;
    @FXML
    private Button answerLeft, answerRight, answerCenter;
    @FXML
    private Label points;
    @FXML
    private String correctAnswer;

    public void answer(ActionEvent event) {
        Button current = (Button) event.getSource();

        answerCheck(answerCenter.getText(), this.getAnswerCenter());
        answerCheck(answerLeft.getText(), this.getAnswerLeft());
        answerCheck(answerRight.getText(), this.getAnswerRight());

        if (current.getText().equals(correctAnswer)) {
            pointsInt += 500;
        }
        points.setText(String.valueOf(pointsInt));


    }

    public void exit(ActionEvent event) {
        Stage current = (Stage) ((Button) event.getSource()).getScene().getWindow();
        current.close();
    }

    public void mainMenu(ActionEvent event) {
        mainCtrl.showQuotesOverview();
    }

    public void answerCheck(String answer, Button current) {
        if (answer.equals(getCorrectAnswer())) {
            current.setText("CORRECT");
            current.setStyle("-fx-background-color: #00FF00; ");
        } else {
            current.setText("FALSE");
            current.setStyle("-fx-background-color: #d20716; ");
        }
    }

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
