package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.AlternativeQuestion;


public class AlternativeQuestionActivityCtrl extends QuestionActivityCtrl {
    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public AlternativeQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        super(server, sceneCtrl);
    }

    /**
     * Sets the text for the needed question given as parameter
     *
     * @param alternativeQuestion the question that is set
     */
    public void displayQuestion(AlternativeQuestion alternativeQuestion) {
        if (alternativeQuestion == null) {
            return;
        }

        sampleQuestion.setText(alternativeQuestion.getQuestion().getKey());

        firstOptionText.setText(alternativeQuestion.getOptions().get(0).getKey());
        secondOptionText.setText(alternativeQuestion.getOptions().get(1).getKey());
        thirdOptionText.setText(alternativeQuestion.getOptions().get(2).getKey());
        questionNumberLabel.setText("Question " + getQuestionNumber());
        points.setText(String.valueOf(getPointsInt()));

        initialize();
        startTimer();
        System.out.println("Timer started");
    }
}
