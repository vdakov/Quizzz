package server.services.QuestionGenerator;

import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.entities.Actions.Action;
import server.entities.Actions.ActionCatalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static server.services.QuestionGenerator.OpenQuestionGenerator.openQuestionsGenerator;

public class QuestionGenerator {

    public static List<Pair<Question, String>> generateQuestions() {

        ActionCatalog actionCatalog = new ActionCatalog();

        List<Pair<Question, String>> questionList = new ArrayList<>();

        List<Action> normalActions = actionCatalog.getShuffledNormalActions();
        List<Action> smartActions = actionCatalog.getShuffledSmartActions();

        // add 2 open questions
        questionList.addAll(openQuestionsGenerator(normalActions, smartActions));

        Collections.shuffle(questionList);
        return questionList;
    }

    public static String makeQuestionFromStatement(String statement) {
        return "What is the energy consumption of " + statement;
    }
}
