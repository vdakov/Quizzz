package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.GameContainer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.ArrayList;


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
    private TextField usernameField;
    @FXML
    private Text missingUsername;
    @FXML
    private TextField gameIdField; // the textfield where the player inputs the gameID

    private ObservableList<GameContainer> currentGames; // the datatype for the list that fills the table

    private ArrayList<String> listOfGameIds;

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


        this.currentGames = server.listOfCurrentGames("test");

        this.listOfGameIds = new ArrayList<>();
        for (GameContainer game : currentGames) {
            this.listOfGameIds.add(game.getGameId());
        }

        missingUsername.setText("");

        this.gameTable.getColumns().add(gameIdColumn);
        this.gameTable.getColumns().add(numPlayerColumn);

        gameTable.setRowFactory(event -> {
            TableRow<GameContainer> row = new TableRow<>();

            row.setOnMouseEntered(event1 -> {


                if (row.isSelected()) {

                    if (row.getItem().getGameId() != null) {
                        String Id = row.getItem().getGameId();
                        this.gameIdField.setText(Id);

                    }


                }


            });

            return row;
        });
        this.gameTable.setItems(currentGames);


    }

    /**
     * Refresh method to update table on each player's whim;
     * Deletes the previous column values and initializes them again
     *
     * @param event the refresh button Action event
     */
    public void refresh(ActionEvent event) {
        this.gameTable.getColumns().remove(this.gameIdColumn);
        this.gameTable.getColumns().remove(this.numPlayerColumn);
        this.initialize();
    }

    /**
     * Returns user to main menu
     *
     * @param event the actionevent of the button
     */
    public void mainMenu(ActionEvent event) {
        this.sceneCtrl.showMainScreenScene();
    }

    /**
     * Allows user to join a game with an ID provided in the text field
     * If an invalid ID is given an alert is shown
     * Currently enters hardcoded username since there is no unique username in multiplayer game implementation
     *
     * @param event
     */
    public void joinRandomWaitingRoom(ActionEvent event) {
        //Checking if username field was filled in
        String playerName = usernameField.getText();
        if (playerName == "") {
            missingUsername.setText("Enter username!");
            return;
        }
        // we will connect to the initialised random room
        String roomId = server.getRandomMultiPlayerRoomId(playerName);


        if (!server.joinMultiPlayerRoom(playerName, roomId)) {
            missingUsername.setText("The username is already taken!");
            return;
        }
        this.sceneCtrl.showWaitingRoom(true, roomId, playerName);


    }

    public void joinWaitingRoom(ActionEvent event) {
        //Checking if username field was filled in
        String playerName = usernameField.getText();
        if (playerName == "") {
            missingUsername.setText("Enter username!");
            return;
        }
        String gameId = this.gameIdField.getText();

        if (!this.listOfGameIds.contains(gameId)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid ID");
            alert.setContentText("Please enter a valid game ID!!!");
            alert.show();
        } else {
            if (!server.joinMultiPlayerRoom(playerName, gameId)) {
                missingUsername.setText("The username is already taken!");
                return;
            }
            this.sceneCtrl.showWaitingRoom(false, gameId, playerName);
        }
    }

    /**
     * Creates a new waiting room and identifies this player as the owner
     *
     * @param event the ActionEvent of the button
     */
    public void createWaitingRoom(ActionEvent event) {
        //Checking if username field was filled in
        String playerName = usernameField.getText();
        if (playerName == "") {
            missingUsername.setText("Enter username!");
            return;
        }
        String gameId = server.createNewMultiPlayerRoom(playerName);
        this.sceneCtrl.showWaitingRoom(true, gameId, "cata");
    }

}
