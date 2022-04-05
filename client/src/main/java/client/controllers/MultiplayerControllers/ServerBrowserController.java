package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
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

        this.gameTable.getColumns().remove(this.gameIdColumn);
        this.gameTable.getColumns().remove(this.numPlayerColumn);

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        if (gameConfiguration.getUserName() != null) {
            usernameField.setText(gameConfiguration.getUserName());
        }

        this.gameIdColumn = new TableColumn<GameContainer, String>("gameId");
        gameIdColumn.setCellValueFactory(new PropertyValueFactory<GameContainer, String>("gameId"));

        this.numPlayerColumn = new TableColumn<GameContainer, Integer>("numPlayers");
        numPlayerColumn.setCellValueFactory(new PropertyValueFactory<GameContainer, Integer>("numPlayers"));


        this.currentGames = server.listOfCurrentGames("test");

        this.listOfGameIds = new ArrayList<>();
        for (GameContainer game : currentGames) {
            this.listOfGameIds.add(game.getGameId());
        }

        server.updateAvailableRooms(q -> {
            try {
                if (q.getNumPlayers() == 0) {
                    currentGames.remove(q);
                } else {
                    currentGames.add(q);
                }
            } catch (Exception e) {
                System.out.println("An exception occurred when trying to live update room count");
            }
        });

        missingUsername.setText("");


        this.gameTable.getColumns().add(gameIdColumn);
        this.gameTable.getColumns().add(numPlayerColumn);


        gameTable.setRowFactory(event -> {
            TableRow<GameContainer> row = new TableRow<>();

            row.setOnMouseClicked(event1 -> {


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

        server.stopUpdateAvailableRooms();

        this.initialize();
    }

    /**
     * Returns user to main menu
     *
     * @param event the actionevent of the button
     */
    public void mainMenu(ActionEvent event) {
        server.stopUpdateAvailableRooms();
        this.sceneCtrl.showMainScreenScene();
    }

    public void joinRandomWaitingRoom(ActionEvent event) {
        if (!checkUsername(usernameField.getText())) {
            return;
        }

        String username = usernameField.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        gameConfiguration.setUserName(username);
        gameConfiguration.setCurrentQuestionNumber(0);

        String roomId = server.getRandomMultiPlayerRoomId();

        gameConfiguration.setRoomId(roomId);

        if (!server.joinMultiPlayerRoom()) {
            missingUsername.setText("The username is already taken!");
            return;
        }

        server.stopUpdateAvailableRooms();
        this.sceneCtrl.showWaitingRoom();
    }

    /**
     * Allows user to join a game with an ID provided in the text field
     * If an invalid ID is given an alert is shown
     * Currently enters hardcoded username since there is no unique username in multiplayer game implementation
     *
     * @param event
     */
    public void joinWaitingRoom(ActionEvent event) {
        if (!checkUsername(usernameField.getText())) {
            return;
        }

        String username = usernameField.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        gameConfiguration.setUserName(username);
        gameConfiguration.setCurrentQuestionNumber(0);

        String roomId = this.gameIdField.getText();

        if (!this.listOfGameIds.contains(roomId)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid ID");
            alert.setContentText("Please enter a valid game ID!!!");
            alert.show();
            return;
        }

        gameConfiguration.setRoomId(roomId);

        if (!server.joinMultiPlayerRoom()) {
            missingUsername.setText("The username is already taken!");
            return;
        }

        server.stopUpdateAvailableRooms();
        this.sceneCtrl.showWaitingRoom();
    }

    /**
     * Creates a new waiting room and identifies this player as the owner
     *
     * @param event the ActionEvent of the button
     */
    public void createWaitingRoom(ActionEvent event) {
        if (!checkUsername(usernameField.getText())) {
            return;
        }

        String username = usernameField.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        gameConfiguration.setUserName(username);
        gameConfiguration.setCurrentQuestionNumber(0);

        String roomId = server.createNewRoom();
        gameConfiguration.setRoomId(roomId); 

        if (roomId != null) {
            server.stopUpdateAvailableRooms();
            this.sceneCtrl.showWaitingRoom();
        } else {
            // error message
        }
    }

    public boolean checkUsername(String username) {
        if (username.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A username needed");
            alert.setHeaderText("Please write the username you want to play with");
            alert.setContentText("Unfortunately, you cannot play a game without a username");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
