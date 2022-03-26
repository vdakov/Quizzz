package client.controllers.QuestionControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import com.google.inject.Inject;
import commons.Questions.ComparisonQuestion;

public class ComparisonQuestionActivityCtrl extends QuestionActivityCtrl {
    /**
     * Creates the scene with the needed dependencies
     *
     * @param server    initialised the communication with the server
     * @param sceneCtrl the scene controller
     */
    @Inject
    public ComparisonQuestionActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        super(server, sceneCtrl);
    }

    /**
     * Sets the text for the needed question given as parameter
     *
     * @param comparisonQuestion the question that is set
     */
    public void displayQuestion(ComparisonQuestion comparisonQuestion) {
        if (comparisonQuestion == null) {
            return;
        }

        questionStatement.setText(comparisonQuestion.getQuestion().getKey());

        firstOptionText.setText(comparisonQuestion.getOptions().get(0).getKey());
        secondOptionText.setText(comparisonQuestion.getOptions().get(1).getKey());
        thirdOptionText.setText(comparisonQuestion.getOptions().get(2).getKey());

        questionNumberLabel.setText("Question " + getQuestionNumber());
        points.setText(String.valueOf(getPointsInt()));
        gameConfig.setScore(getPointsInt());

        initialize();
        startTimer();
    }
}
