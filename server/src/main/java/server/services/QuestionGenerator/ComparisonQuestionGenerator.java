package server.services.QuestionGenerator;

import commons.Questions.ComparisonQuestion;

import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.entities.Actions.Action;
import server.entities.Actions.ActionCatalog;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class ComparisonQuestionGenerator {

    public static List<Pair<Question, String>> comparisonQuestionGenerator(Integer numberOfNeededQuestions, ActionCatalog actionCatalog, Random random) {
        List<Pair<Question, String>> comparisonQuestionWithAnswersList = new ArrayList<>();

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            comparisonQuestionWithAnswersList.add(generateComparisonQuestionFromAction(actionCatalog, random));

        }

        return comparisonQuestionWithAnswersList;
    }

    public static Pair<Question, String> generateComparisonQuestionFromAction(ActionCatalog actionCatalog, Random random) {

//        Action firstAction = actionCatalog.getNormalAction();
//        Action secondAction = actionCatalog.getAction(firstAction.getConsumption(), 5, 25);
//        Action thirdAction  = actionCatalog.getAction(firstAction.getConsumption(), 30, 75);

        Action firstAction  = actionCatalog.getNormalAction();
        Action secondAction = actionCatalog.getNormalAction();
        Action thirdAction  = actionCatalog.getNormalAction();

        List<String> options = new ArrayList<>();
        options.add(firstAction.getTitle());
        options.add(secondAction.getTitle());
        options.add(thirdAction.getTitle());

        ComparisonQuestion comparisonQuestion = new ComparisonQuestion(makeComparisonFromStatement(random), options);

        return Pair.of(comparisonQuestion, Integer.toString(Math.max(firstAction.getConsumption(), Math.max(secondAction.getConsumption(), thirdAction.getConsumption()))));
    }

    public static String makeComparisonFromStatement(Random random) {
        return ((random.nextBoolean() == true) ? "Which of the following activities consumes the most amount of energy" : "Which of the following activities consumes the most amount of energy");
    }

}
