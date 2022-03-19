package client.controllers;

import client.communication.ServerUtils;

import commons.Leaderboard.LeaderboardEntry;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.util.List;

import java.util.List;


public class SingleplayerLeaderboardCtrl {
    @FXML
    private Text place1;
    @FXML
    private Text place2;
    @FXML
    private Text place3;
    @FXML
    private TableView leaderboardTable;

    private ServerUtils server;
    private SceneCtrl sceneCtrl;

    @Inject
    public SingleplayerLeaderboardCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void initialize() {
        List<LeaderboardEntry> leaderboardEntries = server.getSingleplayerLeaderboard();
        leaderboardTable.setItems(FXCollections.observableList(leaderboardEntries));
    }

    public void exit() {
        sceneCtrl.showMainScreenScene();
    }
}
