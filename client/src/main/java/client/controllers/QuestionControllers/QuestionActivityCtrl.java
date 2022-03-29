package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;



import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuestionActivityCtrl {
    // constructor needed variables
    protected final ServerUtils server;
    protected final SceneCtrl sceneCtrl;
    protected final GameConfiguration gameConfig = GameConfiguration.getConfiguration();
    protected final double startTime = 10;
    protected int addedPointsInt;
    protected String userAnswer;
    protected boolean answered;
    @FXML
    protected Label timeLabel;
    @FXML
    protected ProgressBar progressBarTime;
    // final needed labels for questions
    @FXML
    protected Label labelGoBack;
    @FXML
    protected Label questionStatement;
    @FXML
    protected ImageView questionStatementImage;
    @FXML
    protected Label firstOptionText;
    @FXML
    protected ImageView firstOptionImage;
    @FXML
    protected Label secondOptionText;
    @FXML
    protected ImageView secondOptionImage;
    @FXML
    protected ImageView image;
    @FXML
    protected Label thirdOptionText;
    @FXML
    protected ImageView thirdOptionImage;
    // current labels
    @FXML
    protected Label sampleQuestion;
    @FXML
    protected Button goToMainScreen;
    @FXML
    protected Label points;
    @FXML
    protected Label addedPoints;
    @FXML
    protected Label questionNumberLabel;
    @FXML
    protected String correctAnswer;
    @FXML
    protected ImageView emoji1;
    @FXML
    protected ImageView emoji2;
    @FXML
    protected ImageView emoji3;
    @FXML
    protected ImageView emoji4;
    @FXML
    protected ImageView emoji5;
    @FXML
    protected TableView tableview;
    @FXML
    protected TableColumn<String, String> playersActivity;


    protected IntegerProperty timeSeconds =
            new SimpleIntegerProperty((int) startTime);
    protected Timeline timeline;

    @Inject
    public QuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) throws ExecutionException, InterruptedException {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }


    /**
     * Initialises all the colors for the current scene
     */
    public void initialize() throws IOException {
        firstOptionText.setBorder(Border.EMPTY);
        secondOptionText.setBorder(Border.EMPTY);
        thirdOptionText.setBorder(Border.EMPTY);

        answered = false;

        addedPoints.setText(" ");
        addedPointsInt = 0;

        if (!gameConfig.isSinglePlayer())
        {
            tableview.setVisible(true);
            playersActivity.setCellValueFactory(q -> new SimpleStringProperty(q.getValue()));
        }
        else
        {
            tableview.setVisible(false);
        }

        server.registerForMessages("/topic/emojis", q -> {
            refresh(q);
        });

    }


    public void answerQuestion(MouseEvent event) {
        // answers the question
        if (answered) {
            return;
        }

        Label current = (Label) event.getSource();
        userAnswer = current.getText();
        answered = true;

        System.out.println("Tried to update answer");
        server.updateScore(userAnswer);

        answerUpdate();
        pointsUpdate();

        //blocks the possibility to answer anymore
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown

        //check whether the user's answer is correct and update the boolean value

        firstOptionText.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        secondOptionText.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        thirdOptionText.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));

        if (getCorrectAnswer().equals(firstOptionText.getText())) {
            firstOptionText.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        } else if (getCorrectAnswer().equals(secondOptionText.getText())) {
            secondOptionText.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        } else {
            thirdOptionText.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(50), BorderStroke.THICK)));
        }


    }

    public void pointsUpdate() {
        // after the time ends the amount of won points is calculated and then shown to the player

        addedPointsInt = 0;
        if (userAnswer.equals(getCorrectAnswer())) {
            addedPointsInt = 500;
        }
        addedPoints.setText("+" + String.valueOf(addedPointsInt));

//        FadeTransition fadeout = new FadeTransition(Duration.seconds(1), addedPoints);
//        fadeout.setFromValue(1);
//        fadeout.setToValue(0);
//        fadeout.play();
//
//        //after some effect
//        pointsInt += addedPointsInt;
//        addedPointsInt = 0;
//        addedPoints.setText(null);
//        points.setText(String.valueOf(pointsInt));
    }

    public void startTimer() {
        progressBarTime.progressProperty().bind(Bindings.divide(timeSeconds, startTime));

        timeLabel.textProperty().bind(timeSeconds.asString());    //bind the progressbar value to the seconds left
        timeSeconds.set((int) startTime);
        timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(startTime + 1),      //the timeLine handles an animation which lasts start + 1 seconds
                        new KeyValue(timeSeconds, 0)));    //animation finishes when timeSeconds comes to 0
        timeline.setOnFinished(event -> {
            try {
                displayNextQuestion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });       //proceeds to the next question if no answer was given in 10 sec
        timeline.playFromStart();                                 //start the animation
    }

    public void handleTimer(MouseEvent event) {
        if (timeline != null) {
            timeline.stop();        //if timeline exists stop it when any answer button is pressed
        }
        timeline.stop();
        System.out.println("Time took to answer - " + timeSeconds);
    }

    public void displayNextQuestion() throws IOException {
        timeline.stop();
        if (gameConfig.getCurrentQuestionNumber() >= 19) finishGame();
        else sceneCtrl.showNextQuestion();
    }

    public void finishGame() {
        server.addLeaderboardEntry(gameConfig.getUserName(), gameConfig.getRoomId(), Integer.parseInt(server.getScore()));
        sceneCtrl.showLeaderboard();
    }

    public void goToMainScreen() throws IOException {
        timeline.stop();
        sceneCtrl.showMainScreenScene();
    }

    public String getCorrectAnswer() {
        return server.getAnswer();
    }

    public int getPointsInt() {
        return Integer.parseInt(server.getScore());
    }

    public int getQuestionNumber() {
        return gameConfig.getCurrentQuestionNumber();
    }


    public void emoji1Display(MouseEvent event)
    {
       server.send("/topic/emojis", "1");
       // tableview.setBackground(new Background(new BackgroundFill(Color.WHITE, null , null)));
    }

    public void emoji2Display(MouseEvent event)
    {
        server.send("/topic/emojis", "2");
       // tableview.setBackground(new Background(new BackgroundFill(Color.WHITE, null , null)));
        //refresh();
    }

    public void emoji3Display(MouseEvent event)
    {
        server.send("/topic/emojis", "3");
      //  tableview.setBackground(new Background(new BackgroundFill(Color.WHITE, null , null)));
    }

    public void emoji4Display(MouseEvent event)
    {
        server.send("/topic/emojis", "4");
    }

    public void emoji5Display(MouseEvent event)
    {
        server.send("/topic/emojis", "5");
    }


    public void refresh(String emojiType) {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        System.out.println("A mers");
        List<String> chatEntries = new ArrayList<>();

        System.out.println("Type: " + emojiType);

        if (emojiType.equals("1")) {
            chatEntries.add(getTypeOfMessage("1"));
        }
        if (emojiType.equals("2")) {
            chatEntries.add(getTypeOfMessage("2"));
        }
        if (emojiType.equals("3")) {
            chatEntries.add(getTypeOfMessage("3"));
        }
        if (emojiType.equals("4")) {
            chatEntries.add(getTypeOfMessage("4"));
        }
        if (emojiType.equals("5")) {
            chatEntries.add(getTypeOfMessage("5"));
        }
        chatEntries.addAll(tableview.getItems());
        tableview.setItems(FXCollections.observableList(chatEntries));
    }

    public String getTypeOfMessage(String type)
    {
        if (type.equals("1"))
            return gameConfig.getUserName() + "  \uD83D\uDE02";
        if (type.equals("2"))
            return gameConfig.getUserName() + "  \uD83D\uDE0E";
        if (type.equals("3"))
            return gameConfig.getUserName() + "  \uD83D\uDE0D";
        if (type.equals("4"))
            return gameConfig.getUserName() + "  \uD83D\uDE28";
        if (type.equals("5"))
            return gameConfig.getUserName() + "  \uD83D\uDE20";
        return null;
    }
}
