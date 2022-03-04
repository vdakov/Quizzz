package server.services.QuestionGenerator;

import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.entities.Actions.Action;
import server.entities.Actions.ActionCatalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static server.services.QuestionGenerator.KnowledgeQuestionGenerator.knowledgeQuestionGenerator;
import static server.services.QuestionGenerator.OpenQuestionGenerator.openQuestionsGenerator;
import static server.services.QuestionGenerator.UtilMethods.generateRandomQuestionDistribution;

public class QuestionGenerator {


    public static List<Pair<Question, String>> generateQuestions(ActionCatalog actionCatalog) {

        List<Pair<Question, String>> questionList = new ArrayList<>();

        List<Action> normalActions = actionCatalog.getShuffledNormalActions();
        List<Action> smartActions = actionCatalog.getShuffledSmartActions();

        List<Integer> questionDistribution = generateRandomQuestionDistribution(4, 20, 2, 7, new Random());
        System.out.println(questionDistribution);

        // add open questions
        questionList.addAll(openQuestionsGenerator(questionDistribution.get(0), normalActions, smartActions));
        // add knowledge questions
        questionList.addAll(knowledgeQuestionGenerator(questionDistribution.get(1), smartActions));



        Collections.shuffle(questionList);
        return questionList;
    }

    public static String makeQuestionFromStatement(String statement) {
        return "What is the energy consumption of " + statement;
    }
}
