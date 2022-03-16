package client.controllers;

import client.controllers.QuestionControllers.QuestionAlternativeCtrl;
import client.controllers.QuestionControllers.QuestionComparisonCtrl;
import client.controllers.QuestionControllers.QuestionKnowledgeCtrl;
import client.controllers.QuestionControllers.QuestionOpenCtrl;
import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashMap;

public class SceneCtrl {

    private Stage primaryStage;

    private HashMap<String, Pair<Controller, Scene>> scenes;


    public void initialize(Stage primaryStage, HashMap<String, Pair<Controller, Scene>> scenes) {
        this.primaryStage = primaryStage;

        this.scenes = scenes;

        primaryStage.setScene(scenes.get("MainScreen").getValue());
        primaryStage.show();
    }

    public void showQuestionInsteadOfScene(AlternativeQuestion alternativeQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("QuestionAlternative");
        QuestionAlternativeCtrl ctrl = (QuestionAlternativeCtrl) pair.getKey();

        primaryStage.setTitle("Question type 1");
        ctrl.setQuestion(alternativeQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showQuestionHowMuchScene(KnowledgeQuestion knowledgeQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("QuestionKnowledge");
        QuestionKnowledgeCtrl ctrl = (QuestionKnowledgeCtrl) pair.getKey();

        primaryStage.setTitle("Question type 2");
        ctrl.setQuestion(knowledgeQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showQuestionGuessXScene(OpenQuestion openQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("QuestionOpen");
        QuestionOpenCtrl ctrl = (QuestionOpenCtrl) pair.getKey();

        primaryStage.setTitle("Question type 3");
        ctrl.setQuestion(openQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showQuestionWhatIsScene(ComparisonQuestion comparisonQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("QuestionComparison");
        QuestionComparisonCtrl ctrl = (QuestionComparisonCtrl) pair.getKey();

        primaryStage.setTitle("Question type 4");
        ctrl.setQuestion(comparisonQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showMainScreen() {
        var pair = scenes.get("MainScreen");

        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(pair.getValue());
    }

    public void showAddActionScene() {
        var pair = scenes.get("AddAction");

        primaryStage.setTitle("Add actions");
        primaryStage.setScene(pair.getValue());
    }
}
