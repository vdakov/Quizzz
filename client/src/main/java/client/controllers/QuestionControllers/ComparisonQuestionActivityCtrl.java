package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.ComparisonQuestion;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class ComparisonQuestionActivityCtrl extends QuestionActivityCtrl {
    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public ComparisonQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) throws ExecutionException, InterruptedException {
        super(server, sceneCtrl);
    }

    /**
     * Sets the text for the needed question given as parameter
     * Displays the three corresponding images for each question and properly resizes them
     *
     * @param comparisonQuestion the question that is set
     */
    public void displayQuestion(ComparisonQuestion comparisonQuestion) throws IOException {
        if (comparisonQuestion == null) {
            return;
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(server.getQuestionImage("30/beef.jpg"));
        BufferedImage bImage = ImageIO.read(bis);


        questionStatement.setText(comparisonQuestion.getQuestion().getKey());

        firstOptionText.setText(comparisonQuestion.getOptions().get(0).getKey());
        secondOptionText.setText(comparisonQuestion.getOptions().get(1).getKey());
        thirdOptionText.setText(comparisonQuestion.getOptions().get(2).getKey());

        ByteArrayInputStream bis1 = new ByteArrayInputStream(server.getQuestionImage(comparisonQuestion.getOptions().get(0).getRight()));
        BufferedImage bImage1 = ImageIO.read(bis1);

        ByteArrayInputStream bis2 = new ByteArrayInputStream(server.getQuestionImage(comparisonQuestion.getOptions().get(1).getRight()));
        BufferedImage bImage2 = ImageIO.read(bis2);

        ByteArrayInputStream bis3 = new ByteArrayInputStream(server.getQuestionImage(comparisonQuestion.getOptions().get(2).getRight()));
        BufferedImage bImage3 = ImageIO.read(bis3);


        try {
            this.firstOptionImage.setImage(SwingFXUtils.toFXImage(bImage1, null));
        } catch (Exception e) {
            this.firstOptionImage.setImage(new Image("pictures/placeholder.png"));
        }
        this.firstOptionImage.setFitHeight(200);
        this.firstOptionImage.setFitWidth(200);

        try {
            this.secondOptionImage.setImage(SwingFXUtils.toFXImage(bImage2, null));
        } catch (Exception e) {
            this.secondOptionImage.setImage(new Image("pictures/placeholder.png"));
        }
        this.secondOptionImage.setFitHeight(200);
        this.secondOptionImage.setFitWidth(200);

        try {
            this.thirdOptionImage.setImage(SwingFXUtils.toFXImage(bImage3, null));
        } catch (Exception e) {
            this.thirdOptionImage.setImage(new Image("pictures/placeholder.png"));
        }
        this.thirdOptionImage.setFitHeight(200);
        this.thirdOptionImage.setFitWidth(200);

        questionNumberLabel.setText("Question " + getQuestionNumber());
        points.setText(String.valueOf(getPointsInt()));
        gameConfig.setScore(getPointsInt());

        initialize();
        startTimerClient();
        startTimerGlobal();
    }
}
