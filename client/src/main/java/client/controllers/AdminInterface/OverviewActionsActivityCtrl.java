/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.controllers.AdminInterface;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Actions.Action;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class OverviewActionsActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    private ObservableList<Action> data;

    @FXML
    private TableView<Action> table;
    @FXML
    private TableColumn<Action, String> colId;
    @FXML
    private TableColumn<Action, String> colTitle;
    @FXML
    private TableColumn<Action, String> colConsumption;
    @FXML
    private TableColumn<Action, String> colSource;
    @FXML
    private Button refreshButton;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField deletingID;
    @FXML
    private TextField editingID;

    /**
     * Constructor for the OverviewActionsActivityCtrl
     * @param server the server
     * @param mainCtrl the main controller
     */
    @Inject
    public OverviewActionsActivityCtrl(ServerUtils server, SceneCtrl mainCtrl) {
        this.server = server;
        this.sceneCtrl = mainCtrl;
    }

    //    public void initialize(URL location, ResourceBundle resources) {
    public void initialize() {
        colId.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getId() + ""));
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        colConsumption.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getConsumption() + ""));
        colSource.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getSource()));

        table.setRowFactory(event -> {
            TableRow<Action> row = new TableRow<>();

            row.setOnMouseClicked(event1 -> {


                if (row.isSelected()) {

                    if (row.getItem().getId() != null) {
                        String Id = row.getItem().getId();
                        this.deletingID.setText(Id);
                        this.editingID.setText(Id);

                    }


                }
            });

            return row;
        });
        this.refresh();
    }

    /**
     * @param event
     */
    public void addActivity(ActionEvent event) {
        sceneCtrl.showAddActionScene();
        refresh();
    }

    /**
     * Deletes an activity
     * @param event what activates the activity deletion
     */
    public void deleteActivity(ActionEvent event) {
        String id = deletingID.getText();
        try {
            Action deletingAction = server.getActivityById(id);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Delete Activity");
            alert.setHeaderText("Are you sure to delete activity");
            alert.setContentText("ID: " + deletingAction.getId() + "\nTitle: " + deletingAction.getTitle() + "\nConsumption: " + deletingAction.getConsumption());
            alert.showAndWait();
            server.deleteActivity(id);
            refresh();
        } catch (Exception e) {
            System.out.println("The given ID is not founded");
        }
    }

    /**
     * Edits an activity
     * @param event what activates the edit of the activity
     * @throws IOException
     */
    public void editActivity(ActionEvent event) throws IOException {
        String id = editingID.getText();
        try {
            Action editingAction = server.getActivityById(id);
            sceneCtrl.showEditActionScene(editingAction);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("ID NOT FOUND");
            alert.setHeaderText("ID NOT FOUND");
            alert.setContentText("ID COULD NOT BE FOUND, PLEASE ENTER VALID ID !");
            alert.showAndWait();
        }
        refresh();
    }

    /**
     * Refreshes the page
     */
    public void refresh() {
        var activities = server.getActivities();
        data = FXCollections.observableList(activities);
        table.setItems(data);
        colId.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(colId);
        table.sort();
    }

    /**
     * Goes back to the main screen
     * @throws IOException
     */
    public void goToMainScreen() throws IOException {
        sceneCtrl.showMainScreenScene();
    }

    /**
     * Restore the activity bank
     * @param event the action that triggers the restoration of the activity bank
     */
    public void restoreActivityBank(ActionEvent event) {
        server.restoreActivityBank();
        refresh();
    }

}