package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import com.google.inject.Inject;
import commons.Questions.OpenQuestion;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

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

    private int userAnswerInt;
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
    private Rectangle userAnswerRectangle;
    @FXML
    private Rectangle correctAnswerRectangle;

    @FXML
    private Label timeLabel;
    @FXML
    private ProgressBar progressBarTime;



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

        initialize();
        startTimer();
    }

    /**
     * Displays the next question to the user after the transition is finished
     */
    public void displayNextQuestion() {
        sceneCtrl.showNextQuestion();
    }

    public void answerQuestion() {
        // answers the question and blocks the possibility to answer anymore
        try {
            userAnswerInt = Integer.parseInt(userAnswer.getText());
            System.out.println(1);
        } catch (NumberFormatException e) {
            userAnswer.setText("-99999");
            server.updateScore(userAnswer.getText());
            userAnswerInt = Integer.parseInt(userAnswer.getText());
        } catch (NullPointerException e) {
            if (userAnswer.getText() == (null) || userAnswer.getText().trim().isEmpty()) {
                userAnswer.setText("-99999");
                userAnswerInt = Integer.parseInt(userAnswer.getText());
            }
        }  catch (Exception e ) {
            System.out.println(e);
        }

        answerUpdate();
        pointsUpdate();
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown
        userAnswerRectangle.setStrokeWidth(5);
        System.out.println(userAnswerInt);
        if (userAnswerInt == (getCorrectAnswerInt())) {
            userAnswerRectangle.setStroke(Color.valueOf("#92d36e"));
        } else {
            userAnswerRectangle.setStroke(Color.valueOf("#ff0000"));
        }

        correctAnswerRectangle.setFill(Color.valueOf("#c9f1fd"));
        correctAnswerRectangle.setStrokeWidth(5);
        correctAnswerRectangle.setStroke(Color.valueOf("#000000"));
        correctAnswerLabel.setText("Correct Answer: " + getCorrectAnswerInt());
    }

    public void pointsUpdate() {
        // after the time ends the amount of won points is calculated and then shown to the player
        if (userAnswerInt == (getCorrectAnswerInt())) {
            addedPointsInt = 500;
        } else if (userAnswerInt > getCorrectAnswerInt() * 0.95 || userAnswerInt < getCorrectAnswerInt() * 1.05) {
            addedPointsInt = 250;
        }
        addedPoints.setText("+" + String.valueOf(addedPointsInt));

        //after some effect
        pointsInt += addedPointsInt;
        addedPointsInt = 0;
        addedPoints.setText(null);
        points.setText(String.valueOf(pointsInt));

//        server.updateScore()

    }

    private final double startTime = 10;
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty((int) startTime);
    private Timeline timeline;

    private void startTimer() {
        progressBarTime.progressProperty().bind(Bindings.divide(timeSeconds, startTime));

        timeLabel.textProperty().bind(timeSeconds.asString());    //bind the progressbar value to the seconds left
        timeSeconds.set((int) startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(startTime + 1),      //the timeLine handles an animation which lasts start + 1 seconds
                        new KeyValue(timeSeconds, 0)));    //animation finishes when timeSeconds comes to 0
        timeline.setOnFinished(event -> displayNextQuestion());       //proceeds to the next question if no answer was given in 10 sec
        timeline.playFromStart();                                 //start the animation
    }

    public void handleTimerButton(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();        //if timeline exists stop it when any answer button is pressed
        }
        timeline.stop();
        System.out.println("Time took to answer - " + timeSeconds);
    }

    public void goToMainScreen () throws IOException {
        sceneCtrl.showMainScreenScene();
    }


    private Label getQuestionStatement() {
        return sampleQuestion;
    }

    public Button getAnswer() { return answer; }

    public int getCorrectAnswerInt() {
        return Integer.parseInt(server.getAnswer());
    }

    public TextField getWriteAnswer() { return userAnswer; }

    public void setWriteAnswer(TextField writeAnswer) { this.userAnswer = userAnswer; }

    public int getPointsInt() {
        return Integer.parseInt(server.getScore());
    }

    public int getQuestionNumber() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        return gameConfiguration.getCurrentQuestionNumber();
    }
}
