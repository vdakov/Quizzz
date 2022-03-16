package client.controllers;

import client.communication.ServerUtils;
import client.logic.QuestionParsers;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Scanner;

public class MainScreenActivityCtrl extends Controller {

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
                sceneCtrl.showQuestionGuessXScene(QuestionParsers.openQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showQuestionHowMuchScene(QuestionParsers.knowledgeQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showQuestionWhatIsScene(QuestionParsers.comparisonQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showQuestionInsteadOfScene(QuestionParsers.alternativeQuestionParser(scanner.next()), 0, userName, serverId);
                break;
            }
        }

    }
}
