package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.GameContainer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class ServerBrowserController {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;


    @FXML
    private TableView<GameContainer> gameTable; // the table that stores the gameID and the num of Players
    @FXML
    private TableColumn<GameContainer, String> gameIdColumn;
    @FXML
    private TableColumn<GameContainer, Integer> numPlayerColumn;
    @FXML
    private TextField gameIdField; // the textfield where the player inputs the gameID

    private ObservableList<GameContainer> currentGames; // the datatype for the list that fills the table

    @Inject
    public ServerBrowserController(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    /**
     * Initialize method of the server browser screen
     * Sets the table names and values
     */
    public void initialize() {


        this.gameIdColumn = new TableColumn<GameContainer, String>("gameId");
        gameIdColumn.setCellValueFactory(new PropertyValueFactory<GameContainer, String>("gameId"));

        this.numPlayerColumn = new TableColumn<GameContainer, Integer>("numPlayers");
        numPlayerColumn.setCellValueFactory(new PropertyValueFactory<GameContainer, Integer>("numPlayers"));


        //this.currentGames = server.listOfCurrentGames();

        this.gameTable.getColumns().add(gameIdColumn);
        this.gameTable.getColumns().add(numPlayerColumn);
        this.gameTable.setItems(currentGames);


    }

    /**
     * Refresh method to update table on each player's whim;
     * Deletes the previous column values and initializes them again
     * @param event the refresh button Action event
     */
    public void refresh(ActionEvent event) {
        this.gameTable.getColumns().remove(this.gameIdColumn);
        this.gameTable.getColumns().remove(this.numPlayerColumn);
        this.initialize();
    }

    /**
     * Returns user to main menu
     * @param event the actionevent of the button
     */
    public void mainMenu(ActionEvent event) {
        this.sceneCtrl.showMainScreenScene();
    }

    /**
     * Allows user to join a game with an ID provided in the text field
     * If an invalid ID is given an alert is shown
     * Currently enters hardcoded username since there is no unique username in multiplayer game implementation
     * @param event
     */
    public void joinWaitingRoom(ActionEvent event) {
        String gameId = this.gameIdField.getText();
//        if (!server.listOfAllGameIds().contains(gameId)) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Warning");
//            alert.setHeaderText("Invalid ID");
//            alert.setContentText("Please enter a valid game ID!!!");
//            alert.show();
//        } else {
//            server.joinExistingMultiplayerGame("johny", gameId);
//            this.sceneCtrl.showWaitingRoom(false, gameId, "johny");
//        }

    }

    /**
     * Creates a new waiting room and identifies this player as the owner
     * @param event the ActionEvent of the button
     */
    public void createWaitingRoom(ActionEvent event) {
        //String gameId = server.createNewMultiplayerGame("cata");
        //this.sceneCtrl.showWaitingRoom(true, gameId, "cata");
    }
}
