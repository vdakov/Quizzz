package client.scenes;

import client.logic.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class AddActivityCtrl {
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    @FXML
    private TextField id;

    @FXML
    private TextField title;

    @FXML
    private TextField source;

    @FXML
    private TextField consumption;

    @Inject
    public AddActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;

    }

    public void cancel() {
        clearFields();
        sceneCtrl.showActivitiesOverview();
    }

    public void ok() {
        try {
            server.addActivity(getActivity());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        sceneCtrl.showActivitiesOverview();
    }

    private Activity getActivity() {
        return new Activity(title.getText(), source.getText(), Integer.parseInt(consumption.getText()));
    }

    private void clearFields() {
        id.clear();
        title.clear();
        source.clear();
        consumption.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }
}
