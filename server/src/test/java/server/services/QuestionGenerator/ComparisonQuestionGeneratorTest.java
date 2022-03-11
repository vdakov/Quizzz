package server.services.QuestionGenerator;

import org.junit.jupiter.api.Test;
import commons.Actions.Action;
import server.entities.Actions.ActionCatalog;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static server.services.QuestionGenerator.ComparisonQuestionGenerator.makeComparisonStatement;

class ComparisonQuestionGeneratorTest {

    ActionCatalog createActionCatalog() {
        ActionCatalog actionCatalog = new ActionCatalog();
        actionCatalog.addAction(new Action("1", "Mining 1 Bitcoin", 1000, null));
        actionCatalog.addAction(new Action("2", "Cycling between Rotterdam and Hague", 900, null));
        actionCatalog.addAction(new Action("3", "Playing computer games for 20 days", 1100, null));
        actionCatalog.addAction(new Action("4", "Doing 15 searches on the internet", 1500, null));
        actionCatalog.addAction(new Action("5", "Boiling a bottle of watter", 500, null));
        actionCatalog.addAction(new Action("6", "Walking between Delft and Hague", 1750, null));

        return actionCatalog;
    }

    @Test
    void comparisonQuestionsGeneratorTest() {

    }

    @Test
    void generateComparisonQuestionFromActionTest() {
        ActionCatalog actionCatalog = createActionCatalog();
        Random random = new Random();
        //Pair<Question, String> questionAnswerPair = generateComparisonQuestionFromAction(actionCatalog, random);

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