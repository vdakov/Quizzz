package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.logic.QuestionParsers;
import com.google.inject.Inject;
import commons.Questions.ComparisonQuestion;
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

public class ComparisonQuestionCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private int questionNumber;
    private ComparisonQuestion comparisonQuestion;
    private String userName;
    private String serverId;
    private int pointsInt;

    @FXML
    private Label sampleQuestion;
    @FXML
    private Button answerLeft, answerCenter, answerRight;
    @FXML
    private Label points;
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
    public ComparisonQuestionCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void setQuestion(ComparisonQuestion comparisonQuestion, int questionNumber, String userName, String serverId) {
        this.comparisonQuestion = comparisonQuestion;
        this.questionNumber = questionNumber;
        this.userName = userName;
        this.serverId = serverId;

        this.sampleQuestion.setText((comparisonQuestion == null) ? "" : comparisonQuestion.getQuestion().getKey());

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
    }



    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) {
        Button current = (Button) event.getSource();
        System.out.println("Comparison question am intrat");

        if (current.getText().equals("10")) {
            pointsInt += 500; //global variable for points so it remembers it
        }

        //uses the answerCheck method to highlight which the correct answer was
        //and to color them
        answerCheck(answerCenter.getText(), this.getAnswerCenter());
        answerCheck(answerLeft.getText(), this.getAnswerTop());
        answerCheck(answerRight.getText(), this.getAnswerBottom());

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
    public void answerCheck(String answer, Button current) {
        long mTime = System.currentTimeMillis();
        long end = mTime + 1000;

        // This should be setting the colour and then go to a new question screen but it doesn't work right now
        //do {
        if (answer.equals("10")) {
            current.setStyle("-fx-background-color: #00FF00; "); //simple CSS for clarity
        } else {
            current.setStyle("-fx-background-color: #d20716; ");
        }
        // } while (System.currentTimeMillis() < end);

        this.initialize();

    }

    public void goToNextQuestion() {
        String response = server.getQuestion(this.userName, this.serverId, this.questionNumber + 1);
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        String next = scanner.next();
        System.out.println(next + " aici");
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
        }
    }

    public void goToMainScreen(ActionEvent event) throws IOException {
        sceneCtrl.showMainScreenScene();
    }


    //Getters and setters
    public Button getAnswerTop() {
        return answerLeft;
    }

    public Button getAnswerBottom() {
        return answerRight;
    }

    public Button getAnswerCenter() {
        return answerCenter;
    }

}
