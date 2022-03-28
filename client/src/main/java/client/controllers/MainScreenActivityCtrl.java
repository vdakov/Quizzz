package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.io.IOException;

public class MainScreenActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;
    private GameConfiguration gameConfig;
    @FXML
    private TextField userName;

    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
        gameConfig = GameConfiguration.getConfiguration();
    }

    public void enterSoloGame() throws IOException {
        String playerName = userName.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        gameConfiguration.setUserName(playerName);
        gameConfiguration.setCurrentQuestionNumber(gameConfiguration.getCurrentQuestionNumber() + 1);
        gameConfiguration.setGameTypeSingleplayer();

        String roomId = server.createNewRoom();
        gameConfiguration.setRoomId(roomId);

        if (roomId != null) {
            if (server.startRoom() == true) {
                sceneCtrl.showNextQuestion();
            }
        }
    }

    public void showLeaderboard() {
        gameConfig.setGameTypeSingleplayer(); //make sure you get to see the singleplayer version of the leaderboard
        sceneCtrl.showLeaderboard();
    }


    public void enterServerBrowser() throws IOException {
        gameConfig.setGameTypeMultiPlayer();
        String playerName = userName.getText();
        gameConfig.setUserName(playerName);

        this.sceneCtrl.showServerBrowser();
    }

    public void enterAdminInterface() {
        sceneCtrl.showOverviewActionScene();
    }
}
