package server.services.QuestionGenerator;

import commons.Actions.Action;
import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.ComparisonQuestion;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComparisonQuestionGenerator {


    public static List<Pair<Question, String>> comparisonQuestionsGenerator(Integer numberOfNeededQuestions, ActionCatalog actionCatalog, Random random) throws NotEnoughActivitiesException {
        List<Pair<Question, String>> comparisonQuestionWithAnswersList = new ArrayList<>();

        if (numberOfNeededQuestions * 3 > actionCatalog.getSmartActions().size() + actionCatalog.getNormalActions().size()) {
            throw new NotEnoughActivitiesException();
        }

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            comparisonQuestionWithAnswersList.add(generateComparisonQuestionFromAction(actionCatalog, random));

        }

        return comparisonQuestionWithAnswersList;
    }

    public static Pair<Question, String> generateComparisonQuestionFromAction(ActionCatalog actionCatalog, Random random) {

        Action firstAction = actionCatalog.getNormalAction();
        Action secondAction = actionCatalog.getAction(firstAction.getConsumption(), 5, 25, random);
        Action thirdAction  = actionCatalog.getAction(firstAction.getConsumption(), 30, 75, random);

        List<Pair<String, String>> options = new ArrayList<>();
        options.add(Pair.of(firstAction.getTitle(), firstAction.getImagePath()));
        options.add(Pair.of(secondAction.getTitle(), secondAction.getImagePath()));
        options.add(Pair.of(thirdAction.getTitle(), thirdAction.getImagePath()));

        Collections.shuffle(options);

        int sign = (random.nextBoolean()) ? 1 : -1;

        ComparisonQuestion comparisonQuestion = new ComparisonQuestion(Pair.of(makeComparisonStatement(sign), null), options);

        return Pair.of(comparisonQuestion, Long.toString(sign * Math.max(sign * firstAction.getConsumption(), Math.max(sign * secondAction.getConsumption(), sign * thirdAction.getConsumption()))));
    }

    public static String makeComparisonStatement(int sign) {
        return ((sign == 1) ? "Which of the following activities consumes the most amount of energy?" : "Which of the following activities consumes the least amount of energy?");
    }

}
