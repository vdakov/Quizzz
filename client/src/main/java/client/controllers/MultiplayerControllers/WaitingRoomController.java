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

    private int numberOfPlayers;
    private String gameId;
    private boolean owner;
    private String userName;

    @FXML
    private Text playerLabel;
    @FXML
    private Button startButton;
    @FXML
    private Text ownerText;
    @FXML
    private Text gameID;

    @Inject
    public WaitingRoomController(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void initialize() {
        this.ownerText.setText("");
    }

    public void goBackToServerBrowser(ActionEvent event) {
        server.alert(this.userName);
        this.server.removePlayer(this.userName, this.gameId);
        this.sceneCtrl.showServerBrowser();
    }

    public void adjustText(boolean owner, String gameId, String userName) {
        this.userName = userName;
        this.gameId = gameId;
        this.ownerText.setText("");
        if (owner) {
            this.owner = owner; //sets this if the owner leaves
            this.startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //TO BE IMPLEMENTED- START OF GAME
                }
            });
            this.ownerText.setText("YOU ARE THE OWNER OF THIS ROOM");
        } else {
            this.startButton.setDisable(true);
        }
        this.gameID.setText("GAME ID: " + gameId);

        this.playerLabel.setText("There are currently " +
                server.getNumPlayersInGame(gameId) + " players in the room");
    }


}
