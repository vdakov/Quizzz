package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.glassfish.jersey.client.ClientConfig;

import javax.inject.Inject;
import java.io.IOException;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class MainScreenActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    @FXML
    private TextField userName;
    @FXML
    private TextField serverField;

    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    public void initialise() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        if (gameConfiguration.getUserName() != null) {
            userName.setText(gameConfiguration.getUserName());
        }
        gameConfiguration.setRoomId(null);
        gameConfiguration.setGameTypeUndefined();
    }

    public void enterSoloGame() throws IOException {
        if (!checkUsername()) {
            return;
        }

        String username = userName.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        gameConfiguration.setUserName(username);
        gameConfiguration.setCurrentQuestionNumber(0);
        gameConfiguration.setGameTypeSingleplayer();

        String roomId = server.createNewRoom();
        gameConfiguration.setRoomId(roomId);

        if (roomId != null) {
            if (server.startRoom() == true) {
                sceneCtrl.showNextQuestion();
                return;
            }
        }
    }

    public void enterServerBrowser() throws IOException {
        if (!checkUsername()) {
            return;
        }

        String username = userName.getText();

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        gameConfiguration.setUserName(username);
        gameConfiguration.setGameTypeMultiPlayer();

        this.sceneCtrl.showServerBrowser();
    }

    public boolean checkUsername() {
        if (userName.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("A username needed");
            alert.setHeaderText("Please write the username you want to play with");
            alert.setContentText("Unfortunately, you cannot play a game");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void showLeaderboard() {
        GameConfiguration.getConfiguration().setGameTypeSingleplayer(); //make sure you get to see the singleplayer version of the leaderboard
        sceneCtrl.showLeaderboard();
    }

    public void enterAdminInterface() {
        sceneCtrl.showOverviewActionScene();
    }

    public void go(ActionEvent event){
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(this.serverField.getText()).path("api/activities/testConnection" )
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(Response.class);

        System.out.println(response.getStatus());

        if(response.getStatus()==200){
            server.setSERVER(this.serverField.getText());
            //sceneCtrl.showMainScreenScene();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid Server");
        alert.setContentText("Please enter a valid server!!!");
        alert.show();

    }


}
