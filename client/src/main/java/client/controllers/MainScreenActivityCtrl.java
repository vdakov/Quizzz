package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.io.IOException;

public class MainScreenActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    @FXML
    private TextField userName;
    @FXML
    private TextField serverField;

    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    /**
     * Initializes the scene
     */
    public void initialise() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        if (gameConfiguration.getUserName() != null) {
            userName.setText(gameConfiguration.getUserName());
        }
        gameConfiguration.setRoomId(null);
        gameConfiguration.setGameTypeUndefined();
    }

    /**
     * Enters a singleplayer game
     * @throws IOException
     */
    public void enterSoloGame() throws IOException {
        if (!checkUsername()) {
            return;
        }

        String username = userName.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        gameConfiguration.setUserName(username);
        gameConfiguration.setCurrentQuestionNumber(0);
        gameConfiguration.setGameTypeSingleplayer();

        String roomId = server.createNewRoom();
        gameConfiguration.setRoomId(roomId);

        if (roomId != null) {
            if (server.startRoom() == true) {
                sceneCtrl.showNextQuestion();
                return;
            }
        }
    }

    /**
     * Enters the server browser
     * @throws IOException
     */
    public void enterServerBrowser() throws IOException {
        if (!checkUsername()) {
            return;
        }

        String username = userName.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        gameConfiguration.setUserName(username);
        gameConfiguration.setGameTypeMultiPlayer();

        this.sceneCtrl.showServerBrowser();
    }

    /**
     * Checks for the username to not be blank
     * @return a boolean vakue
     */
    public boolean checkUsername() {
        if (userName.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A username needed");
            alert.setHeaderText("Please write the username you want to play with");
            alert.setContentText("Unfortunately, you cannot play a game");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Shows the leaderboard
     */
    public void showLeaderboard() {
        GameConfiguration.getConfiguration().setGameTypeSingleplayer(); //make sure you get to see the singleplayer version of the leaderboard
        sceneCtrl.showLeaderboard();
    }

    /**
     * Enters the admin interface
     */
    public void enterAdminInterface() {
        sceneCtrl.showOverviewActionScene();
    }



}
