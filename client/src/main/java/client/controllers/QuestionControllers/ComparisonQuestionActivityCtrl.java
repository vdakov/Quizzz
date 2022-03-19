package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import com.google.inject.Inject;
import commons.Questions.ComparisonQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class ComparisonQuestionActivityCtrl {

    // constructor needed variables
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    private int pointsInt;
    private int addedPointsInt;
    private String userAnswer;

    // final needed labels for questions
    @FXML
    private Label labelGoBack;

    @FXML
    private Label questionStatement;

    @FXML
    private Label firstOptionText;
    @FXML
    private ImageView firstOptionImage;

    @FXML
    private Label secondOptionText;
    @FXML
    private ImageView secondOptionImage;

    @FXML
    private Label thirdOptionText;
    @FXML
    private ImageView thirdOptionImage;

    @FXML
    private String correctAnswer;


    @FXML
    private Button goToMainScreen;
    @FXML
    private Label points;
    @FXML
    private Label addedPoints;
    @FXML
    private Label questionNumberLabel;

    @FXML
    private Rectangle firstOptionRectangle;
    @FXML
    private Rectangle secondOptionRectangle;
    @FXML
    private Rectangle thirdOptionRectangle;

    //Constructor for the Question Controller


    /**
     * Creates the scene with the needed dependencies
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public ComparisonQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    /**
     * Initialises all the colors for the current scene
     */
    public void initialize() {

        firstOptionRectangle.setStroke(Color.valueOf("#b38df7"));
        secondOptionRectangle.setStroke(Color.valueOf("#ffd783"));
        thirdOptionRectangle.setStroke(Color.valueOf("#ffa382"));

        addedPoints.setText(" ");
        addedPointsInt = 0;
    }

    /**
     * Sets the text for the needed question given as parameter
     * @param comparisonQuestion the question that is set
     */
    public void displayQuestion(ComparisonQuestion comparisonQuestion) {
        if (comparisonQuestion == null) {
            return;
        }

        getQuestionStatement().setText(comparisonQuestion.getQuestion().getKey());

        getQuestionFirstOption() .setText(comparisonQuestion.getOptions().get(0).getKey());
        getQuestionSecondOption().setText(comparisonQuestion.getOptions().get(1).getKey());
        getQuestionThirdOption() .setText(comparisonQuestion.getOptions().get(2).getKey());

        questionNumberLabel.setText("Question " + getQuestionNumber());
        points.setText(String.valueOf(getPointsInt()));
        initialize();
    }

    /**
     * Displays the next question to the user after the transition is finished
     */
    public void displayNextQuestion() {
        sceneCtrl.showNextQuestion();
    }

    public void answerQuestion(MouseEvent event) {
        // answers the question and blocks the possibility to answer anymore
        Label current = (Label) event.getSource();
        userAnswer = current.getText();

        answerUpdate();
        pointsUpdate();
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown
        firstOptionRectangle.setStroke(Color.valueOf("#ff0000"));
        secondOptionRectangle.setStroke(Color.valueOf("#ff0000"));
        thirdOptionRectangle.setStroke(Color.valueOf("#ff0000"));
        if (getCorrectAnswer().equals(firstOptionText.getText())) {
            firstOptionRectangle.setStroke(Color.valueOf("#92d36e"));
        } else if (getCorrectAnswer().equals(secondOptionText.getText())) {
            secondOptionRectangle.setStroke(Color.valueOf("#92d36e"));
        } else {
            thirdOptionRectangle.setStroke(Color.valueOf("#92d36e"));
        }
    }

    public void pointsUpdate() {
        // after the time ends the amount of won points is calculated and then shown to the player
        addedPointsInt = 0;
        if (userAnswer.equals(getCorrectAnswer())) {
            addedPointsInt = 500;
        }
        addedPoints.setText("+" + String.valueOf(addedPointsInt));
    }

    public void goToMainScreen () throws IOException {
        sceneCtrl.showMainScreen();
    }

    private Label getQuestionStatement() {
        return questionStatement;
    }

    private Label getQuestionFirstOption() {
        return firstOptionText;
    }

    private Label getQuestionSecondOption() {
        return secondOptionText;
    }

    private Label getQuestionThirdOption() {
        return thirdOptionText;
    }

    public String getCorrectAnswer() {
        return server.getAnswer();
    }

    public int getPointsInt() {
//        return server.getScore();
        return Integer.parseInt(server.getScore());
    }

    public int getQuestionNumber() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        return gameConfiguration.getCurrentQuestionNumber();
    }
}
