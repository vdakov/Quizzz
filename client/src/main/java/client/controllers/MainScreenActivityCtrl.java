package client.controllers;

//import client.communication.ServerUtils;
//import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class MainScreenActivityCtrl {



        private Scene scene;
        private Stage stage;
        private Parent root;

        public void enterSoloGame (ActionEvent event) throws IOException {

            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../../scenes/QuestionSceneHowMuch.fxml")));
            stage = (Stage) ( (Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
}
