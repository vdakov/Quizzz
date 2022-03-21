package client.controllers;

import client.communication.ServerUtils;
import client.logic.QuestionParsers;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Scanner;

public class MainScreenActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    public void enterSoloGame() throws IOException {
        String userName = "cata";
        String serverId = server.createNewSinglePlayerRoom(userName);
        System.out.println(serverId);

        String response = server.getQuestion(userName, serverId, 0);
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        switch (scanner.next()) {
            case "OpenQuestion": {
                sceneCtrl.showOpenQuestionScene(QuestionParsers.openQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showKnowledgeQuestionScene(QuestionParsers.knowledgeQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showComparisonQuestionScene(QuestionParsers.comparisonQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showAlternativeQuestionScene(QuestionParsers.alternativeQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
        }

    }

    public void showSingleplayerLeaderboard() {
        sceneCtrl.showSingleplayerLeaderboard();
    }
}
