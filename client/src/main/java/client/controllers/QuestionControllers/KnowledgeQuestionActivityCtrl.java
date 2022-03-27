package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.KnowledgeQuestion;

import java.util.concurrent.ExecutionException;


public class KnowledgeQuestionActivityCtrl extends QuestionActivityCtrl {
    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public KnowledgeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) throws ExecutionException, InterruptedException {
        super(server, sceneCtrl);
    }

    /**
     * Sets the text for the needed question given as parameter
     *
     * @param knowledgeQuestion the question that is set
     */
    public void displayQuestion(KnowledgeQuestion knowledgeQuestion) {
        if (knowledgeQuestion == null) {
            return;
        }

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

