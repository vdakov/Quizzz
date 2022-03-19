package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.GameContainer;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ServerBrowserController {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;


    @FXML
    private TableView<GameContainer> gameTable;
    @FXML
    private TableColumn<GameContainer, String> gameId;
    @FXML
    private TableColumn<GameContainer, Integer> numPlayers;


    @FXML
    private Button mainMenuButton;
    @FXML
    private Button joinWaitingRoom;
    @FXML
    private Button createWaitingRoom;
    
    private ObservableList<GameContainer> currentGames;

    @Inject
    public ServerBrowserController(ServerUtils server, SceneCtrl sceneCtrl){
        this.server=server;
        this.sceneCtrl= sceneCtrl;

        this.gameId= new TableColumn<>("ID");
        gameId.setCellValueFactory(new PropertyValueFactory<GameContainer, String>("gameId"));

        this.numPlayers=new TableColumn<>("Players");
        numPlayers.setCellValueFactory(new PropertyValueFactory<GameContainer, Integer>("numPlayers"));
        
        this.currentGames= FXCollections.observableArrayList();

        this.gameTable= new TableView<>();
        this.gameTable.setItems(currentGames);
    }

    public void initialize(){

    }

    public void mainMenu(ActionEvent event){
        this.sceneCtrl.showMainScreenScene();
    }

    public void joinWaitingRoom(ActionEvent event){
        this.sceneCtrl.showWaitingRoom();

    }

    public void createWaitingRoom(ActionEvent event){
        this.sceneCtrl.showWaitingRoom();
    }
}
