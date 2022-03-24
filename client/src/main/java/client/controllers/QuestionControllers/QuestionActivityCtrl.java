package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import javax.inject.Inject;
import java.io.IOException;

public class QuestionActivityCtrl {
    // constructor needed variables
    protected final ServerUtils server;
    protected final SceneCtrl sceneCtrl;
    protected final GameConfiguration gameConfig = GameConfiguration.getConfiguration();
    protected final double startTime = 10;
    protected int addedPointsInt;
    protected String userAnswer;
    @FXML
    protected Label timeLabel;
    @FXML
    protected ProgressBar progressBarTime;
    // final needed labels for questions
    @FXML
    protected Label labelGoBack;
    @FXML
    protected Label questionStatement;
    @FXML
    protected ImageView questionStatementImage;
    @FXML
    protected Label firstOptionText;
    @FXML
    protected ImageView firstOptionImage;
    @FXML
    protected Label secondOptionText;
    @FXML
    protected ImageView secondOptionImage;
    @FXML
    protected Label thirdOptionText;
    @FXML
    protected ImageView thirdOptionImage;
    // current labels
    @FXML
    protected Label sampleQuestion;
    @FXML
    protected Button goToMainScreen;
    @FXML
    protected Label points;
    @FXML
    protected Label addedPoints;
    @FXML
    protected Label questionNumberLabel;
    @FXML
    protected String correctAnswer;
    @FXML
    protected Rectangle firstOptionRectangle;
    @FXML
    protected Rectangle secondOptionRectangle;
    @FXML
    protected Rectangle thirdOptionRectangle;
    protected IntegerProperty timeSeconds =
            new SimpleIntegerProperty((int) startTime);
    protected Timeline timeline;


    @Inject
    public QuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
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


    public void answerQuestion(MouseEvent event) {
        // answers the question
        Label current = (Label) event.getSource();
        userAnswer = current.getText();

        server.updateScore(userAnswer);

        answerUpdate();
        pointsUpdate();

        //blocks the possibility to answer anymore
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown

        //check whether the user's answer is correct and update the boolean value

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

//        FadeTransition fadeout = new FadeTransition(Duration.seconds(1), addedPoints);
//        fadeout.setFromValue(1);
//        fadeout.setToValue(0);
//        fadeout.play();
//
//        //after some effect
//        pointsInt += addedPointsInt;
//        addedPointsInt = 0;
//        addedPoints.setText(null);
//        points.setText(String.valueOf(pointsInt));
    }

    public void startTimer() {
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

    public void displayNextQuestion() {
        timeline.stop();
        if (gameConfig.getCurrentQuestionNumber() >= 20) finishGame();
        sceneCtrl.showNextQuestion();
    }

    public void finishGame() {
        server.addSingleplayerLeaderboardEntry(GameConfiguration.getConfiguration().getUserName(), Integer.parseInt(server.getScore()));
        sceneCtrl.showSingleplayerLeaderboard();
    }

    public void goToMainScreen() throws IOException {
        sceneCtrl.showMainScreenScene();
    }

    public String getCorrectAnswer() {
        return server.getAnswer();
    }

    public int getPointsInt() {
        return Integer.parseInt(server.getScore());
    }

    public int getQuestionNumber() {
        return gameConfig.getCurrentQuestionNumber();
    }
}
