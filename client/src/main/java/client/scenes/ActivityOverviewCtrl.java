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
package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import commons.Quote;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ActivityOverviewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    private ObservableList<Activity> data;

    @FXML
    private TableView<Activity> table;
    @FXML
    private TableColumn<Activity, String> colId;
    @FXML
    private TableColumn<Activity, String> colTitle;
    @FXML
    private TableColumn<Activity, String> colConsumption;
    @FXML
    private TableColumn<Activity, String> colSource;

    @Inject
    public ActivityOverviewCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getId()));
        colTitle.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getTitle()));
        colConsumption.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getConsumption() + ""));
        colSource.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getSource()));
    }

    public void addActivity() {
        mainCtrl.showAddActivity();
    }

    public void refresh() {
        var activities = server.getActivities();
        data = FXCollections.observableList(activities);
        table.setItems(data);
    }

    public void toQuotes() {
        mainCtrl.showQuotesOverview();
    }
}