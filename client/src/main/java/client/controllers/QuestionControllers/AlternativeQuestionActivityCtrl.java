package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.AlternativeQuestion;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class AlternativeQuestionActivityCtrl extends QuestionActivityCtrl {
    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public AlternativeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) throws ExecutionException, InterruptedException {
        super(server, sceneCtrl);
    }

    /**
     * Sets the text for the needed question given as parameter
     * Properly displays the corresponding images
     *
     * @param alternativeQuestion the question that is set
     */
    public void displayQuestion(AlternativeQuestion alternativeQuestion) throws IOException {
        if (alternativeQuestion == null) {
            return;
        }

        ByteArrayInputStream bis = new ByteArrayInputStream(server.getQuestionImage(alternativeQuestion.getQuestion().getRight()));
        BufferedImage bImage = ImageIO.read(bis);

        try {
            this.image.setImage(SwingFXUtils.toFXImage(bImage, null));
        } catch (Exception e) {
            this.image.setImage(new Image("pictures/placeholder.png"));
        }


        sampleQuestion.setText(alternativeQuestion.getQuestion().getKey());

        firstOptionText.setText(alternativeQuestion.getOptions().get(0).getKey());
        secondOptionText.setText(alternativeQuestion.getOptions().get(1).getKey());
        thirdOptionText.setText(alternativeQuestion.getOptions().get(2).getKey());
        questionNumberLabel.setText("Question " + getQuestionNumber());
        points.setText(String.valueOf(getPointsInt()));
        gameConfig.setScore(getPointsInt());

        initialize();
        startTimerClient();
        startTimerGlobal();
        System.out.println("Timer started");
    }
}
