package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.OpenQuestion;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class OpenQuestionActivityCtrl extends QuestionActivityCtrl {
    private String userAnswer;
    private int addedPointsInt;
    // current labels
    @FXML
    private Button answer;
    @FXML
    private Label correctAnswerLabel;
    @FXML
    private Label hintAnswerLabel;
    @FXML
    private TextField answerTextfield;

    @FXML
    private GridPane userAnswerRectangle;
    @FXML
    private GridPane correctAnswerRectangle;

    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public OpenQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) throws ExecutionException, InterruptedException {
        super(server, sceneCtrl);
    }

    /**
     * Initialises all the colors for the current scene
     */
    public void initialize() {
        answerTextfield.setDisable(false);
        answer.setDisable(false);
        answer.setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783; ");
        userAnswerRectangle.setBorder(Border.EMPTY);
        answerTextfield.setText("");
        correctAnswerRectangle.setOpacity(0);
        answerTextfield.setText("");
        answered = false;

        addedPoints.setText(" ");
        addedPointsInt = 0;

        if (gameConfig.getCurrentQuestionNumber() <= 1) {
            resetJokers();
        }

        hintAnswerLabel.setOpacity(0);

        if (getHintJokerUsed() != null) {
            hintJoker.setDisable(getHintJokerUsed());
        }
        if (getDoublePointJokerUsed() != null) {
            doublePointJoker.setDisable(getDoublePointJokerUsed());
        }
        if (!gameConfig.isSinglePlayer()) {
            splitPane.setVisible(true);
            chatColumn.setPercentWidth(15);
            questionCol1.setPercentWidth(23.333);
            questionCol1.setPercentWidth(23.333);
            questionCol1.setPercentWidth(23.333);

            playersActivity.setCellValueFactory(q -> new SimpleStringProperty(q.getValue()));

            if (getTimeJokerUsed() != null) {
                timeJoker.setDisable(getTimeJokerUsed());
                if (getTimeJokerUsed()) {
                    timeJoker.setOpacity(0.5);
                }
            }

        } else {
            splitPane.setVisible(false);
            chatColumn.setPercentWidth(9);
            questionCol1.setPercentWidth(25.333);
            questionCol1.setPercentWidth(25.333);
            questionCol1.setPercentWidth(25.333);

            timeJoker.setOpacity(0);
            gameConfig.setTimeJokerUsed(true);
        }

        server.registerForMessages("/topic/emojis", q -> {
            refresh(q.get(0), q.get(1), q.get(2));
        });
        gameConfig.connect();
    }

    /**
     * Sets the text for the needed question given as parameter
     * Displays the appropriate image for the question
     *
     * @param openQuestion the question that is set
     */
    public void displayQuestion(OpenQuestion openQuestion) throws IOException {
        if (openQuestion == null) {
            return;
        }

        sampleQuestion.setText(openQuestion.getQuestion().getKey());
        questionNumberLabel.setText("Question " + getQuestionNumber());
        points.setText(String.valueOf(getPointsInt()));
        gameConfig.setScore(getPointsInt());


        ByteArrayInputStream bis = new ByteArrayInputStream(server.getQuestionImage(openQuestion.getQuestion().getRight()));
        BufferedImage bImage = ImageIO.read(bis);

        try {
            this.image.setImage(SwingFXUtils.toFXImage(bImage, null));
        } catch (Exception e) {
            this.image.setImage(new Image("pictures/placeholder.png"));
        }


        if (gameConfig.isSinglePlayer()) splitPane.setVisible(false);
        else splitPane.setVisible(true);

        initialize();
        startTimerClient();
        startTimerGlobal();
    }

    /**
     * Gets the answer that was given for the question
     * @param event the action that triggers getting the input that was answered
     * @throws IOException
     */
    public void answerQuestion(ActionEvent event) throws IOException {
        // answers the question and blocks the possibility to answer anymore
        if (answered) {
            return;
        }
        timeLeft = timeSecondsGlobal.get();
        System.out.println(timeLeft);

        userAnswer = answerTextfield.getText();
        answered = true;
        disableAnswers();
    }

    /**
     * Updates the answer
     *  after the time ends the right answer is requested and then shown
     */
    public void answerUpdate() {
        System.out.println(userAnswer);
        if (userAnswer.equals(getCorrectAnswer())) {
            userAnswerRectangle.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.THICK)));
        } else {
            userAnswerRectangle.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.THICK)));
            correctAnswerLabel.setText(getCorrectAnswer());
        }

        correctAnswerRectangle.setOpacity(1);
        progressBarTime.setOpacity(0);
        timeLabel.textProperty().bind(timeSecondsGlobal.divide(1000).asString());

    }

    /**
     * Updates the points
     */
    public void pointsUpdate() {
        if (userAnswer.equals(getCorrectAnswer())) {
            addedPointsInt = getAddedPointsInt();
        } else {
            addedPointsInt = 0;
        }
        addedPoints.setText("+" + addedPointsInt);

    }

    /**
     * The hint Joker
     */
    public void useHintJoker() {
        //Joker that eliminates the wrong answer
        if (getHintJokerUsed()) {
            return;
        }

        Integer correctAnswer = Integer.parseInt(getCorrectAnswer());

        Random random = new Random();
        // creating a hint answer within the range -50% - 50% of the correct answer
        double randomd = random.nextDouble();
        int hintAnswer = (int) ((randomd - 0.5) * correctAnswer + correctAnswer);
        hintAnswerLabel.setText("Hint answer: " + hintAnswer);
        hintAnswerLabel.setOpacity(1);

        server.useHintJoker();
        hintJoker.setDisable(true);
        gameConfig.setHintJokerUsed(true);
    }

    /**
     * The double points joker
     */
    public void useDoublePointJoker() {
        if (getDoublePointJokerUsed()) {
            return;
        }

        server.useDoublePointJoker();
        gameConfig.setDoublePointJokerUsed(true);
        doublePointJoker.setDisable(true);

    }

    /**
     * Method that stops the player from answering after client timer has ran out
     *
     * @throws IOException
     */
    public void disableAnswers() throws IOException {
        answerTextfield.setDisable(true);
        answer.setDisable(true);
    }

    /**
     * Updates the score
     */
    public void updateTheScoreServer() {
        server.updateScore(userAnswer);
    }
}
