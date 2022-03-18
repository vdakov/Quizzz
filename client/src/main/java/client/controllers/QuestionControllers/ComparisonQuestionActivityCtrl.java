package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.ComparisonQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ComparisonQuestionActivityCtrl {

    // constructor needed variables
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

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
    private Label sampleQuestion;
    @FXML
    private Button goToMainScreen;
    @FXML
    private Button answerLeft, answerCenter, answerRight;
    @FXML
    private Label points;
    @FXML
    private Label addedPoints;
    @FXML
    private Label questionNumberLabel;
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

        initialize();
    }

    /**
     * Displays the next question to the user after the transition is finished
     */
    public void displayNextQuestion() {
        //sceneCtrl.showNextQuestion();
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

    public void goToMainScreen () throws IOException {
        //sceneCtrl.showMainScreen();
    }

    private Label getQuestionStatement() {
        return sampleQuestion;
    }

    private Label getQuestionFirstOption() {
        return null;
    }

    private Label getQuestionSecondOption() {
        return null;
    }

    private Label getQuestionThirdOption() {
        return null;
    }


    //Getters and setters
    public Button getAnswerTop() { return answerLeft; }

    public Button getAnswerBottom() {
        return answerRight;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }

}
