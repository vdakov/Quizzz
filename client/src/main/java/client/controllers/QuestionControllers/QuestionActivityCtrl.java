package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import client.data.GameConfiguration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.util.Duration;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class QuestionActivityCtrl {
    // constructor needed variables
    protected final ServerUtils server;
    protected final SceneCtrl sceneCtrl;
    protected final GameConfiguration gameConfig = GameConfiguration.getConfiguration();
    protected final double startTime = 13000;
    protected double startTimeClient = 10000;
    protected int addedPointsInt;
    protected String userAnswer;
    protected boolean answered;
    protected int timeLeft;
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
    protected Button firstOptionText;
    @FXML
    protected ImageView firstOptionImage;
    @FXML
    protected Button secondOptionText;
    @FXML
    protected ImageView secondOptionImage;
    @FXML
    protected ImageView image;
    @FXML
    protected Button thirdOptionText;
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
    @FXML
    protected ColumnConstraints chatColumn;
    @FXML
    protected ColumnConstraints questionCol1;
    @FXML
    protected ColumnConstraints questionCol2;
    @FXML
    protected ColumnConstraints questionCol3;

    protected IntegerProperty timeSecondsGlobal =
            new SimpleIntegerProperty((int) startTime);
    protected Timeline timelineGlobal;

    protected IntegerProperty timeSecondsClient =
            new SimpleIntegerProperty((int) startTimeClient);
    protected Timeline timelineClient;

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
        answered = false;
        firstOptionText.setBorder(Border.EMPTY);
        firstOptionText.setDisable(false);
        secondOptionText.setBorder(Border.EMPTY);
        secondOptionText.setDisable(false);
        thirdOptionText.setBorder(Border.EMPTY);
        thirdOptionText.setDisable(false);
        firstOptionText.setStyle("   -fx-background-color: #2e4c8d;");
        secondOptionText.setStyle("   -fx-background-color: #2e4c8d;");
        thirdOptionText.setStyle("   -fx-background-color: #2e4c8d;");


//        firstOptionText.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                if(!answered){
//                    firstOptionText.setStyle("   -fx-background-color: #203665;");
//                }
//
//
//            }
//        });
//
//        secondOptionText.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                if(!answered){
//                    secondOptionText.setStyle("   -fx-background-color: #203665;");
//                }
//
//
//            }
//        });
//
//        thirdOptionText.setOnAction(new EventHandler<ActionEvent>() {
//            @Override public void handle(ActionEvent e) {
//                if(!answered){
//                    thirdOptionText.setStyle("   -fx-background-color: #203665;");
//                }
//
//
//            }
//        });


        answered = false;

        addedPoints.setText(" ");
        addedPointsInt = 0;

        server.resetDoubledAddedPoints();

        if (gameConfig.getCurrentQuestionNumber() <= 1) {
            resetJokers();
        }
        
        if (!gameConfig.isSinglePlayer()) {
            splitPane.setVisible(true); //show the chat
            chatColumn.setPercentWidth(15);
            questionCol1.setPercentWidth(23.333);
            questionCol1.setPercentWidth(23.333);
            questionCol1.setPercentWidth(23.333);
            
            playersActivity.setCellValueFactory(q -> new SimpleStringProperty(q.getValue()));
            server.registerForMessages("/topic/emojis", q -> {
                refresh(q.get(0), q.get(1), q.get(2));
            });
            timeJoker.setOpacity(1);
            timeJoker.setDisable(false);
            if (getTimeJokerUsed() != null) {
                timeJoker.setDisable(getTimeJokerUsed());
                if (getTimeJokerUsed()) {
                    timeJoker.setOpacity(0.5);
                }
            }

        } else {
            splitPane.setVisible(false); //hide the chat
            chatColumn.setPercentWidth(9);
            questionCol1.setPercentWidth(25.333);
            questionCol1.setPercentWidth(25.333);
            questionCol1.setPercentWidth(25.333);
            timeJoker.setOpacity(0);
            gameConfig.setTimeJokerUsed(true);
        }
        //  server.registerForMessages("/topic/emojis", q -> {
        //    refresh(q.get(0), q.get(1), q.get(2));
        //  });

        hintJoker.setDisable(false);
        if (getHintJokerUsed() != null) {
            hintJoker.setDisable(getHintJokerUsed());
        }
    }

    public void answerQuestion(ActionEvent event) throws IOException {
        // answers the question
        if (answered) {
            return;
        }

        timeLeft = timeSecondsGlobal.get();
        updateTimeLeft();
        disableAnswers();

        Button current = (Button) event.getSource();
        userAnswer = current.getText();
        answered = true;
        //blocks the possibility to answer anymore
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown

        //check whether the user's answer is correct and update the boolean value
        firstOptionText.setDisable(false);
        secondOptionText.setDisable(false);
        thirdOptionText.setDisable(false);

        firstOptionText.setStyle("-fx-background-color: #ff000f;");
        secondOptionText.setStyle("-fx-background-color: #ff000f;");
        thirdOptionText.setStyle("-fx-background-color: #ff000f;");


        if (getCorrectAnswer().equals(firstOptionText.getText())) {
            firstOptionText.setStyle("-fx-background-color: #72ff00;");
        } else if (getCorrectAnswer().equals(secondOptionText.getText())) {
            secondOptionText.setStyle("-fx-background-color: #72ff00;");
        } else {
            thirdOptionText.setStyle("-fx-background-color: #72ff00;");
        }

        progressBarTime.setOpacity(0);
        timeLabel.textProperty().bind(timeSecondsGlobal.divide(1000).asString());
    }


    public void pointsUpdate() {
        // after the time ends the amount of won points is calculated and then shown to the player
        addedPointsInt = 0;
        addedPoints.setText("+" + (Integer.parseInt(server.getScore()) - Integer.parseInt(points.getText())));
    }
    //Always 10 seconds, to make the game synchronous
    public void startTimerGlobal() {
        timeSecondsGlobal.set((int) startTime);
        timelineGlobal = new Timeline();
        timelineGlobal.getKeyFrames().add(
                new KeyFrame(Duration.millis(startTime + 1),      //the timeLine handles an animation which lasts start + 1 seconds
                        new KeyValue(timeSecondsGlobal, 0)));    //animation finishes when timeSeconds comes to 0
        timelineGlobal.setOnFinished(event -> {
            try {
                displayNextQuestion();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });       //proceeds to the next question if no answer was given in 10 sec
        timelineGlobal.playFromStart();                                 //start the animation
    }

    public void startTimerClient() {
        if (!gameConfig.isSinglePlayer()) {
            startTimeClient = server.getTimeClient();
            timeSecondsClient.set((int) startTimeClient);
        }
         else {
            timeSecondsClient.set((int) startTimeClient);
        }
        progressBarTime.setOpacity(1);
        progressBarTime.progressProperty().bind(Bindings.divide(timeSecondsClient, startTimeClient));
        timeLabel.textProperty().bind(timeSecondsClient.divide(1000).asString());    //bind the progressbar value to the seconds left

        timelineClient = new Timeline();
        timelineClient.getKeyFrames().add(
                new KeyFrame(Duration.millis(startTimeClient + 1000),      //the timeLine handles an animation which lasts start + 1 seconds
                        new KeyValue(timeSecondsClient, 0)));    //animation finishes when timeSeconds comes to 0
        timelineClient.setOnFinished(event -> {
            try {
                disableAnswers();
                updateTheScoreServer();
                answerUpdate();
                pointsUpdate();
                System.out.println("Points : " + server.getAddedPoints());
                System.out.println("Score : " + server.getScore());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });       //proceeds to the next question if no answer was given in 10 sec
        timelineClient.playFromStart();                                 //start the animation
    }

    /**
     * Method that stops the player from answering after client timer has ran out
     * @throws IOException
     */
    public void disableAnswers() throws IOException {
        firstOptionText.setDisable(true);
        secondOptionText.setDisable(true);
        thirdOptionText.setDisable(true);
    }

    /**
     * Method that displays the next question or stops the game after the last question ends
     *
     * @throws IOException
     */
    public void displayNextQuestion() throws IOException {
        timelineGlobal.stop();
        timelineClient.stop();

        if (answered == false) {
            gameConfig.setConsecutiveUnansweredQuestions(gameConfig.getConsecutiveUnansweredQuestions() + 1);
        } else {
            gameConfig.setConsecutiveUnansweredQuestions(0);
        }

        System.out.println("Question bugs: " + gameConfig.getConsecutiveUnansweredQuestions() + "     " + answered);

        if (gameConfig.getConsecutiveUnansweredQuestions() >= 3 && gameConfig.getGameTypeString().equals("MULTIPLAYER")) {
            this.server.removePlayer();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You have been removed from the room");
            alert.setHeaderText("You have been remove from the room due to inactivity");
            alert.setContentText("You have not answered 3 consecutive questions in a row, so you were left out of the game");
            alert.show();

            sceneCtrl.showServerBrowser();
            return;
        }

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
        timelineGlobal.stop();
        timelineClient.stop();
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
        List<Button> answerLabels = new ArrayList();
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
        for (Button answerLabel : answerLabels) {
            if (!answerLabel.getText().equals(correctAnswer)) {
                answerLabel.setDisable(true);
                return;
            }
        }
    }

    public void useDoublePointJoker() {
        if (getDoublePointJokerUsed()) { return; }
        System.out.println(getDoublePointJokerUsed());

        server.useDoublePointJoker();
        gameConfig.setDoublePointJokerUsed(true);
        doublePointJoker.setDisable(true);
    }

    public void useTimeJoker() {
        //Joker that reduces time for all other players
        if (getTimeJokerUsed()) { return; }
        System.out.println("Time joker just used" + getTimeJokerUsed());
        timeJoker.setDisable(true);
        timeJoker.setOpacity(0.5);
        gameConfig.setTimeJokerUsed(true);
        server.useTimeJoker();

    }

    /**
     * Getter for the correct answer
     *
     * @return the correct answer from the server
     */
    public String getCorrectAnswer() {
        return server.getAnswer();
    }

    /**
     * Getter for the score
     *
     * @return the score from the server
     */
    public int getPointsInt() {
        return Integer.parseInt(server.getScore());
    }

    public Boolean getHintJokerUsed() {
        return gameConfig.isHintJokerUsed();
    }

    public Boolean getDoublePointJokerUsed() {
        return gameConfig.isDoublePointJokerUsed();
    }

    public Boolean getTimeJokerUsed() {
        return gameConfig.isTimeJokerUsed();
    }

    public void resetJokers() {
        gameConfig.setHintJokerUsed(false);
        gameConfig.setDoublePointJokerUsed(false);
        gameConfig.setTimeJokerUsed(false);
    }


    /**
     * Getter for the question number
     *
     * @return the current question number
     */
    public int getQuestionNumber() {
        return gameConfig.getCurrentQuestionNumber();
    }
    public void updateTheScoreServer() {
        server.updateScore(userAnswer);
    }

    /**
     * Methods that will send to the server the type of emojy the player has selected
     *
     * @param event the users clicks on the label
     */

    public void emoji1Display(MouseEvent event) {
        List<String> payload = new ArrayList<>();
        payload.add("1");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji2Display(MouseEvent event) {
        List<String> payload = new ArrayList<>();
        payload.add("2");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji3Display(MouseEvent event) {
        List<String> payload = new ArrayList<>();
        payload.add("3");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji4Display(MouseEvent event) {
        List<String> payload = new ArrayList<>();
        payload.add("4");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
    }

    public void emoji5Display(MouseEvent event) {
        List<String> payload = new ArrayList<>();
        payload.add("5");
        payload.add(gameConfig.getUserName());
        payload.add(gameConfig.getRoomId());
        server.send("/topic/emojis", payload);
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

    /**
     * Method that refreshes the list of messages in the chat by adding a new message  whenever a user clicks on one of the objects.
     *
     * @param type the unique number assigned to an object
     */

    public void refresh(String type, String username, String roomId) {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        List<String> chatEntries = new ArrayList<>();
        if (type.equals("1") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {           // happy emoji
            chatEntries.add(getTypeOfMessage("1", username));
        }
        if (type.equals("2") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {           //sad emoji
            chatEntries.add(getTypeOfMessage("2", username));
        }
        if (type.equals("3") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {           //no words emoji
            chatEntries.add(getTypeOfMessage("3", username));
        }
        if (type.equals("4") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {           //snowman emoji
            chatEntries.add(getTypeOfMessage("4", username));
        }
        if (type.equals("5") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {           //dead emoji
            chatEntries.add(getTypeOfMessage("5", username));
        }
        if (type.equals("Hint") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {        //Hint joker
            chatEntries.add(getTypeOfMessage("Hint", username));
        }
        if (type.equals("x2 Points") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {   //x2 Points Joker
            chatEntries.add(getTypeOfMessage("x2 Points", username));
        }
        if (type.equals("Half Time") && roomId.equals(gameConfiguration.getRoomId()) && !username.equals(gameConfiguration.getUserName())) {   //Half Time Joker
            chatEntries.add(getTypeOfMessage("Half Time", username));
        }

        chatEntries.addAll(tableview.getItems());
        tableview.setItems(FXCollections.observableList(chatEntries));

    }

    /**
     * Method that is going to return a message about what emojis were used by players
     *
     * @param type identifies the type of object with which the players have interacted. All objects have an unique number assigned to them
     * @return a message in the form of a String
     */
    public String getTypeOfMessage(String type, String username) {
        switch (type) {
            case "1":
                //happy emoji
                return " \u263A" + " " + username;

            case "2":
                //sad emoji
                return " \u2639" + " " + username;

            case "3":
                //no words emoji
                return " \u2687" + " " + username;

            case "4":
                //snowman emoji
                return " \u2603" + " " + username;

            case "5":
                //dead emoji
                return " \u2620" + " " + username;

            case "Hint":
                //Hint Joker
                return "Hint by" + " " + username;

            case "x2 Points":
                //x2 Points Joker
                return "x2 Points by" + " " + username;

            case "Half Time":
                //Half Time Joker
                return "Half Time by" + " " + username;

            default:
                return null;
        }
    }

    public int getAddedPointsInt() {
        return server.getAddedPoints();
    }

    public void updateTimeLeft() {
        gameConfig.setTimeLeft(timeLeft);
        server.setTimeLeft();
    }
}
