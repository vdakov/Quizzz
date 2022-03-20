package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import com.google.inject.Inject;
import commons.Questions.KnowledgeQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;


public class KnowledgeQuestionActivityCtrl {

    // constructor needed variables
    private final ServerUtils server;
    private final SceneCtrl   sceneCtrl;

    private int pointsInt;
    private int addedPointsInt;
    private String userAnswer;

    // final needed labels for questions
    @FXML
    private Label labelGoBack;

    @FXML
    private Label questionStatement;
    @FXML
    private ImageView questionStatementImage;

    @FXML
    private Label firstOptionText;
    @FXML
    private Label secondOptionText;
    @FXML
    private Label thirdOptionText;

    // current labels
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button goToMainScreen;
    @FXML
    private Button answerTop, answerBottom, answerCenter;
    @FXML
    private Label points;
    @FXML
    private Label addedPoints;
    @FXML
    private Label questionNumberLabel;
    @FXML
    private Label labelAnswerCenter;
    @FXML
    private Label labelAnswerTop;
    @FXML
    private Label labelAnswerBottom;
    @FXML
    private String correctAnswer;

    @FXML
    private Rectangle firstOptionRectangle;
    @FXML
    private Rectangle secondOptionRectangle;
    @FXML
    private Rectangle thirdOptionRectangle;

    /**
     * Creates the scene with the needed dependencies
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public KnowledgeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
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
     * @param knowledgeQuestion the question that is set
     */
    public void displayQuestion(KnowledgeQuestion knowledgeQuestion) {
        if (knowledgeQuestion == null) {
            return;
        }

        getQuestionStatement().setText(knowledgeQuestion.getQuestion().getKey());

        getQuestionFirstOption() .setText(knowledgeQuestion.getOptions().get(0));
        getQuestionSecondOption().setText(knowledgeQuestion.getOptions().get(1));
        getQuestionThirdOption() .setText(knowledgeQuestion.getOptions().get(2));

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

        server.updateScore(userAnswer);

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

////        FadeTransition fadeout = new FadeTransition(Duration.seconds(1), addedPoints);
////        fadeout.setFromValue(1);
////        fadeout.setToValue(0);
////        fadeout.play();
//
//        //after some effect
//        pointsInt += addedPointsInt;
//        addedPointsInt = 0;
//        addedPoints.setText(null);
//        points.setText(String.valueOf(pointsInt));
    }

    public void goToMainScreen () throws IOException {
        sceneCtrl.showMainScreen();

    }

    private Label getQuestionStatement() {
        return sampleQuestion;
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

