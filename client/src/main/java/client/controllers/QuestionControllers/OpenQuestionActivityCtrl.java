package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.OpenQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

public class OpenQuestionActivityCtrl {

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
    private Label submitAnswer;
    @FXML
    private TextField userAnswer;

    private String userAnswerString;
    private int pointsInt;
    private int addedPointsInt;

    // current labels
    @FXML
    private Label sampleQuestion;
    @FXML
    private Button goToMainScreen;
    @FXML
    private Button answer;
    @FXML
    private Label points;
    @FXML
    private Label addedPoints;
    @FXML
    private Label questionNumberLabel;
    @FXML
    private TextField writeAnswer;
    @FXML
    private String correctAnswer;
    @FXML
    private Label correctAnswerLabel;
    @FXML
    private Rectangle correctAnswerRectangle;


    /**
     * Creates the scene with the needed dependencies
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public OpenQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    /**
     * Initialises all the colors for the current scene
     */
    public void initialize() {
        questionNumberLabel.setText("Question ?");

        getAnswer().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");
    }

    /**
     * Sets the text for the needed question given as parameter
     * @param openQuestion the question that is set
     */
    public void displayQuestion(OpenQuestion openQuestion) {
        if (openQuestion == null) {
            return;
        }
        getQuestionStatement().setText(openQuestion.getQuestion().getKey());
//        getQuestionStatement().setText("Taking a hot shower(50L water)");
    }

    /**
     * Displays the next question to the user after the transition is finished
     */
    public void displayNextQuestion() {
        sceneCtrl.showNextQuestion();
    }

    public void answerQuestion(ActionEvent event) {
        Button current = (Button)event.getSource();
        // answers the question and blocks the possibility to answer anymore
        if(userAnswer.getText() == (null)){
            userAnswer.setText("You didn't put any answer");
        }
        userAnswerString = userAnswer.getText();
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown
        correctAnswerRectangle.setFill(Color.valueOf("#c9f1fd"));
    }

    public void pointsUpdate() {
        // after the time ends the amount of won points is calculated and then shown to the player
        addedPointsInt = 0;
        if(Integer.parseInt(userAnswerString)==(getCorrectAnswerInt())){
            addedPointsInt = 500;
        } else if (Integer.parseInt(userAnswerString)-(getCorrectAnswerInt())<=10){
            addedPointsInt = 250;
        }
        addedPoints.setText("+"+String.valueOf(addedPointsInt));

        //after some effect
        pointsInt += addedPointsInt;
        addedPointsInt = 0;
        addedPoints.setText(null);
        points.setText(String.valueOf(pointsInt));

    }

    public void goToMainScreen () throws IOException {
        sceneCtrl.showMainScreen();
    }

    private Label getQuestionStatement() {
        return sampleQuestion;
    }

    public Button getAnswer() { return answer; }

    public String getCorrectAnswer() { return correctAnswer; }

    public int getCorrectAnswerInt() {
//        return Integer.parseInt(getCorrectAnswer());
        return 100;
    }

    public TextField getWriteAnswer() { return writeAnswer; }

    public void setWriteAnswer(TextField writeAnswer) { this.writeAnswer = writeAnswer; }

}
