package client.controllers;

import client.communication.ServerUtils;
import javafx.animation.KeyValue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.inject.Inject;
import java.util.Locale;

public class TimerCtrl{

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
    }

    public void handleButton(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds.set(startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(startTime+1),
                        new KeyValue(timeSeconds, 0)));
        timeline.playFromStart();
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
