package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.GameContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ServerBrowserController {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;


    @FXML
    private TableView<GameContainer> games;
    @FXML
    private TableColumn<GameContainer, String> gameId;
    @FXML
    private TableColumn<GameContainer, Integer> numPlayers;


    @FXML
    private Button mainMenuButton;
    @FXML
    private Button joinWaitingRoom;

    @Inject
    public ServerBrowserController(ServerUtils server, SceneCtrl sceneCtrl){
        this.server=server;
        this.sceneCtrl= sceneCtrl;
    }

    public void initialize(){

    }
}
