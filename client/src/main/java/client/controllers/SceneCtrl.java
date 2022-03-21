package client.controllers;

import client.controllers.MultiplayerControllers.ServerBrowserController;
import client.controllers.MultiplayerControllers.WaitingRoomController;
import client.controllers.QuestionControllers.AlternativeQuestionCtrl;
import client.controllers.QuestionControllers.ComparisonQuestionCtrl;
import client.controllers.QuestionControllers.KnowledgeQuestionCtrl;
import client.controllers.QuestionControllers.OpenQuestionCtrl;
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
    private HashMap<String, Pair<Object, Scene>> scenes;


    public void initialize(Stage primaryStage, HashMap<String, Pair<Object, Scene>> scenes) {
        this.primaryStage = primaryStage;
        this.scenes = scenes;

        showMainScreenScene();
        primaryStage.show();
    }

    public void showAlternativeQuestionScene(AlternativeQuestion alternativeQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("AlternativeQuestion");
        AlternativeQuestionCtrl ctrl = (AlternativeQuestionCtrl) pair.getKey();

        primaryStage.setTitle("Question type 1");
        ctrl.setQuestion(alternativeQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showKnowledgeQuestionScene(KnowledgeQuestion knowledgeQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("KnowledgeQuestion");
        KnowledgeQuestionCtrl ctrl = (KnowledgeQuestionCtrl) pair.getKey();

        primaryStage.setTitle("Question type 2");
        ctrl.setQuestion(knowledgeQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showOpenQuestionScene(OpenQuestion openQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("OpenQuestion");
        OpenQuestionCtrl ctrl = (OpenQuestionCtrl) pair.getKey();

        primaryStage.setTitle("Question type 3");
        ctrl.setQuestion(openQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showComparisonQuestionScene(ComparisonQuestion comparisonQuestion, int questionNo, String userName, String roomId) {
        var pair = scenes.get("ComparisonQuestion");
        ComparisonQuestionCtrl ctrl = (ComparisonQuestionCtrl) pair.getKey();

        primaryStage.setTitle("Question type 4");
        ctrl.setQuestion(comparisonQuestion, questionNo, userName, roomId);
        primaryStage.setScene(pair.getValue());
    }

    public void showSingleplayerLeaderboard() {
        System.out.println("LEADERBOARD");
        var pair = scenes.get("SingleplayerLeaderboard");
        var ctrl = (SingleplayerLeaderboardCtrl) pair.getKey();

        ctrl.initialize();
        primaryStage.setTitle("Singleplayer Leaderboard");
        primaryStage.setScene(pair.getValue());
    }

    public void showMainScreenScene() {
        var pair = scenes.get("MainScreen");

        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(pair.getValue());
    }

    public void showAddActionScene() {
        var pair = scenes.get("AddAction");

        primaryStage.setTitle("Add actions");
        primaryStage.setScene(pair.getValue());
    }

    public void showServerBrowser() {
        var pair = scenes.get("ServerBrowser");
        ServerBrowserController ctrl = (ServerBrowserController) pair.getKey();


        primaryStage.setTitle("Server Browser");
        primaryStage.setScene(pair.getValue());
    }

    public void showWaitingRoom(boolean owner, String gameId, String userName) {
        var pair = scenes.get("WaitingRoom");
        WaitingRoomController ctrl = (WaitingRoomController) pair.getKey();

        ctrl.initialize(owner, gameId, userName);

        primaryStage.setTitle("WaitingRoom");
        primaryStage.setScene(pair.getValue());
    }

}
