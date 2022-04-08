package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class SplashCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;


    public SplashCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void initialize(){
        serverField= new TextField();
        serverField.setText("http://localhost:8080");
    }

    @FXML
    private TextField serverField;

    public void go(ActionEvent event){
        Response response = ClientBuilder.newClient(new ClientConfig())
                .target(this.serverField.getText()).path("api/activities/testConnection" )
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(Response.class);

        if(response.getStatus()==200){
            server.setSERVER(this.serverField.getText());
            sceneCtrl.showMainScreenScene();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid Server");
        alert.setContentText("Please enter a valid server!!!");
        alert.show();

    }
}
