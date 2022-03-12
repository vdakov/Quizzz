package server.services.QuestionGenerator;

import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import commons.Actions.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static server.services.QuestionGenerator.AlternativeQuestionGenerator.alternativeQuestionsGenerator;
import static server.services.QuestionGenerator.ComparisonQuestionGenerator.comparisonQuestionsGenerator;
import static server.services.QuestionGenerator.KnowledgeQuestionGenerator.knowledgeQuestionsGenerator;
import static server.services.QuestionGenerator.OpenQuestionGenerator.openQuestionsGenerator;
import static server.services.QuestionGenerator.UtilMethods.generateRandomQuestionDistribution;

public class QuestionGenerator {

    // created to manually check the result
    public static void main(String[] args) {
        ActionCatalog actionCatalog = new ActionCatalog();

//        actionCatalog.addAction(new Action("1", "Mining 1 Bitcoin", 1000, null, source));
//        actionCatalog.addAction(new Action("2", "Cycling between Rotterdam and Hague", 900, null, source));
//        actionCatalog.addAction(new Action("3", "Playing computer games for 20 days", 1100, null, source));
//        actionCatalog.addAction(new Action("4", "Doing 15 searches on the internet", 1500, null, source));
//        actionCatalog.addAction(new Action("5", "Boiling a bottle of watter", 500, null, source));
//        actionCatalog.addAction(new Action("6", "Walking between Delft and Hague", 1750, null, source));

        Random random = new Random();

        // to generate more questions there is the need for more activities ( rn the code breaks if there are not
        // enough activities, so it can generate the questions with different activities
        try {
            List<Pair<Question, String>> questions = generateQuestions(actionCatalog, 1, 0, 2, random);
            System.out.println(questions);
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities exception found");
        }
    }

    /**
     * Generates a list of questions that are of 4 types ( already known ) with the answer for every question
     *
     * @param actionCatalog         the list of all possible actions that will be used for the questions
     * @param questionsNumber       the number of questions to be generated
     * @param lowerBoundAppearances the minimum number of appearances of a certain question type
     * @param upperBoundAppearances the maximum number of appearances of a certain question type
     * @param random                the random instance that will be used for randomisation
     * @return a list of pairs consisting of a question and the answer to that question
     * @throws NotEnoughActivitiesException if there are not enough activities to make the desired number of questions so that no activities are duplicate
     */
    public static List<Pair<Question, String>> generateQuestions(ActionCatalog actionCatalog, int questionsNumber,
                                                                 int lowerBoundAppearances, int upperBoundAppearances, Random random) throws NotEnoughActivitiesException {
        List<Pair<Question, String>> questionWithAnswerList = new ArrayList<>();

        actionCatalog.shuffleNormalActions();
        actionCatalog.shuffleSmartActions();

        List<Integer> questionDistribution = generateRandomQuestionDistribution(4, questionsNumber, lowerBoundAppearances, upperBoundAppearances, random);

        // add open questions
        questionWithAnswerList.addAll(openQuestionsGenerator       (questionDistribution.get(0), actionCatalog, random));
        // add knowledge questions
        questionWithAnswerList.addAll(knowledgeQuestionsGenerator  (questionDistribution.get(1), actionCatalog, random));
        // add comparison questions
        questionWithAnswerList.addAll(comparisonQuestionsGenerator (questionDistribution.get(2), actionCatalog, random));
        // add alternative questions
        questionWithAnswerList.addAll(alternativeQuestionsGenerator(questionDistribution.get(3), actionCatalog, random));

        Collections.shuffle(questionWithAnswerList);
        return questionWithAnswerList;
    }
}
