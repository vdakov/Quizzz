package client.controllers.AdminInterface;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Actions.Action;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

public class EditActionActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    private String id;

    @FXML
    private TextField title;

    @FXML
    private TextField source;

    @FXML
    private TextField consumption;

    @Inject
    public EditActionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    public void initialize() {

    }

    public void cancel() {
        clearFields();
        sceneCtrl.showOverviewActionScene();
    }

    public void ok() {
        try {
            server.editActivity(id, getActivity());
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }

        clearFields();
        //sceneCtrl.showOverviewActionsScene();
    }

    private Action getActivity() {
        try {
            consumption.getText();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }
        return new Action(null, null, title.getText(), Integer.parseInt(consumption.getText()), source.getText());
    }

    private void clearFields() {
        title.clear();
        source.clear();
        consumption.clear();
    }

    public void showOriginalAction() {
        Action editingAction = server.getActivityById(id);
        title.setText(editingAction.getTitle());
        source.setText(editingAction.getSource());
        consumption.setText(String.valueOf(editingAction.getConsumption()));
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
