package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.logic.QuestionParsers;
import com.google.inject.Inject;
import commons.Questions.KnowledgeQuestion;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.Scanner;


public class KnowledgeQuestionActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private int questionNumber;
    private String userName;
    private String roomId;
    private KnowledgeQuestion knowledgeQuestion;
    private int pointsInt;
    private final double startTime = 10;
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty((int)startTime);
    private Timeline timeline;
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
    @FXML
    private Label timeLabel;
    @FXML
    private ProgressBar progressBarTime;


    //Constructor for the Question Controller
    @Inject
    public KnowledgeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.knowledgeQuestion = null;
    }

    public void setQuestion(KnowledgeQuestion knowledgeQuestion, int questionNumber, String userName, String roomId) {
        this.knowledgeQuestion = knowledgeQuestion;
        this.questionNumber = questionNumber;
        this.userName = userName;
        this.roomId = roomId;

        this.sampleQuestion.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getQuestion().getKey());

        labelAnswerBottom.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(0));
        labelAnswerTop.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(1));
        labelAnswerCenter.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(2));

        this.correctAnswer = "" + ((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(1));
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {

        //resets the colors to white each time
        getAnswerTop().setStyle("-fx-background-color: #b38df7;; -fx-border-color:  #b38df7;");
        getAnswerCenter().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");
        getAnswerBottom().setStyle("-fx-background-color: #ffa382; -fx-border-color:  #ffa382");


        //hardcoded activity- have to eventually make it initialize through a database- Not now tho :)

        //transforms the activity into a question
        this.sampleQuestion.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getQuestion().getKey());

        labelAnswerBottom.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(0));
        labelAnswerTop.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(1));
        labelAnswerCenter.setText((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(2));

        this.correctAnswer = "" + ((knowledgeQuestion == null) ? "" : knowledgeQuestion.getOptions().get(1));
    }

    public void startTimer(){
        progressBarTime.progressProperty().bind(Bindings.divide(timeSeconds, startTime));
        timeLabel.textProperty().bind(timeSeconds.asString());
        timeSeconds.set((int)startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(startTime+1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
    }
    public void handleTimerButton(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
        timeline.stop();
        System.out.println("Time took to answer - " + timeSeconds);
    }

    public void goToNextQuestion() {
        String response = server.getQuestion(this.userName, this.roomId, this.questionNumber + 1);
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        System.out.println("Knowledge question am intrat");
        String next = scanner.next();
        System.out.println(next);
        switch (next) {
            case "OpenQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionGuessXScene(QuestionParsers.openQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionHowMuchScene(QuestionParsers.knowledgeQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionWhatIsScene(QuestionParsers.comparisonQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionInsteadOfScene(QuestionParsers.alternativeQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
        }
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) throws InterruptedException {
        Button current = (Button) event.getSource();

        // stop the timer
        if (timeline != null) {
            timeline.stop();
        }
        timeline.stop();
        System.out.println("Time took to answer - " + (timeSeconds.getValue() - startTime) );
        //

        if (current.getText().equals(getCorrectAnswer())) {
            pointsInt += 500; //global variable for points so it remembers it
        }

        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(labelAnswerCenter.getText(), this.getAnswerCenter());
        answerCheck(labelAnswerTop.getText(), this.getAnswerTop());
        answerCheck(labelAnswerBottom.getText(), this.getAnswerBottom());

        //changes the points value
        points.setText(String.valueOf(pointsInt));

        goToNextQuestion();

    }


    //Method that checks whether answer is correct
    //
    public void answerCheck(String answer, Button current) throws InterruptedException {

        if (answer.equals(getCorrectAnswer())) {
            current.setStyle("-fx-background-color: #00FF00; ");  //simple CSS for clarity
        } else {
            current.setStyle("-fx-background-color: #d20716;");
        }

    }

    public void goToMainScreen (ActionEvent event) throws IOException {
        sceneCtrl.showMainScreen();
    }

    //Getters and setters
    public Button getAnswerTop() { return answerTop; }

    public Button getAnswerBottom() {
        return answerBottom;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Label getLabelAnswerTop() { return labelAnswerTop; }

    public Label getLabelAnswerCenter() { return labelAnswerCenter; }

    public Label getLabelAnswerBottom() { return labelAnswerBottom; }

    public void setLabelAnswerTop(Label labelAnswerTop) { this.labelAnswerTop = labelAnswerTop; }

    public void setLabelAnswerCenter(Label labelAnswerCenter) { this.labelAnswerCenter = labelAnswerCenter; }

    public void setLabelAnswerBottom(Label labelAnswerBottom) { this.labelAnswerBottom = labelAnswerBottom; }
}

