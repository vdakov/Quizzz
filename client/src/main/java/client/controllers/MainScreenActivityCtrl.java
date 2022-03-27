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

    public void showSingleplayerLeaderboard() {
        sceneCtrl.showSingleplayerLeaderboard();
    }

    public void enterServerBrowser() throws IOException {

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        gameConfiguration.setGameTypeMultiPlayer();
        String playerName = userName.getText();
        gameConfiguration.setUserName(playerName);

        this.sceneCtrl.showServerBrowser();
    }


}
