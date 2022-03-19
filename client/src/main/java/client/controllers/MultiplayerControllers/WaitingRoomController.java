package client.controllers.MultiplayerControllers;


import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WaitingRoomController {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    private int numberOfPlayers;
    private String gameId;

    @FXML
    private Label playerLabel;
    @FXML
    private Button startButton;

    @Inject
    public WaitingRoomController(ServerUtils server, SceneCtrl sceneCtrl){
        this.server=server;
        this.sceneCtrl= sceneCtrl;
    }

    public void initialize(){

    }



}
