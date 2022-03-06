package server.services.QuestionGenerator;

import commons.Questions.ComparisonQuestion;

import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.controllers.Actions.Action;
import server.controllers.Actions.ActionCatalog;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComparisonQuestionGenerator {


    public static List<Pair<Question, String>> comparisonQuestionsGenerator(Integer numberOfNeededQuestions, ActionCatalog actionCatalog, Random random) {
        List<Pair<Question, String>> comparisonQuestionWithAnswersList = new ArrayList<>();

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            comparisonQuestionWithAnswersList.add(generateComparisonQuestionFromAction(actionCatalog, random));

        }

        return comparisonQuestionWithAnswersList;
    }

    public static Pair<Question, String> generateComparisonQuestionFromAction(ActionCatalog actionCatalog, Random random) {

        Action firstAction = actionCatalog.getNormalAction();
        Action secondAction = actionCatalog.getAction(firstAction.getConsumption(), 5, 25, random);
        Action thirdAction  = actionCatalog.getAction(firstAction.getConsumption(), 30, 75, random);

        List<String> options = new ArrayList<>();
        options.add(firstAction.getTitle());
        options.add(secondAction.getTitle());
        options.add(thirdAction.getTitle());

        Collections.shuffle(options);

        int sign = (random.nextBoolean()) ? 1 : -1;

        ComparisonQuestion comparisonQuestion = new ComparisonQuestion(makeComparisonStatement(sign), options);

        return Pair.of(comparisonQuestion, Integer.toString(sign * Math.max(sign * firstAction.getConsumption(), Math.max(sign * secondAction.getConsumption(), sign * thirdAction.getConsumption()))));
    }

    public static String makeComparisonStatement(int sign) {
        return ((sign == 1) ? "Which of the following activities consumes the most amount of energy?" : "Which of the following activities consumes the least amount of energy?");
    }

}
