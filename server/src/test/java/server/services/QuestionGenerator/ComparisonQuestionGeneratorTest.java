package server.services.QuestionGenerator;


import commons.Actions.Action;
import commons.Actions.ActionCatalog;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static server.services.QuestionGenerator.ComparisonQuestionGenerator.generateComparisonQuestionFromAction;
import static server.services.QuestionGenerator.ComparisonQuestionGenerator.makeComparisonStatement;

class ComparisonQuestionGeneratorTest {

    ActionCatalog createActionCatalog() {
        ActionCatalog actionCatalog = new ActionCatalog();
        String source = "a";
        actionCatalog.addAction(new Action("2", "a", "Cycling between Rotterdam and Hague", 900, source));
        actionCatalog.addAction(new Action("3", "a", "Playing computer games for 20 days", 1100, source));
        actionCatalog.addAction(new Action("4", "a", "Doing 15 searches on the internet", 1500, source));
        actionCatalog.addAction(new Action("5", "a", "Boiling a bottle of watter", 500, source));
        actionCatalog.addAction(new Action("6", "a", "Walking between Delft and Hague", 1750, source));


//        actionCatalog.addAction(new ActionOld("2", "Cycling between Rotterdam and Hague", 900, null, source));
//        actionCatalog.addAction(new ActionOld("3", "Playing computer games for 20 days", 1100, null, source));
//        actionCatalog.addAction(new ActionOld("4", "Doing 15 searches on the internet", 1500, null, source));
//        actionCatalog.addAction(new ActionOld("5", "Boiling a bottle of watter", 500, null, source));
//        actionCatalog.addAction(new ActionOld("6", "Walking between Delft and Hague", 1750, null, source));

        return actionCatalog;
    }

    @Test
    void comparisonQuestionsGeneratorTest() {

    }

    @Test
    void generateComparisonQuestionFromActionTest() {
        ActionCatalog actionCatalog = createActionCatalog();
        Random random = new Random();
        Pair<Question, String> questionAnswerPair = generateComparisonQuestionFromAction(actionCatalog, random);

        // check if the result respects the desired property
    }

    @Test
    void makeComparisonFromStatementType1Test() {
        String expected = "Which of the following activities consumes the most amount of energy?";
        assertEquals(expected, makeComparisonStatement(1));
    }

    @Test
    void makeComparisonFromStatementType2Test() {
        String expected = "Which of the following activities consumes the least amount of energy?";
        assertEquals(expected, makeComparisonStatement(-1));
    }
}