package commons;

import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionsTest {

    @Test
    void getOpenQuestionTest() {
        OpenQuestion openQuestion = new OpenQuestion("How much energy does it take to mine 1 Bitcoin?");
        assertEquals("How much energy does it take to mine 1 Bitcoin?", openQuestion.getQuestion());
    }

    @Test
    void getKnowledgeQuestionOptionsTest() {
        String questionStatement = "How much energy does mining 1 Bitcoin take ?";
        List<String> options = new ArrayList<>();
        options.add("1500 kWh");
        options.add("1000 hWh");
        options.add("2000 hWh");

        List<String> expected = new ArrayList<>();
        expected.add("1500 kWh");
        expected.add("1000 hWh");
        expected.add("2000 hWh");

        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion(questionStatement, options);

        assertEquals(expected, knowledgeQuestion.getOptions());
    }

    @Test
    void toStringKnowledgeQuestionTest() {
        String questionStatement = "How much energy does mining 1 Bitcoin take ?";
        List<String> options = new ArrayList<>();
        options.add("1500 kWh");
        options.add("1000 hWh");
        options.add("2000 hWh");

        String expected = "Knowledge question statement: How much energy does mining 1 Bitcoin take ?\n" +
                "   Choice 0: 1500 kWh\n" +
                "   Choice 1: 1000 hWh\n" +
                "   Choice 2: 2000 hWh";

        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion(questionStatement, options);

        assertEquals(expected, knowledgeQuestion.toString());
    }

    @Test
    void getComparisonQuestionOptionsTest() {
        String questionStatement = "Which of the following activities take more energy?";
        List<String> options = new ArrayList<>();
        options.add("Mining 1 Bitcoin");
        options.add("Driving 1000km with a truck");
        options.add("Watching TV for 10 years");

        List<String> expected = new ArrayList<>();
        expected.add("Mining 1 Bitcoin");
        expected.add("Driving 1000km with a truck");
        expected.add("Watching TV for 10 years");

        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion(questionStatement, options);

        assertEquals(expected, knowledgeQuestion.getOptions());
    }

    @Test
    void toStringComparisonQuestionTest() {
        String questionStatement = "Which of the following activities take more energy?";
        List<String> options = new ArrayList<>();
        options.add("Mining 1 Bitcoin");
        options.add("Driving 1000km with a truck");
        options.add("Watching TV for 10 years");

        String expected = "Comparison question statement: Which of the following activities take more energy?\n" +
                "   Choice 0: Mining 1 Bitcoin\n" +
                "   Choice 1: Driving 1000km with a truck\n" +
                "   Choice 2: Watching TV for 10 years";

        ComparisonQuestion comparisonQuestion = new ComparisonQuestion(questionStatement, options);

        assertEquals(expected, comparisonQuestion.toString());
    }

    @Test
    void getAlternativeQuestionOptionsTest() {
        String questionStatement = "Which of the following has the closest consumption to using a computer for 20 years";
        List<String> options = new ArrayList<>();
        options.add("Mining 1 Bitcoin");
        options.add("Driving 1000km with a truck");
        options.add("Watching TV for 10 years");

        List<String> expected = new ArrayList<>();
        expected.add("Mining 1 Bitcoin");
        expected.add("Driving 1000km with a truck");
        expected.add("Watching TV for 10 years");

        AlternativeQuestion alternativeQuestion = new AlternativeQuestion(questionStatement, options);

        assertEquals(expected, alternativeQuestion.getOptions());
    }

    @Test
    void toStringAlternativeQuestionTest() {
        String questionStatement = "Which of the following has the closest consumption to using a computer for 20 years";
        List<String> options = new ArrayList<>();
        options.add("Mining 1 Bitcoin");
        options.add("Driving 1000km with a truck");
        options.add("Watching TV for 10 years");

        String expected = "Alternative question statement: Which of the following has the closest consumption to using a computer for 20 years\n" +
                "   Choice 0: Mining 1 Bitcoin\n" +
                "   Choice 1: Driving 1000km with a truck\n" +
                "   Choice 2: Watching TV for 10 years";

        AlternativeQuestion alternativeQuestion = new AlternativeQuestion(questionStatement, options);

        assertEquals(expected, alternativeQuestion.toString());
    }
}