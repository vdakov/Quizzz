package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.logic.QuestionParsers;
import com.google.inject.Inject;
import commons.Questions.AlternativeQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Scanner;

public class AlternativeQuestionCtrl {
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private String userName;
    private String serverId;
    private int questionNumber;
    private AlternativeQuestion alternativeQuestion;
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


    //Constructor for the Question Controller
    @Inject
    public AlternativeQuestionCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
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
        }
    }

    //Initializes the sample question screen through hardcoding
    public void initialize() {
        //resets the colors to white each time
        getAnswerTop().setStyle("-fx-background-color: #b38df7;; -fx-border-color:  #b38df7;");
        getAnswerCenter().setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");
        getAnswerBottom().setStyle("-fx-background-color: #ffa382; -fx-border-color:  #ffa382");
    }


    //method for answering the question- activated on click of button in QuestionScreen scene
    public void answer(ActionEvent event) {
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
        else sceneCtrl.showSingleplayerLeaderboard();
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
