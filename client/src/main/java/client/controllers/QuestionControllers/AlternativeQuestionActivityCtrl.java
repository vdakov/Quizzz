package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.logic.QuestionParsers;
import com.google.inject.Inject;
import commons.Questions.AlternativeQuestion;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.util.Scanner;

import javafx.util.Duration;

public class AlternativeQuestionActivityCtrl {
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private String userName;
    private String serverId;
    private int questionNumber;
    private AlternativeQuestion alternativeQuestion;
    private int pointsInt;
    private String correctAnswer;
    private final double startTime = 10;
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty((int) startTime);
    private Timeline timeline;
    @FXML
    private Label timeLabel;
    @FXML
    private ProgressBar progressBarTime;


    @FXML
    private Label sampleQuestion;
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
    private ToolBar toolbar;
    @FXML
    private ImageView emoji1;
    @FXML
    private ImageView emoji2;
    @FXML
    private ImageView emoji3;
    @FXML
    private ImageView emoji4;
    @FXML
    private ImageView emoji5;
    @FXML
    private ImageView startAnimation;


    //Constructor for the Question Controller
    @Inject
    public AlternativeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void setQuestion(AlternativeQuestion alternativeQuestion, int questionNumber, String userName, String serverId) {
        this.alternativeQuestion = alternativeQuestion;
        this.questionNumber = questionNumber;
        this.userName = userName;
        this.serverId = serverId;

        this.sampleQuestion.setText((alternativeQuestion == null) ? "" : alternativeQuestion.getQuestion().getKey());

        labelAnswerBottom.setText((alternativeQuestion == null) ? "" : alternativeQuestion.getOptions().get(0).getKey());
        labelAnswerTop.setText((alternativeQuestion == null) ? "" : alternativeQuestion.getOptions().get(1).getKey());
        labelAnswerCenter.setText((alternativeQuestion == null) ? "" : alternativeQuestion.getOptions().get(2).getKey());

        this.correctAnswer = "" + ((alternativeQuestion == null) ? "" : alternativeQuestion.getOptions().get(1).getKey());

        // if(serverId is Singleplayer)
        {
            toolbar.setStyle("-fx-opacity: 1");
            emoji1.setStyle("-fx-opacity: 1 ");
            emoji2.setStyle("-fx-opacity: 1");
            emoji3.setStyle("-fx-opacity: 1 ");
            emoji4.setStyle("-fx-opacity: 1");
            emoji5.setStyle("-fx-opacity: 1 ");
        }

    }

    public void emoji1Animation(MouseEvent event) {
        sceneCtrl.showMainScreenScene();
    }

    public void transition(ImageView image) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setDuration(Duration.millis(3500));
        translate.setCycleCount(1);
        translate.setByY(-500);
        translate.play();
    }

    public void goToNextQuestion() {
        String response = server.getQuestion(this.userName, this.serverId, this.questionNumber + 1);
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        System.out.println("Alternative question am intrat");
        String next = scanner.next();
        System.out.println(next);
        switch (next) {
            case "OpenQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showOpenQuestionScene(QuestionParsers.openQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showKnowledgeQuestionScene(QuestionParsers.knowledgeQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showComparisonQuestionScene(QuestionParsers.comparisonQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showAlternativeQuestionScene(QuestionParsers.alternativeQuestionParser(scanner.next()), this.questionNumber + 1, userName, serverId);
                break;
            }
            default:
                //start timer when next question is shown
                //startTimer();
        }
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {
        //resets the colors to white each time
        getAnswerTop().setStyle("-fx-background-color: #b38df7;; -fx-border-color:  #b38df7;");
        getAnswerCenter().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");
        getAnswerBottom().setStyle("-fx-background-color: #ffa382; -fx-border-color:  #ffa382");

    }

    public void startTimer() {
        progressBarTime.progressProperty().bind(Bindings.divide(timeSeconds, startTime));

        timeLabel.textProperty().bind(timeSeconds.asString());    //bind the progressbar value to the seconds left
        timeSeconds.set((int) startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(startTime + 1),      //the timeLine handles an animation which lasts start + 1 seconds
                        new KeyValue(timeSeconds, 0)));    //animation finishes when timeSeconds comes to 0
        timeline.setOnFinished(event -> goToNextQuestion());       //proceeds to the next question if no answer was given in 10 sec
        timeline.playFromStart();                                 //start the animation
    }

    public void handleTimerButton(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();        //if timeline exists stop it when any answer button is pressed
        }
        timeline.stop();
        System.out.println("Time took to answer - " + timeSeconds);
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) {
        Button current = (Button) event.getSource();

        handleTimerButton(event);

        if (current.getText().equals(getCorrectAnswer())) {
            // multiply the score by percentage time remaining
            pointsInt += 500.0 * ((float) timeSeconds.getValue() / startTime); //global variable for points so it remembers it
        }

        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(labelAnswerCenter.getText(), this.getAnswerCenter());
        answerCheck(labelAnswerTop.getText(), this.getAnswerTop());
        answerCheck(labelAnswerBottom.getText(), this.getAnswerBottom());

        //changes the points value
        points.setText(String.valueOf(pointsInt));

        if (questionNumber < 20) goToNextQuestion();
        else finishGame();
    }


    //Method that checks whether answer is correct
    public void answerCheck(String answer, Button current) {
        long mTime = System.currentTimeMillis();
        long end = mTime + 1000;

        // This should be setting the colour and then go to a new question screen but it doesn't work right now
        //do {
        if (answer.equals(getCorrectAnswer())) {
            current.setStyle("-fx-background-color: #00FF00; "); //simple CSS for clarity
        } else {
            current.setStyle("-fx-background-color: #d20716; ");
        }
        //} while (System.currentTimeMillis() < end);

        this.initialize();

    }

    public void finishGame() {
        server.addSingleplayerLeaderboardEntry(userName, pointsInt);
        sceneCtrl.showSingleplayerLeaderboard();
    }

    public void goToMainScreen(ActionEvent event) throws IOException {
        sceneCtrl.showMainScreenScene();
    }


    //Getters and setters
    public Button getAnswerTop() {
        return answerTop;
    }

    public Button getAnswerBottom() {
        return answerBottom;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Label getLabelAnswerTop() {
        return labelAnswerTop;
    }

    public Label getLabelAnswerCenter() {
        return labelAnswerCenter;
    }

    public Label getLabelAnswerBottom() {
        return labelAnswerBottom;
    }

    public void setLabelAnswerTop(Label labelAnswerTop) {
        this.labelAnswerTop = labelAnswerTop;
    }

    public void setLabelAnswerCenter(Label labelAnswerCenter) {
        this.labelAnswerCenter = labelAnswerCenter;
    }

    public void setLabelAnswerBottom(Label labelAnswerBottom) {
        this.labelAnswerBottom = labelAnswerBottom;
    }
}
