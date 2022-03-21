package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.logic.QuestionParsers;
import com.google.inject.Inject;
import commons.Questions.KnowledgeQuestion;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Scanner;


public class KnowledgeQuestionCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private int questionNumber;
    private String userName;
    private String roomId;
    private KnowledgeQuestion knowledgeQuestion;
    private int pointsInt;

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
    private String correctAnswer;
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


    //Constructor for the Question Controller
    @Inject
    public KnowledgeQuestionCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
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

        // if(serverId is Multiplayer)
        {
            toolbar.setStyle("-fx-opacity: 1");
            emoji1.setStyle("-fx-opacity: 1 ");
            emoji2.setStyle("-fx-opacity: 1");
            emoji3.setStyle("-fx-opacity: 1 ");
            emoji4.setStyle("-fx-opacity: 1");
            emoji5.setStyle("-fx-opacity: 1 ");
        }
    }

    public void transition(ImageView image)
    {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setDuration(Duration.millis(3500));
        translate.setCycleCount(1);
        translate.setByY(-500);
        translate.play();
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


    public void goToNextQuestion() {
        String response = server.getQuestion(this.userName, this.roomId, this.questionNumber + 1);
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        System.out.println("Knowledge question am intrat");
        String next = scanner.next();
        System.out.println(next);
        switch (next) {
            case "OpenQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showOpenQuestionScene(QuestionParsers.openQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showKnowledgeQuestionScene(QuestionParsers.knowledgeQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showComparisonQuestionScene(QuestionParsers.comparisonQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showMainScreenScene();
                sceneCtrl.showAlternativeQuestionScene(QuestionParsers.alternativeQuestionParser(scanner.next()), this.questionNumber + 1, userName, roomId);
                break;
            }
        }
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) throws InterruptedException {
        Button current = (Button) event.getSource();


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

        if (questionNumber < 20) goToNextQuestion();
        else finishGame();
    }

    public void finishGame() {
        server.addSingleplayerLeaderboardEntry(userName, pointsInt);
        sceneCtrl.showSingleplayerLeaderboard();
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

