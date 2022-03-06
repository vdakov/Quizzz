package server.services.QuestionGenerator;

import commons.Questions.KnowledgeQuestion;
import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.controllers.Actions.Action;
import server.controllers.Actions.ActionCatalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class KnowledgeQuestionGenerator {

    public static List<Pair<Question, String>> knowledgeQuestionsGenerator(Integer numberOfNeededQuestions, ActionCatalog actionCatalog, Random random) {
        List<Pair<Question, String>> knowledgeQuestionWithAnswersList = new ArrayList<>();

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            knowledgeQuestionWithAnswersList.add(generateKnowledgeQuestionFromAction((random.nextBoolean()) ? actionCatalog.getSmartAction() : actionCatalog.getNormalAction(), random));

        }

        return knowledgeQuestionWithAnswersList;
    }

    public static Pair<Question, String> generateKnowledgeQuestionFromAction(Action action, Random random) {
        Integer correctAnswer = action.getConsumption();
        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add(Integer.toString(correctAnswer));
        possibleAnswers.add(generateKnowledgeAnswer(correctAnswer, 10, 25, random));
        possibleAnswers.add(generateKnowledgeAnswer(correctAnswer, 50, 75, random));
        Collections.shuffle(possibleAnswers);

        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion(makeKnowledgeQuestionFromStatement(action.getTitle()), possibleAnswers);

        return Pair.of(knowledgeQuestion, Integer.toString(correctAnswer));
    }

    public static String generateKnowledgeAnswer(Integer correctAnswer, Integer errorLowerBound, Integer errorUpperBound, Random random) {
        int offset = (int) ((errorLowerBound + random.nextInt(errorUpperBound - errorLowerBound)) * correctAnswer * 1.0 / 100);
        return Integer.toString((correctAnswer + Math.round(((random.nextBoolean()) ? 1 : -1) * offset)));
    }

    public static String makeKnowledgeQuestionFromStatement(String statement) {
        char[] c = statement.toCharArray();
        c[0] += 32;
        String finalStatement = new String(c);
        return "Which one of the following is the energy consumption of " + finalStatement + "?";
    }
}
