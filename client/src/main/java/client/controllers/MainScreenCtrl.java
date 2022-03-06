package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;
import commons.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class MainScreenCtrl {


        private final ServerUtils server;
        private final SceneCtrl sceneCtrl;

    //Constructor for the Main Screen Controller
    @Inject
    public MainScreenCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }


        @FXML
        Button enter;

        private Scene scene;
        private Stage stage;
        private Parent root;

        public void enterSoloGame (ActionEvent event) throws IOException{

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("jetbrains://idea/navigate/reference?project=quizzzz&path=scenes/QuestionScene.fxml")));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }
}
