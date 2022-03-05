package server.services.QuestionGenerator;

import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.entities.Actions.ActionCatalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static server.services.QuestionGenerator.KnowledgeQuestionGenerator.knowledgeQuestionGenerator;
import static server.services.QuestionGenerator.OpenQuestionGenerator.openQuestionsGenerator;
import static server.services.QuestionGenerator.UtilMethods.generateRandomQuestionDistribution;

public class QuestionGenerator {

    public static List<Pair<Question, String>> generateQuestions(ActionCatalog actionCatalog, Random random) {

        List<Pair<Question, String>> questionWithAnswerList = new ArrayList<>();

        actionCatalog.shuffleNormalActions();
        actionCatalog.shuffleSmartActions();

        List<Integer> questionDistribution = generateRandomQuestionDistribution(4, 20, 2, 7, random);

        // add open questions
        questionWithAnswerList.addAll(openQuestionsGenerator(questionDistribution.get(0), actionCatalog, random));
        // add knowledge questions
        questionWithAnswerList.addAll(knowledgeQuestionGenerator(questionDistribution.get(1), actionCatalog, random));


        Collections.shuffle(questionWithAnswerList);
        return questionWithAnswerList;
    }
}
