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
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class EditActionActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    private String id;
    private String folder;

    @FXML
    private TextField title;

    @FXML
    private TextField source;

    @FXML
    private TextField consumption;

    @FXML
    private ImageView currentImage;

    @FXML
    private ImageView newImage;

    @FXML
    private TextField imageNameField;

    private String base64Image;

    @Inject
    public EditActionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    public void initialize(Action editingAction) throws IOException {
        System.out.println(editingAction.getImagePath());
        ByteArrayInputStream bis = new ByteArrayInputStream(server.getQuestionImage(editingAction.getImagePath()));
        BufferedImage bImage = ImageIO.read(bis);
        this.currentImage.setImage(SwingFXUtils.toFXImage(bImage, null));

        this.id = editingAction.getId();
        this.folder = editingAction.getImagePath().substring(0, 2);
        System.out.println(id);
        System.out.println(this.id);
        title.setText(editingAction.getTitle());
        source.setText(editingAction.getSource());
        this.imageNameField.setText(editingAction.getImagePath());
        consumption.setText(String.valueOf(editingAction.getConsumption()));
    }

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
            server.editActivity(this.id, getActivity());
            server.sendImageEdit(this.base64Image, this.imageNameField.getText(), this.folder);
        } catch (WebApplicationException e) {

            var alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        cancel();
    }

    private Action getActivity() {
        try {
            consumption.getText();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
        }
        return new Action(this.id, this.folder + "/" + this.imageNameField.getText(),
                title.getText(), Long.parseLong(this.consumption.getText()), source.getText());
    }

    private void clearFields() {
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
            this.newImage.setImage(SwingFXUtils.toFXImage(ImageIO.read(chosenImage), null));

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Invalid File");
            alert.setContentText("Please choose a valid file!!!");
            alert.showAndWait();
        }
    }

}
