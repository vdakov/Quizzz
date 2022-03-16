package client.controllers;

import javafx.event.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TimerCtrl extends Application {

    private final Integer startTime = 10;
    private Integer seconds = startTime;
    private Label label;

    @Override
    public void start(Stage st) throws Exception{
        label = new Label();
        label.setTextFill(Color.BLACK);
        label.setFont(Font.font(20));

        HBox layout = new HBox(5);
        layout.getChildren().add(label);
        doTime();

        st.setTitle("Count down Timer");
        st.setScene(new Scene(layout, 400, 50));
        st.show();
    }

    private void doTime(){
        Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        if(time!=null){
            time.stop();
        }
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                label.setText("Countdown: "+seconds.toString());
                if(seconds<=0){
                    time.stop();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Count down reset to 0");
                    alert.show();
                }
            }
        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
