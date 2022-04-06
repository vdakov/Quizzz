package client.controllers;

import client.communication.ServerUtils;
import client.data.GameConfiguration;
import com.google.inject.Inject;
import commons.Leaderboard.LeaderboardEntry;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private Button goBackToMainScreen;
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
        leaderboardTable.setItems(FXCollections.observableList(new ArrayList<>()));

        if (gameConfig.getCurrentQuestionNumber() == 9)
            playAgainButton.setVisible(false);
        else playAgainButton.setVisible(true);

        getLeaderboard();

        server.checkLeaderboardFilled();

        if (gameConfig.getCurrentQuestionNumber() == 9) {
            timeLabel.setVisible(true);
        } else {
            timeLabel.setVisible(false);
        }
    }

    public void getLeaderboard() {
        if (gameConfig.isMultiPlayer()) {
            server.waitForFilledLeaderboard(q -> {
                fillLeaderboard(q);
                startTimer();
            });
            return;
        } else {
            fillLeaderboard(server.getLeaderboard(GameConfiguration.getConfiguration().getRoomId()));
        }
    }

    public void fillLeaderboard(List<LeaderboardEntry> entries) {
        List top10 = entries.subList(0, Integer.min(10, entries.size()));

        if (gameConfig.isMultiPlayer()) {
            for (int i = 0; i < top10.size(); i++) {
                LinkedHashMap e = (LinkedHashMap) top10.get(i);
                LeaderboardEntry entry = new LeaderboardEntry((String) e.get("username"), (String) e.get("roomId"), (int) e.get("score"), (boolean) e.get("singleplayer"));
                entry.setRank(i + 1);
                top10.set(i, entry);
            }
        }

        if (gameConfig.getRoomId() != null) {
            LeaderboardEntry spacer = new LeaderboardEntry("...", "", -1, false); //a spacer where all columns will be "..."
            spacer.setRank(-1);
            top10.add(spacer);
            top10.add(entries.stream() //add the entry with the same roomId and username as the player
                    .filter(e -> e.getRoomId().equals(gameConfig.getRoomId()) && e.getUsername().equals(gameConfig.getUserName()))
                    .collect(Collectors.toList()).get(0));
        }
        leaderboardTable.setItems(FXCollections.observableList(top10));

        if ((gameConfig.getRoomId() == null && entries.size() >= 3)
                || (gameConfig.getRoomId() != null && entries.size() >= 5)) {
            place1.setText(entries.get(0).getUsername());
            place2.setText(entries.get(1).getUsername());
            place3.setText(entries.get(2).getUsername());
        }
    }

    public void exit() {
        sceneCtrl.showMainScreenScene();
    }

    public void playAgain() {
        sceneCtrl.showWaitingRoom();
    }

    /**
     * Method that ends the game and returns the player to the main screen of the app
     * Also, checks whether all player have left the room and if then it changes the room status as finished
     * @throws IOException
     */
    public void goToMainScreen() {
        sceneCtrl.showMainScreenScene();
    }
}
