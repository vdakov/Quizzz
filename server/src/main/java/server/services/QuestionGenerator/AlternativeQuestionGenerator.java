package server.services.QuestionGenerator;

import commons.Actions.Action;
import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.AlternativeQuestion;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlternativeQuestionGenerator {

    public static List<Pair<Question, String>> alternativeQuestionsGenerator(Integer numberOfNeededQuestions, ActionCatalog actionCatalog, Random random) throws NotEnoughActivitiesException {
        List<Pair<Question, String>> alternativeQuestionWithAnswersList = new ArrayList<>();

        if (numberOfNeededQuestions * 4 > actionCatalog.getSmartActions().size() + actionCatalog.getNormalActions().size()) {
            throw new NotEnoughActivitiesException();
        }

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            alternativeQuestionWithAnswersList.add(generateAlternativeQuestionFromAction(actionCatalog, random));
        }

        return alternativeQuestionWithAnswersList;
    }

    public static Pair<Question, String> generateAlternativeQuestionFromAction(ActionCatalog actionCatalog, Random random) {

        Action baseAction = actionCatalog.getNormalAction();

        Action firstAction  = actionCatalog.getAction(baseAction.getConsumption(), 0, 10, random);
        Action secondAction = actionCatalog.getAction(firstAction.getConsumption(), 15, 50, random);
        Action thirdAction  = actionCatalog.getAction(baseAction.getConsumption(), 50, 100, random);

        List<Pair<String, String>> options = new ArrayList<>();
        options.add(Pair.of(firstAction.getTitle(), firstAction.getImagePath()));
        options.add(Pair.of(secondAction.getTitle(), secondAction.getImagePath()));
        options.add(Pair.of(thirdAction.getTitle(), thirdAction.getImagePath()));

        Collections.shuffle(options);

        AlternativeQuestion alternativeQuestion = new AlternativeQuestion(Pair.of(makeAlternativeQuestionFromStatement(baseAction.getTitle()), baseAction.getImagePath()), options);

        return Pair.of(alternativeQuestion, firstAction.getTitle());
    }

    public static String makeAlternativeQuestionFromStatement(String statement) {
        char[] c = statement.toCharArray();
        c[0] += 32;
        String finalStatement = new String(c);
        return "Instead of " + finalStatement + ", what activity could you do to consume the same amount of energy?";
    }
}
