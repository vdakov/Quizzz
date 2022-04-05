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
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
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
    protected Button hintJoker;
    @FXML
    protected Button doublePointJoker;
    @FXML
    protected Button timeJoker;
    @FXML
    protected Label emoji1;
    @FXML
    protected Label emoji2;
    @FXML
    protected Label emoji3;
    @FXML
    protected Label emoji4;
    @FXML
    protected Label emoji5;
    @FXML
    protected TableView tableview;
    @FXML
    protected TableColumn<String, String> playersActivity;
    @FXML
    protected SplitPane splitPane;



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
     * If the game is multiplayer it displays the option to use emojis and to post them in a chat
     */
    public void initialize() throws IOException {
        firstOptionText.setBorder(Border.EMPTY);
        firstOptionText.setDisable(false);
        secondOptionText.setBorder(Border.EMPTY);
        secondOptionText.setDisable(false);
        thirdOptionText.setBorder(Border.EMPTY);
        thirdOptionText.setDisable(false);

        answered = false;

        addedPoints.setText(" ");
        addedPointsInt = 0;

        if (!gameConfig.isSinglePlayer())
        {
            splitPane.setVisible(true);
            playersActivity.setCellValueFactory(q -> new SimpleStringProperty(q.getValue()));
                server.registerForMessages("/topic/emojis", q -> {
                    refresh(q.get(0), q.get(1), q.get(2));
                });
        }
        else
        {
            splitPane.setVisible(false);
        }
      //  server.registerForMessages("/topic/emojis", q -> {
        //    refresh(q.get(0), q.get(1), q.get(2));
      //  });

        hintJoker.setDisable(false);
        if (getHintJokerUsed() != null) {
            hintJoker.setDisable(getHintJokerUsed());
        }
    }


    public void answerQuestion(MouseEvent event) {
        // answers the question
        if (answered) {
            return;
        }

        Label current = (Label) event.getSource();
        userAnswer = current.getText();
        answered = true;

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
            addedPointsInt = getAddedPointsInt();
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

    /**
     * Method that displays the next question or stops the game after the last question ends
     * @throws IOException
     */
    public void displayNextQuestion() throws IOException {
        timeline.stop();
        if ((gameConfig.isMultiPlayer() && gameConfig.getCurrentQuestionNumber() == 9) || gameConfig.getCurrentQuestionNumber() == 19) {
            server.addOrUpdateLeaderboardEntry(gameConfig.getUserName(), gameConfig.getRoomId(), gameConfig.getScore());
            sceneCtrl.showLeaderboard();
        } else sceneCtrl.showNextQuestion();
    }

    /**
     * Method that ends the game and returns the player to the main screen of the app
     * @throws IOException
     */
    public void goToMainScreen() throws IOException {
        timeline.stop();
        sceneCtrl.showMainScreenScene();
    }

    /**
     * Getter for the correct answer
     * @return the correct answer from the server
     */
    public void useHintJoker() {
        //Joker that eliminates the wrong answer
        if (getHintJokerUsed()) { return; }

        //Make a list of possible answers
        List<Label> answerLabels = new ArrayList();
        answerLabels.add(firstOptionText);
        answerLabels.add(secondOptionText);
        answerLabels.add(thirdOptionText);

        //shuffle answers to choose randomly from them
        Collections.shuffle(answerLabels);
        String correctAnswer = getCorrectAnswer();

        server.useHintJoker();
        gameConfig.setHintJokerUsed(true);
        hintJoker.setDisable(true);

        //go until incorrect answer is found and eliminate it
        for (Label answerLabel : answerLabels) {
            if (!answerLabel.getText().equals(correctAnswer)) {
                answerLabel.setDisable(true);
                return;
            }
        }
    }

    public void hintJokerEvent(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("Hint");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void pointsJokerEvent(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("x2 Points");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void timeJokerEvent(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("Half Time");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void useDoublePointJoker() {
        if (server.getDoublePointJokerUsed()) { return; }

        server.useDoublePointJoker();
        doublePointJoker.setDisable(true);
    }

    /**
     * Getter for the correct answer
     * @return the correct answer from the server
     */
    public String getCorrectAnswer() {
        return server.getAnswer();
    }

    /**
     * Getter for the score
     * @return the score from the server
     */
    public int getPointsInt() {
        return Integer.parseInt(server.getScore());
    }

    public Boolean getHintJokerUsed() {
        return server.getHintJokerUsed();
    }

    public boolean getDoublePointJokerUsed() {
        return server.getDoublePointJokerUsed();
    }


    /**
     * Getter for the question number
     * @return the current question number
     */

    public int getQuestionNumber() {
        return gameConfig.getCurrentQuestionNumber();
    }

    /**
     * Methods that will send to the server the type of emojy the player has selected
     * @param event  the users clicks on the label
     */

    public void emoji1Display(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("1");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji2Display(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("2");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji3Display(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("3");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji4Display(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("4");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji5Display(MouseEvent event)
    {
        List<String> payload = new ArrayList<>();
        payload.add("5");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }


    /**
     * Method that refreshes the list of messages in the chat by adding a new message  whenever a user clicks on one of the objects.
     * @param type the unique number assigned to an object
     */
    public void refresh(String type, String username, String roomId) {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        List<String> chatEntries = new ArrayList<>();
            if (type.equals("1") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {           // happy emoji
                chatEntries.add(getTypeOfMessage("1", username));
            }
            if (type.equals("2") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {           //sad emoji
                chatEntries.add(getTypeOfMessage("2", username));
            }
            if (type.equals("3") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {           //no words emoji
                chatEntries.add(getTypeOfMessage("3", username));
            }
            if (type.equals("4") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {           //snowman emoji
                chatEntries.add(getTypeOfMessage("4", username));
            }
            if (type.equals("5") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {           //dead emoji
                chatEntries.add(getTypeOfMessage("5", username));
            }
            if (type.equals("Hint") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {        //Hint joker
                 chatEntries.add(getTypeOfMessage("Hint", username));
            }
            if (type.equals("x2 Points") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {   //x2 Points Joker
                 chatEntries.add(getTypeOfMessage("x2 Points", username));
            }
            if (type.equals("Half Time") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName()) ) {   //Half Time Joker
                chatEntries.add(getTypeOfMessage("Half Time", username));
            }

            chatEntries.addAll(tableview.getItems());
            tableview.setItems(FXCollections.observableList(chatEntries));

    }

    /**
     * Method that is going to return a message about what emojis were used by players
     * @param type identifies the type of object with which the players have interacted. All objects have an unique number assigned to them
     * @return a message in the form of a String
     */
    public String getTypeOfMessage(String type, String username)
    {
        switch (type) {
            case "1" :
                //happy emoji
                return   " \u263A" + " " + username;

            case "2" :
                //sad emoji
                return  " \u2639" + " " + username;

            case "3" :
                //no words emoji
                return " \u2687" + " " + username;

            case "4" :
                //snowman emoji
                return  " \u2603" +  " " + username;

            case "5" :
                //dead emoji
                return  " \u2620" + " " + username;

            case "Hint" :
                //Hint Joker
                return "Hint by" + " " + username;

            case "x2 Points" :
                //x2 Points Joker
                return  "x2 Points by" + " " + username;

            case "Half Time" :
                //Half Time Joker
                return  "Half Time by" + " " + username;

            default:
                return null;
        }


    }

    public int getAddedPointsInt() {
        return server.getAddedPoints();
    }
}
