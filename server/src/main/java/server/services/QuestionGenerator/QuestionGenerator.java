package server.services.QuestionGenerator;

import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.controllers.Actions.ActionCatalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static server.services.QuestionGenerator.ComparisonQuestionGenerator.comparisonQuestionsGenerator;
import static server.services.QuestionGenerator.KnowledgeQuestionGenerator.knowledgeQuestionsGenerator;
import static server.services.QuestionGenerator.OpenQuestionGenerator.openQuestionsGenerator;
import static server.services.QuestionGenerator.UtilMethods.generateRandomQuestionDistribution;

public class QuestionGenerator {

    /**
     * Generates a list of questions that are of 4 types ( already known ) with the answer for every question
     *
     * @param actionCatalog         the list of all possible actions that will be used for the questions
     * @param questionsNumber       the number of questions to be generated
     * @param lowerBoundAppearances the minimum number of appearances of a certain question type
     * @param upperBoundAppearances the maximum number of appearances of a certain question type
     * @param random                the random instance that will be used for randomisation
     * @return a list of pairs consisting of a question and the answer to that question
     */
    public static List<Pair<Question, String>> generateQuestions(ActionCatalog actionCatalog, int questionsNumber,
                                                                 int lowerBoundAppearances, int upperBoundAppearances, Random random) {
        List<Pair<Question, String>> questionWithAnswerList = new ArrayList<>();

        actionCatalog.shuffleNormalActions();
        actionCatalog.shuffleSmartActions();

        List<Integer> questionDistribution = generateRandomQuestionDistribution(4, questionsNumber, lowerBoundAppearances, upperBoundAppearances, random);

        // add open questions
        questionWithAnswerList.addAll(openQuestionsGenerator      (questionDistribution.get(0), actionCatalog, random));
        // add knowledge questions
        questionWithAnswerList.addAll(knowledgeQuestionsGenerator (questionDistribution.get(1), actionCatalog, random));
        // add comparison questions
        questionWithAnswerList.addAll(comparisonQuestionsGenerator(questionDistribution.get(2), actionCatalog, random));
        // add alternative questions
        questionWithAnswerList.addAll(comparisonQuestionsGenerator(questionDistribution.get(3), actionCatalog, random));

        Collections.shuffle(questionWithAnswerList);
        return questionWithAnswerList;


    }
}
