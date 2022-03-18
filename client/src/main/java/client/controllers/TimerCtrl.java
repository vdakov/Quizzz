package client.controllers;

import client.communication.ServerUtils;
import javafx.animation.KeyValue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import javax.inject.Inject;

public class TimerCtrl {

    private final Integer startTime = 10;
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty(startTime);

    @FXML
    private Label timeLabel;
    @FXML
    private Button button;
    private Timeline timeline;

    @Inject
    public TimerCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    public void initialize() {
        timeLabel.textProperty().bind(timeSeconds.asString());
        timeSeconds.set(startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(startTime + 1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
    }

    public void handleButton(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
        timeline.stop();
        System.out.println("Time took to answer - " + timeSeconds);
    }

//    private void doTime(){
//        Timeline time = new Timeline();
//        time.setCycleCount(Timeline.INDEFINITE);
//        if(time!=null){
//            time.stop();
//        }
//        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent event) {
//                seconds--;
//                label.setText("Countdown: "+seconds.toString());
//                if(seconds<=0){
//                    time.stop();
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setHeaderText("Count down reset to 0");
//                    alert.show();
//                }
//            }
//        });
//        time.getKeyFrames().add(frame);
//        time.playFromStart();
//    }

}
