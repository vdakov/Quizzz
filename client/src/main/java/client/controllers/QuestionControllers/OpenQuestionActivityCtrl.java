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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class OpenQuestionActivityCtrl extends QuestionActivityCtrl {
    private int userAnswerInt;
    private int addedPointsInt;
    // current labels
    @FXML
    private Button answer;
    @FXML
    private Label correctAnswerLabel;
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
        answer.setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783; -fx-background-radius: 15; -fx-border-radius: 15;");
        userAnswerRectangle.setBorder(Border.EMPTY);
        answerTextfield.setText("");
        correctAnswerRectangle.setOpacity(0);
        answerTextfield.setText("");
        answered = false;

        if (!gameConfig.isSinglePlayer())
        {
            splitPane.setVisible(true);
            playersActivity.setCellValueFactory(q -> new SimpleStringProperty(q.getValue()));
        }
        else
        {
            splitPane.setVisible(false);
        }

        server.registerForMessages("/topic/emojis", q -> {
            refresh(q);
        });
    }

    /**
     * Sets the text for the needed question given as parameter
     * Displays the appropriate image for the question
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


        this.image.setImage(SwingFXUtils.toFXImage(bImage, null));

        if (gameConfig.isSinglePlayer()) tableview.setVisible(false);
        else tableview.setVisible(true);

        initialize();
        startTimer();
    }

    public void answerQuestion(ActionEvent event) {
        // answers the question and blocks the possibility to answer anymore
        if (answered) {
            return;
        }
        answered = true;

        try {
            //userAnswerInt = Integer.parseInt(answerTextfield.getText());
            server.updateScore(answerTextfield.getText());
            System.out.println(1);
        } catch (NumberFormatException e) {
            answerTextfield.setText("-99999");
            //server.updateScore(answerTextfield.getText());
            userAnswerInt = Integer.parseInt(answerTextfield.getText());
        } catch (NullPointerException e) {
            if (answerTextfield.getText() == (null) || answerTextfield.getText().trim().isEmpty()) {
                answerTextfield.setText("-99999");
                userAnswerInt = Integer.parseInt(answerTextfield.getText());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        answerUpdate();
        pointsUpdate();
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown
        System.out.println(userAnswerInt);
        if (userAnswerInt == Integer.parseInt(getCorrectAnswer())) {
            userAnswerRectangle.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(30), BorderStroke.THICK)));
        } else {
            userAnswerRectangle.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(30), BorderStroke.THICK)));
            correctAnswerRectangle.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(30), BorderStroke.THICK)));
            correctAnswerLabel.setText(getCorrectAnswer());
        }

        correctAnswerRectangle.setOpacity(1);

    }

    public void pointsUpdate() {
        int correctAnswer = Integer.parseInt(getCorrectAnswer());
        // after the time ends the amount of won points is calculated and then shown to the player
        if (userAnswerInt == correctAnswer) {
            addedPointsInt = 500;
        } else if (userAnswerInt > correctAnswer * 0.95 && userAnswerInt < correctAnswer * 1.05) {
            addedPointsInt = 250;
        }
        addedPoints.setText("+" + addedPointsInt);

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
}
