package client.controllers;


import client.communication.ServerUtils;
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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Scanner;

public class SceneCtrl {
    private Stage primaryStage;
    private Scene scene;
    private HashMap<String, Pair<Object, Parent>> sceneRoots;

    private ServerUtils serverUtils;
    private GameConfiguration gameConfiguration;

    @Inject
    public void initialiseServer(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
        this.gameConfiguration = GameConfiguration.getConfiguration();
    }


    public void initialize(Stage primaryStage, HashMap<String, Pair<Object, Parent>> sceneRoots) {
        this.primaryStage = primaryStage;
        this.sceneRoots = sceneRoots;
        this.scene = new Scene(new Group(), 1080, 720);
        this.primaryStage.setScene(this.scene);

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
        var pair = sceneRoots.get("OpenQuestion");
        OpenQuestionActivityCtrl ctrl = (OpenQuestionActivityCtrl) pair.getKey();
        ctrl.displayQuestion(openQuestion);
        scene.setRoot(pair.getValue());
    }

    public void showKnowledgeQuestionScene(KnowledgeQuestion knowledgeQuestion) {
        var pair = sceneRoots.get("KnowledgeQuestion");
        KnowledgeQuestionActivityCtrl ctrl = (KnowledgeQuestionActivityCtrl) pair.getKey();

        ctrl.displayQuestion(knowledgeQuestion);
        scene.setRoot(pair.getValue());
    }

    public void showComparisonQuestionScene(ComparisonQuestion comparisonQuestion) {
        var pair = sceneRoots.get("ComparisonQuestion");
        ComparisonQuestionActivityCtrl ctrl = (ComparisonQuestionActivityCtrl) pair.getKey();
        ctrl.displayQuestion(comparisonQuestion);
        scene.setRoot(pair.getValue());
    }

    public void showAlternativeQuestionScene(AlternativeQuestion alternativeQuestion) {
        var pair = sceneRoots.get("AlternativeQuestion");
        AlternativeQuestionActivityCtrl ctrl = (AlternativeQuestionActivityCtrl) pair.getKey();
        ctrl.displayQuestion(alternativeQuestion);
        scene.setRoot(pair.getValue());
    }

    public void showLeaderboard() {
        System.out.println("LEADERBOARD");
        var pair = sceneRoots.get("SingleplayerLeaderboard");
        var ctrl = (LeaderboardCtrl) pair.getKey();

        ctrl.initialize();
        primaryStage.setTitle("Singleplayer Leaderboard");
        scene.setRoot(pair.getValue());
    }

    public void showMainScreenScene() {
        var pair = sceneRoots.get("MainScreen");

        primaryStage.setTitle("Main Screen");
        scene.setRoot(pair.getValue());
    }

    public void showAddActionScene() {
        var pair = sceneRoots.get("AddAction");

        primaryStage.setTitle("Add actions");
        scene.setRoot(pair.getValue());
    }

    public void showServerBrowser() {
        var pair = sceneRoots.get("ServerBrowser");
        ServerBrowserController ctrl = (ServerBrowserController) pair.getKey();


        primaryStage.setTitle("Server Browser");
        scene.setRoot(pair.getValue());
    }

    public void showWaitingRoom(boolean owner, String gameId, String userName) {
        var pair = sceneRoots.get("WaitingRoom");
        WaitingRoomController ctrl = (WaitingRoomController) pair.getKey();

        ctrl.initialize(owner, gameId, userName);

        primaryStage.setTitle("WaitingRoom");
        scene.setRoot(pair.getValue());
    }
}
