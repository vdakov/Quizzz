package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.OpenQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OpenQuestionActivityCtrl extends QuestionActivityCtrl {
    @FXML
    private TextField userAnswer;

    private int userAnswerInt;
    private int addedPointsInt;
    // current labels
    @FXML
    private Button answer;
    @FXML
    private Label correctAnswerLabel;

    @FXML
    private Rectangle userAnswerRectangle;
    @FXML
    private Rectangle correctAnswerRectangle;

    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public OpenQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        super(server, sceneCtrl);
    }

    /**
     * Initialises all the colors for the current scene
     */
    public void initialize() {
        answer.setStyle("-fx-background-color: #ffd783; -fx-border-color:  #ffd783");
    }

    /**
     * Sets the text for the needed question given as parameter
     *
     * @param openQuestion the question that is set
     */
    public void displayQuestion(OpenQuestion openQuestion) {
        if (openQuestion == null) {
            return;
        }
        sampleQuestion.setText(openQuestion.getQuestion().getKey());

        initialize();
        startTimer();
    }

    public void answerQuestion() {
        // answers the question and blocks the possibility to answer anymore
        try {
            userAnswerInt = Integer.parseInt(userAnswer.getText());
            System.out.println(1);
        } catch (NumberFormatException e) {
            userAnswer.setText("-99999");
            server.updateScore(userAnswer.getText());
            userAnswerInt = Integer.parseInt(userAnswer.getText());
        } catch (NullPointerException e) {
            if (userAnswer.getText() == (null) || userAnswer.getText().trim().isEmpty()) {
                userAnswer.setText("-99999");
                userAnswerInt = Integer.parseInt(userAnswer.getText());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        answerUpdate();
        pointsUpdate();
    }

    public void answerUpdate() {
        // after the time ends the right answer is requested and then shown
        userAnswerRectangle.setStrokeWidth(5);
        System.out.println(userAnswerInt);
        if (userAnswerInt == Integer.parseInt(getCorrectAnswer())) {
            userAnswerRectangle.setStroke(Color.valueOf("#92d36e"));
        } else {
            userAnswerRectangle.setStroke(Color.valueOf("#ff0000"));
        }

        correctAnswerRectangle.setFill(Color.valueOf("#c9f1fd"));
        correctAnswerRectangle.setStrokeWidth(5);
        correctAnswerRectangle.setStroke(Color.valueOf("#000000"));
        correctAnswerLabel.setText("Correct Answer: " + getCorrectAnswer());
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
