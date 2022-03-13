package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainScreenActivityCtrl {


    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    //Constructor for the Main Screen Controller
    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void enterSoloGame() {
        sceneCtrl.showQuestionScene();
    }
}
