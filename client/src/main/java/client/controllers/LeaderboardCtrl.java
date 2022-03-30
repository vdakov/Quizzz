package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
import com.google.inject.Inject;
import commons.Leaderboard.LeaderboardEntry;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


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
    @FXML
    private Button playAgainButton;
    @FXML
    private ProgressBar progressBarTime;
    @FXML
    private Label timeLabel;

    private ServerUtils server;
    private SceneCtrl sceneCtrl;
    private GameConfiguration gameConfig;

    @Inject
    public LeaderboardCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        gameConfig = GameConfiguration.getConfiguration();
    }

    public void initialize() {
        //set which field each column contains
        placeCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getRank() == -1 ? "..." : q.getValue().getRank() + ""));
        nameCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getUsername()));
        pointsCol.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getScore() == -1 ? "..." : q.getValue().getScore() + ""));
    }


    private Timeline timeline;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(10);

    public void startTimer() {
        progressBarTime.progressProperty().bind(Bindings.divide(timeSeconds, 10));

        timeLabel.textProperty().bind(timeSeconds.asString());    //bind the progressbar value to the seconds left
        timeSeconds.set(10);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(11),      //the timeLine handles an animation which lasts start + 1 seconds
                        new KeyValue(timeSeconds, 0)));    //animation finishes when timeSeconds comes to 0
        timeline.setOnFinished(event -> {
            try {
                timeline.stop();
                sceneCtrl.showNextQuestion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });       //proceeds to the next question if no answer was given in 10 sec
        timeline.playFromStart();                                 //start the animation
    }

    public void refresh() {
        playAgainButton.setVisible(gameConfig.isSinglePlayer() ? false : true);

        getLeaderboard();

        if (gameConfig.getCurrentQuestionNumber() == 10) {
            progressBarTime.setVisible(true);
            timeLabel.setVisible(true);
            startTimer();
        } else {
            progressBarTime.setVisible(false);
            timeLabel.setVisible(false);
        }
    }

    public void getLeaderboard() {
        List<LeaderboardEntry> leaderboardEntries = server.getLeaderboard(GameConfiguration.getConfiguration().getRoomId());
        List<LeaderboardEntry> top10 = leaderboardEntries.subList(0, Integer.min(10, leaderboardEntries.size()));

        if (gameConfig.getRoomId() != null) {
            LeaderboardEntry spacer = new LeaderboardEntry("...", "", -1, false); //a spacer where all columns will be "..."
            spacer.setRank(-1);
            top10.add(spacer);
            top10.add(leaderboardEntries.stream() //add the entry with the same roomId and username as the player
                    .filter(e -> e.getRoomId().equals(gameConfig.getRoomId()) && e.getUsername().equals(gameConfig.getUserName()))
                    .collect(Collectors.toList()).get(0));
        }
        leaderboardTable.setItems(FXCollections.observableList(top10));

        if (leaderboardEntries.size() >= 3) {
            place1.setText(leaderboardEntries.get(0).getUsername());
            place2.setText(leaderboardEntries.get(1).getUsername());
            place3.setText(leaderboardEntries.get(2).getUsername());
        }
    }

    public void exit() {
        sceneCtrl.showMainScreenScene();
    }

    public void playAgain() {
        sceneCtrl.showWaitingRoom();
    }
}
