package client.controllers.MultiplayerControllers;


import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;


public class WaitingRoomController {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;


    private int numberOfPlayers; //stores the number of currentplayers
    private String gameId; //stores the gameID of the current room
    private boolean owner; //stores whether the current user is the owner or not
    private String userName; //stores the current user's username

    @FXML
    private Text playerLabel; // displays the amount of players
    @FXML
    private Button startButton; // the button to start the game- only accessible to the owner
    @FXML
    private Text ownerText; // the text displaying whether you are the owner of the room
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
       // this.server.removePlayer(this.userName, this.gameId);
        if (owner) {
            this.owner = false;
        }
        this.sceneCtrl.showServerBrowser();
    }


    //GAME OWNER FUNCTIONALITY IS CURRENTLY NOT IMPLEMENTED_ WE NEED TO CHECK WHETHER WE NEED IT

    /**
     * Adjusts all of the text field to the current user
     *
     * @param owner    boolean value for whether this players is the current owner of the game
     * @param gameId   stores the gameId
     * @param userName stores the current player's username
     */
    public void initialize(boolean owner, String gameId, String userName) {
        this.userName = userName;
        this.gameId = gameId;
        this.ownerText.setText("");
        this.startButton.setDisable(true);
        if (owner) {
            this.owner = owner; //sets this if the owner leaves
            this.startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TO BE IMPLEMENTED- START OF GAME
                }
            });
            this.ownerText.setText("YOU ARE THE OWNER OF THIS ROOM");
            this.startButton.setDisable(false);
        }
        this.gameID.setText("GAME ID: " + gameId);

        //this.playerLabel.setText("There are currently " +
        //        server.getNumPlayersInGame(gameId) + " players in the room");
    }

    /**
     * Refresh method to update the number of current players
     *
     * @param event the event listener of the button
     */
    public void refresh(ActionEvent event) {
        this.initialize(this.owner, this.gameId, this.userName);
    }


}
