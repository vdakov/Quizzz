package server.services.QuestionGenerator;

import commons.Questions.OpenQuestion;
import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.entities.Actions.Action;
import server.entities.Actions.ActionCatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class OpenQuestionGenerator {

    public static List<Pair<Question, String>> openQuestionsGenerator(Integer numberOfNeededQuestions, ActionCatalog actionCatalog, Random random) {
        List<Pair<Question, String>> openQuestionsWithAnswersList = new ArrayList<>();

        for (int i = 0; i < numberOfNeededQuestions; i++) {
            openQuestionsWithAnswersList.add(generateOpenQuestionFromAction((random.nextBoolean() == true) ? actionCatalog.getSmartAction() : actionCatalog.getNormalAction()));
        }

        return openQuestionsWithAnswersList;
    }

    public static Pair<Question, String> generateOpenQuestionFromAction(Action action) {
        OpenQuestion openQuestion = new OpenQuestion(makeOpenQuestionFromStatement(action.getTitle()));
        Integer correctAnswer = action.getConsumption();

        return Pair.of(openQuestion, Integer.toString(correctAnswer));
    }

    public static String makeOpenQuestionFromStatement(String statement) {
        char c[] = statement.toCharArray();
        c[0] += 32;
        String finalStatement = new String(c);
        return "What is the energy consumption of " + finalStatement + "?";
    }
}
