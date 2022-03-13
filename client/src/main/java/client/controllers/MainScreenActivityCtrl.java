package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;

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
