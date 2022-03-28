package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.KnowledgeQuestion;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class KnowledgeQuestionActivityCtrl extends QuestionActivityCtrl {
    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public KnowledgeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        super(server, sceneCtrl);
    }

    /**
     * Sets the text for the needed question given as parameter
     *Displays the appropriate image for the question
     * @param knowledgeQuestion the question that is set
     */
    public void displayQuestion(KnowledgeQuestion knowledgeQuestion) throws IOException {
        if (knowledgeQuestion == null) {
            return;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(server.getQuestionImage(knowledgeQuestion.getQuestion().getRight()));
        BufferedImage bImage = ImageIO.read(bis);


        this.image.setImage(SwingFXUtils.toFXImage(bImage, null));


        sampleQuestion.setText(knowledgeQuestion.getQuestion().getKey());

        firstOptionText.setText(knowledgeQuestion.getOptions().get(0));
        secondOptionText.setText(knowledgeQuestion.getOptions().get(1));
        thirdOptionText.setText(knowledgeQuestion.getOptions().get(2));

        questionNumberLabel.setText("Question " + getQuestionNumber());
        points.setText(String.valueOf(getPointsInt()));

        initialize();
        startTimer();
    }
}

