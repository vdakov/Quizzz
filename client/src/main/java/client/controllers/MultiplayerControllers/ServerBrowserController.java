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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ServerBrowserController {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;


    @FXML
    private TableView<GameContainer> gameTable;
    @FXML
    private TableColumn<GameContainer, String> gameIdColumn;
    @FXML
    private TableColumn<GameContainer, Integer> numPlayerColumn;



    @FXML
    private Button mainMenuButton;
    @FXML
    private Button joinWaitingRoom;
    @FXML
    private Button createWaitingRoom;
    @FXML
    private TextField gameIdField;
    
    private ObservableList<GameContainer> currentGames;

    @Inject
    public ServerBrowserController(ServerUtils server, SceneCtrl sceneCtrl){
        this.server=server;
        this.sceneCtrl= sceneCtrl;


    }

    public void initialize(){


        this.gameIdColumn= new TableColumn<GameContainer, String>("gameId");
        gameIdColumn.setCellValueFactory(new PropertyValueFactory<GameContainer, String>("gameId"));

        this.numPlayerColumn=new TableColumn<GameContainer, Integer>("numPlayers");
       numPlayerColumn.setCellValueFactory(new PropertyValueFactory<GameContainer, Integer>("numPlayers"));



        this.currentGames= server.listOfCurrentGames();

        this.gameTable.getColumns().add(gameIdColumn);
        this.gameTable.getColumns().add(numPlayerColumn);
        this.gameTable.setItems(currentGames);


    }

    public void refresh(ActionEvent event){
        this.gameTable.getColumns().remove(this.gameIdColumn);
        this.gameTable.getColumns().remove(this.numPlayerColumn);
        this.initialize();
    }

    public void mainMenu(ActionEvent event){
        this.sceneCtrl.showMainScreenScene();
    }

    public void joinWaitingRoom(ActionEvent event){
        String gameId=this.gameIdField.getText();
        if(!server.listOfAllGameIds().contains(gameId)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid ID");
            alert.setContentText("Please enter a valid game ID!!!");
        }else {
            server.joinExistingMultiplayerGame("johny", gameId);
            this.sceneCtrl.showWaitingRoom(false, gameId, "johny");
        }

    }

    public void createWaitingRoom(ActionEvent event){
        String gameId= server.createNewMultiplayerGame("cata");
        this.sceneCtrl.showWaitingRoom(true,gameId, "cata");
    }
}
