package server.services.QuestionGenerator;

import commons.Questions.KnowledgeQuestion;
import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.entities.Actions.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static server.entities.Actions.ActionCatalog.shiftActionsLeft;
import static server.services.QuestionGenerator.QuestionGenerator.makeQuestionFromStatement;

public class KnowledgeQuestionGenerator {

    public static List<Pair<Question, String>> knowledgeQuestionGenerator(Integer numberOfNeededQuestions, List<Action> smartActions) {
        List<Pair<Question, String>> questionList = new ArrayList<>();

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            questionList.add(generateKnowledgeQuestionFromAction(shiftActionsLeft(smartActions)));
        }

        return questionList;
    }

    public static Pair<Question, String> generateKnowledgeQuestionFromAction(Action action) {
        String correctAnswer = action.getConsumption();
        List<String> possibleAnswers = new ArrayList<>();
        possibleAnswers.add(correctAnswer);
        possibleAnswers.add(generateKnowledgeAnswer(Integer.parseInt(correctAnswer), 10, 25));
        possibleAnswers.add(generateKnowledgeAnswer(Integer.parseInt(correctAnswer), 50, 75));
        Collections.shuffle(possibleAnswers);

        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion(makeQuestionFromStatement(action.getTitle()), possibleAnswers);

        return Pair.of(knowledgeQuestion, correctAnswer);
    }

    public static String generateKnowledgeAnswer(Integer correctAnswer, Integer errorLowerBound, Integer errorUpperBound) {
        Random random = new Random();
        int offset = (int) ((errorLowerBound + random.nextInt(errorUpperBound - errorLowerBound)) * correctAnswer * 1.0 / 100);
        return Integer.toString((correctAnswer + Math.round(((random.nextBoolean()) ? 1 : -1) * offset)));
    }
}
