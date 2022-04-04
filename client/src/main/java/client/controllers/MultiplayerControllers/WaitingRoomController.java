package client.controllers.MultiplayerControllers;


import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;


public class WaitingRoomController {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    @FXML
    private Text playerLabel; // displays the amount of players
    @FXML
    private Button startButton; // the button to start the game- only accessible to the owner
    @FXML
    private Text gameID; // displays the gameID of the current waiting room

    @Inject
    public WaitingRoomController(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    /**
     * Removes player from lobby and returns them to the server browser
     *
     * @param event the Action Event from the button
     */
    public void goBackToServerBrowser(ActionEvent event) {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        this.server.removePlayer(gameConfiguration.getUserName(), gameConfiguration.getRoomId());

        server.stopWaitForRoomThread();
        server.stopUpdatePlayerNumber();

        this.sceneCtrl.showServerBrowser();
    }

    /**
     * Adjusts all of the text field to the current user
     *
     */
    public void initialize() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        if (gameConfiguration.getRoomId() == null) {
            return;
        }

        this.gameID.setText(gameConfiguration.getRoomId());
        this.playerLabel.setText(String.valueOf(server.getNumPlayers()));

        server.waitForMultiPlayerRoomStart(q -> {
            try {
                sceneCtrl.showNextQuestion();
            } catch (Exception e) {
                System.out.println("Exception occurred when trying to start the game by showing the next question");
            }
        });

        server.updatePlayerNumber(q -> {
            try {
                this.playerLabel.setText(Integer.toString(q));
            } catch (Exception e) {
                System.out.println("Exception occurred when trying to show player number");
            }
        });

        this.startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent event) {
                    server.startRoom();
                    server.deleteEntries(GameConfiguration.getConfiguration().getRoomId());
                }
            });
    }

    /**
     * Refresh method to update the number of current players
     */
    public void refresh() throws IOException {
        server.stopWaitForRoomThread();
        server.stopUpdatePlayerNumber();

        this.initialize();
    }


}
