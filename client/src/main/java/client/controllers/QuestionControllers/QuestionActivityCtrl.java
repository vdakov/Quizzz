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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.inject.Inject;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionActivityCtrl {
    // constructor needed variables
    protected final ServerUtils server;
    protected final SceneCtrl sceneCtrl;
    protected final GameConfiguration gameConfig = GameConfiguration.getConfiguration();
    protected final double startTime = 10;
    protected int addedPointsInt;
    protected String userAnswer;
    protected boolean answered;
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
    protected ImageView image;
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
    protected Button emoji;
    @FXML
    protected Button hintJoker;
    @FXML
    protected Button doublePointJoker;

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
    public void initialize() throws IOException {
        firstOptionText.setBorder(Border.EMPTY);
        firstOptionText.setDisable(false);
        secondOptionText.setBorder(Border.EMPTY);
        secondOptionText.setDisable(false);
        thirdOptionText.setBorder(Border.EMPTY);
        thirdOptionText.setDisable(false);

        answered = false;

        addedPoints.setText(" ");
        addedPointsInt = 0;


        if (gameConfig.isSinglePlayer()) emoji.setVisible(false);
        else emoji.setVisible(true);

        hintJoker.setDisable(false);
        if (getHintJokerUsed() != null) {
            hintJoker.setDisable(getHintJokerUsed());
        }

    }


    public void answerQuestion(MouseEvent event) {
        // answers the question
        if (answered) {
            return;
        }

        Label current = (Label) event.getSource();
        userAnswer = current.getText();
        answered = true;

        System.out.println("Tried to update answer");
        server.updateScore(userAnswer);

        answerUpdate();
        pointsUpdate();

        //blocks the possibility to answer anymore
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown

        //check whether the user's answer is correct and update the boolean value

        firstOptionText.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        secondOptionText.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        thirdOptionText.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));

        if (getCorrectAnswer().equals(firstOptionText.getText())) {
            firstOptionText.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        } else if (getCorrectAnswer().equals(secondOptionText.getText())) {
            secondOptionText.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        } else {
            thirdOptionText.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        }


    }

    public void pointsUpdate() {
        // after the time ends the amount of won points is calculated and then shown to the player

        addedPointsInt = 0;
        if (userAnswer.equals(getCorrectAnswer())) {
            addedPointsInt = getAddedPointsInt();
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
        timeline.setOnFinished(event -> {
            try {
                displayNextQuestion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });       //proceeds to the next question if no answer was given in 10 sec
        timeline.playFromStart();                                 //start the animation
    }

    public void handleTimer(MouseEvent event) {
        if (timeline != null) {
            timeline.stop();        //if timeline exists stop it when any answer button is pressed
        }
        timeline.stop();
        System.out.println("Time took to answer - " + timeSeconds);
    }

    public void displayNextQuestion() throws IOException {
        timeline.stop();
        if (gameConfig.getCurrentQuestionNumber() >= 19) finishGame();
        else sceneCtrl.showNextQuestion();
    }

    public void finishGame() {
        server.addLeaderboardEntry(gameConfig.getUserName(), gameConfig.getRoomId(), Integer.parseInt(server.getScore()));
        sceneCtrl.showLeaderboard();
    }

    public void goToMainScreen() throws IOException {
        timeline.stop();
        sceneCtrl.showMainScreenScene();
    }

    public void useHintJoker() {
        //Joker that eliminates the wrong answer
        if (server.getHintJokerUsed()) { return; }

        //Make a list of possible answers
        List<Label> answerLabels = new ArrayList();
        answerLabels.add(firstOptionText);
        answerLabels.add(secondOptionText);
        answerLabels.add(thirdOptionText);

        //shuffle answers to choose randomly from them
        Collections.shuffle(answerLabels);
        String correctAnswer = getCorrectAnswer();

        server.useHintJoker();
        hintJoker.setDisable(true);

        //go until incorrect answer is found and eliminate it
        for (Label answerLabel : answerLabels) {
            if (!answerLabel.getText().equals(correctAnswer)) {
                answerLabel.setDisable(true);
                return;
            }
        }
    }

    public void useDoublePointJoker() {
        if (server.getDoublePointJokerUsed()) { return; }

        server.useDoublePointJoker();
        doublePointJoker.setDisable(true);
    }

    public String getCorrectAnswer() {
        return server.getAnswer();
    }

    public int getPointsInt() {
        return Integer.parseInt(server.getScore());
    }

    public Boolean getHintJokerUsed() {
        return server.getHintJokerUsed();
    }

    public boolean getDoublePointJokerUsed() {
        return server.getDoublePointJokerUsed();
    }

    public int getQuestionNumber() {
        return gameConfig.getCurrentQuestionNumber();
    }

    public int getAddedPointsInt() {
        return Integer.parseInt(server.getAddedPoints());
    }
}
