package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
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

    public void enterSoloGame () throws IOException {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        String userName = "cata";
        String roomId = server.createNewSinglePlayerRoom(userName);
        gameConfiguration.setRoomId(roomId);
        gameConfiguration.setUserName(userName);
        gameConfiguration.setCurrentQuestionNumber(gameConfiguration.getCurrentQuestionNumber() + 1);
        String response = server.getQuestion();
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        switch (scanner.next()) {
            case "OpenQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionGuessXScene(QuestionParsers.openQuestionParser(scanner.next()));
                break;
            }
            case "KnowledgeQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionHowMuchScene(QuestionParsers.knowledgeQuestionParser(scanner.next()));
                break;
            }
            case "ComparisonQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionWhatIsScene(QuestionParsers.comparisonQuestionParser(scanner.next()));
                break;
            }
            case "AlternativeQuestion": {
                sceneCtrl.showMainScreen();
                sceneCtrl.showQuestionInsteadOfScene(QuestionParsers.alternativeQuestionParser(scanner.next()));
                break;
            }
        }
    }
}
