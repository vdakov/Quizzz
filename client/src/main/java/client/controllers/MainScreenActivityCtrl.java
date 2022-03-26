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

    @FXML
    private TextField userName;

    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    public void enterSoloGame() throws IOException {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        String playerName = userName.getText();
        String roomId = server.createNewSinglePlayerRoom(playerName);
        gameConfiguration.setRoomId(roomId);
        gameConfiguration.setUserName(playerName);
        gameConfiguration.setCurrentQuestionNumber(1);
        gameConfiguration.setGameTypeSingleplayer();
        sceneCtrl.showNextQuestion();
    }

    public void showLeaderboard() {
        sceneCtrl.showLeaderboard();
    }

    public void enterServerBrowser() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        gameConfiguration.setGameTypeMultiPlayer();
        this.sceneCtrl.showServerBrowser();
    }
}
