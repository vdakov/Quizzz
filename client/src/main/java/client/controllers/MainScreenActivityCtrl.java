package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Scanner;

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

    public void enterSoloGame () throws IOException {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        String playerName = userName.getText();
        String roomId = server.createNewSinglePlayerRoom(playerName);
        gameConfiguration.setRoomId(roomId);
        gameConfiguration.setUserName(playerName);
        gameConfiguration.setCurrentQuestionNumber(gameConfiguration.getCurrentQuestionNumber() + 1);
        String response = server.getQuestion();
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        sceneCtrl.showNextQuestion();
    }

    public void enterServerBrowser(ActionEvent event) {
        this.sceneCtrl.showServerBrowser();
    }
}
