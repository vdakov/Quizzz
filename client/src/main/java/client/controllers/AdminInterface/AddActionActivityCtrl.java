package client.controllers.AdminInterface;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Actions.Action;
import jakarta.ws.rs.WebApplicationException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AddActionActivityCtrl {

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

    @FXML
    private TextField imageNameField;

    @FXML
    private ImageView image;

    private String base64Image;


    /**
     * Constructor for the AddActionAcvityCtrl
     * @param server the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public AddActionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;

    }

    /**
     * Initializes the controller
     */
    public void initialize() {

    }

    /**
     * Clears all the fields
     */
    public void cancel() {
        clearFields();
        sceneCtrl.showOverviewActionScene();
    }


    public void ok() {
        if (this.title == null || this.consumption == null || this.imageNameField.getText() == null || this.base64Image == null || this.source == null) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all fields!!!!");
            alert.showAndWait();

            return;
        }

        try {
            server.addActivity(getActivity());
            server.sendImage(this.base64Image, this.imageNameField.getText());

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

    /**
     * Gets the activity
     * @return an Action initiliazed with the fields of the activity
     */
    private Action getActivity() {
        try {
            consumption.getText();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }
        return new Action("contribution-" + title.getText(), "US/" + this.imageNameField.getText(), title.getText(), Long.parseLong(this.consumption.getText()), source.getText());
    }

    /**
     * Clears a field
     */
    private void clearFields() {
        title.clear();
        source.clear();
        consumption.clear();
        imageNameField.clear();
        this.image.setImage(null);
    }

    /**
     * Chooses the file
     * @param event the way in which the file is chosen
     * @throws IOException
     */
    public void chooseFile(ActionEvent event) throws IOException {
        FileChooser imageChooser = new FileChooser();
        imageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Images", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG Images", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG Images", "*.png")

        );
        File chosenImage = imageChooser.showOpenDialog(null);

        if (chosenImage != null) {
            String filename = chosenImage.getName();
            this.imageNameField.setText(filename);
            this.base64Image = Base64.encodeBase64String(FileUtils.readFileToByteArray(chosenImage));
            this.image.setImage(SwingFXUtils.toFXImage(ImageIO.read(chosenImage), null));

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid File");
            alert.setContentText("Please choose a valid file!!!");
            alert.showAndWait();
        }
    }

}
