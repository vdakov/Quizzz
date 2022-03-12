package server.services.QuestionGenerator;

import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.OpenQuestion;
import commons.Questions.Question;

import org.apache.commons.lang3.tuple.Pair;
import commons.Actions.Action;

import commons.Actions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class OpenQuestionGenerator {

    /**
     * Generates a list containing some open questions and the correct answer to them
     *
     * @param numberOfNeededQuestions the number of open questions that will be used in a game
     * @param actionCatalog           the list of all possible actions that will be used for the questions
     * @param random                  the random instance that will be used for randomisation
     * @return a list of pairs consisting of an open question and the answer to that question
     */
    public static List<Pair<Question, String>> openQuestionsGenerator(Integer numberOfNeededQuestions, ActionCatalog actionCatalog, Random random) throws NotEnoughActivitiesException {
        List<Pair<Question, String>> openQuestionsWithAnswersList = new ArrayList<>();

        if (numberOfNeededQuestions * 1 > actionCatalog.getSmartActions().size() + actionCatalog.getNormalActions().size()) {
            throw new NotEnoughActivitiesException();
        }

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            openQuestionsWithAnswersList.add(generateOpenQuestionFromAction((random.nextBoolean()) ? actionCatalog.getSmartAction() : actionCatalog.getNormalAction()));
        }

        return openQuestionsWithAnswersList;
    }

    /**
     * Generates an open question from an action
     *
     * @param action the action that will be converted to a question
     * @return a pair consisting of an open question and the answer to that question
     */
    public static Pair<Question, String> generateOpenQuestionFromAction(Action action) {
        OpenQuestion openQuestion = new OpenQuestion(Pair.of(makeOpenQuestionFromActionTitle(action.getTitle()), action.getImagePath()));
        Integer correctAnswer = action.getConsumption();

        return Pair.of(openQuestion, Integer.toString(correctAnswer));
    }

    /**
     * Transforms an action title into a question statement
     *
     * @param statement the action title that will be modified
     * @return a question that involves the given action title
     */
    public static String makeOpenQuestionFromActionTitle(String statement) {
        char[] c = statement.toCharArray();
        c[0] += 32;
        String finalStatement = new String(c);
        return "What is the energy consumption of " + finalStatement + "?";
    }


}
