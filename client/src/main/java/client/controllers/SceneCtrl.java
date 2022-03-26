package client.controllers;


import client.communication.ServerUtils;
import client.controllers.AdminInterface.AddActionActivityCtrl;
import client.controllers.AdminInterface.EditActionActivityCtrl;
import client.controllers.AdminInterface.OverviewActionsActivityCtrl;
import client.controllers.MultiplayerControllers.ServerBrowserController;
import client.controllers.MultiplayerControllers.WaitingRoomController;
import client.controllers.QuestionControllers.AlternativeQuestionActivityCtrl;
import client.controllers.QuestionControllers.ComparisonQuestionActivityCtrl;
import client.controllers.QuestionControllers.KnowledgeQuestionActivityCtrl;
import client.controllers.QuestionControllers.OpenQuestionActivityCtrl;
import client.data.GameConfiguration;
import client.logic.QuestionParsers;
import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Scanner;

public class SceneCtrl {
    private Stage primaryStage;
    private HashMap<String, Pair<Object, Scene>> scenes;

    private ServerUtils serverUtils;
    private GameConfiguration gameConfiguration;

    @Inject
    public void initialiseServer(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
        this.gameConfiguration = GameConfiguration.getConfiguration();
    }


    public void initialize(Stage primaryStage, HashMap<String, Pair<Object, Scene>> scenes) {
        this.primaryStage = primaryStage;
        this.scenes = scenes;

        showMainScreenScene();
        primaryStage.show();
    }

    public void showNextQuestion() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        gameConfiguration.setCurrentQuestionNumber(gameConfiguration.getCurrentQuestionNumber() + 1);
        String response = serverUtils.getQuestion();
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        String questionType = scanner.next();
        primaryStage.setTitle("Question #" + gameConfiguration.getCurrentQuestionNumber() + ": " + questionType);
        switch (questionType) {
            case "OpenQuestion": {
                this.showOpenQuestionScene(QuestionParsers.openQuestionParser(scanner.next()));
                break;
            }
            case "KnowledgeQuestion": {
                this.showKnowledgeQuestionScene(QuestionParsers.knowledgeQuestionParser(scanner.next()));
                break;
            }
            case "ComparisonQuestion": {
                this.showComparisonQuestionScene(QuestionParsers.comparisonQuestionParser(scanner.next()));
                break;
            }
            case "AlternativeQuestion": {
                this.showAlternativeQuestionScene(QuestionParsers.alternativeQuestionParser(scanner.next()));
                break;
            }
        }
    }

    public void showOpenQuestionScene(OpenQuestion openQuestion) {
        var pair = scenes.get("OpenQuestion");
        OpenQuestionActivityCtrl ctrl = (OpenQuestionActivityCtrl) pair.getKey();
        ctrl.displayQuestion(openQuestion);
        primaryStage.setScene(pair.getValue());
    }

    public void showKnowledgeQuestionScene(KnowledgeQuestion knowledgeQuestion) {
        var pair = scenes.get("KnowledgeQuestion");
        KnowledgeQuestionActivityCtrl ctrl = (KnowledgeQuestionActivityCtrl) pair.getKey();

        ctrl.displayQuestion(knowledgeQuestion);
        primaryStage.setScene(pair.getValue());
    }

    public void showComparisonQuestionScene(ComparisonQuestion comparisonQuestion) {
        var pair = scenes.get("ComparisonQuestion");
        ComparisonQuestionActivityCtrl ctrl = (ComparisonQuestionActivityCtrl) pair.getKey();
        ctrl.displayQuestion(comparisonQuestion);
        primaryStage.setScene(pair.getValue());
    }

    public void showAlternativeQuestionScene(AlternativeQuestion alternativeQuestion) {
        var pair = scenes.get("AlternativeQuestion");
        AlternativeQuestionActivityCtrl ctrl = (AlternativeQuestionActivityCtrl) pair.getKey();
        ctrl.displayQuestion(alternativeQuestion);
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

    public void showOverviewActionScene() {
        var pair = scenes.get("OverviewActions");
        var ctrl = (OverviewActionsActivityCtrl) pair.getKey();

        ctrl.initialize();
        primaryStage.setTitle("Overview Action");
        primaryStage.setScene(pair.getValue());
    }

    public void showAddActionScene() {
        var pair = scenes.get("AddAction");
        var ctrl = (AddActionActivityCtrl) pair.getKey();

        ctrl.initialize();
        primaryStage.setTitle("Add actions");
        primaryStage.setScene(pair.getValue());
    }

    public void showEditActionScene() {
        var pair = scenes.get("EditAction");
        var ctrl = (EditActionActivityCtrl) pair.getKey();

        ctrl.initialize();
        primaryStage.setTitle("Edit actions");
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
