package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.AlternativeQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AlternativeQuestionActivityCtrl {

    // constructor needed variables
    private final ServerUtils server;
    private final SceneCtrl   sceneCtrl;

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
    private ImageView firstOptionImage;

    @FXML
    private Label secondOptionText;
    @FXML
    private ImageView secondOptionImage;

    @FXML
    private Label thirdOptionText;
    @FXML
    private ImageView thirdOptionImage;

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
    private Label labelAnswerCenter;
    @FXML
    private Label labelAnswerTop;
    @FXML
    private Label labelAnswerBottom;
    @FXML
    private String correctAnswer;


    /**
     * Creates the scene with the needed dependencies
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public AlternativeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    /**
     * Initialises all the colors for the current scene
     */
    public void initialize() {
    }

    /**
     * Sets the text for the needed question given as parameter
     * @param alternativeQuestion the question that is set
     */
    public void displayQuestion(AlternativeQuestion alternativeQuestion) {
        if (alternativeQuestion == null) {
            return;
        }

        getQuestionStatement().setText(alternativeQuestion.getQuestion().getKey());


        getQuestionFirstOption() .setText(alternativeQuestion.getOptions().get(0).getKey());
        getQuestionSecondOption().setText(alternativeQuestion.getOptions().get(1).getKey());
        getQuestionThirdOption() .setText(alternativeQuestion.getOptions().get(2).getKey());

        initialize();
    }

    /**
     * Displays the next question to the user after the transition is finished
     */
    public void displayNextQuestion() {
        sceneCtrl.showNextQuestion();
    }

    public void answerQuestion() {
        // answers the question and blocks the possibility to answer anymore
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown
    }

    public void pointsUpdate() {
        // after the time ends the amount of won points is calculated and then shown to the player
    }

    public void goToMainScreen() throws IOException {
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


    //Getters and setters
    public Button getAnswerTop() { return answerTop; }

    public Button getAnswerBottom() {
        return answerBottom;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }
}
