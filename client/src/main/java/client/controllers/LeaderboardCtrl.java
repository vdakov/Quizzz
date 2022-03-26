package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
import com.google.inject.Inject;
import commons.Leaderboard.LeaderboardEntry;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.util.List;


public class LeaderboardCtrl {
    @FXML
    private Text place1;
    @FXML
    private Text place2;
    @FXML
    private Text place3;
    @FXML
    private TableView leaderboardTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> placeCol;
    @FXML
    private TableColumn<LeaderboardEntry, String> nameCol;
    @FXML
    private TableColumn<LeaderboardEntry, String> pointsCol;

    private ServerUtils server;
    private SceneCtrl sceneCtrl;

    @Inject
    public LeaderboardCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void initialize() {
        //set which field each column contains
        placeCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getRank() + ""));
        nameCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getUsername()));
        pointsCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getScore() + ""));

        refresh();
    }

    public void refresh() {
        List<LeaderboardEntry> leaderboardEntries = server.getLeaderboard(GameConfiguration.getConfiguration().getRoomId());
        leaderboardTable.setItems(FXCollections.observableList(leaderboardEntries));
    }

    public void exit() {
        sceneCtrl.showMainScreenScene();
    }
}
