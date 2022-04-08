package client.controllers;

import client.communication.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SplashCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;


    public SplashCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void initialize() {
        serverField = new TextField();
        serverField.setText("http://localhost:8080");
    }

    @FXML
    private TextField serverField;
}
