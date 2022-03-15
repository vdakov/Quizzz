package commons;

import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionsTest {

    @Test
    void getOpenQuestionTest() {
        OpenQuestion openQuestion = new OpenQuestion(Pair.of("How much energy does it take to mine 1 Bitcoin?", "path"));
        assertEquals("How much energy does it take to mine 1 Bitcoin?", openQuestion.getQuestion().getKey());
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

        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion(Pair.of(questionStatement, "path"), options);

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

        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion(Pair.of(questionStatement, "path"), options);

        assertEquals(expected, knowledgeQuestion.toString());
    }

    @Test
    void getComparisonQuestionOptionsTest() {
        String questionStatement = "Which of the following activities take more energy?";
        List<Pair<String, String>> options = new ArrayList<>();
        options.add(Pair.of("Mining 1 Bitcoin", "path"));
        options.add(Pair.of("Driving 1000km with a truck", "path"));
        options.add(Pair.of("Watching TV for 10 years", "path"));

        List<Pair<String, String>> expected = new ArrayList<>();
        expected.add(Pair.of("Mining 1 Bitcoin", "path"));
        expected.add(Pair.of("Driving 1000km with a truck", "path"));
        expected.add(Pair.of("Watching TV for 10 years", "path"));

        ComparisonQuestion comparisonQuestion = new ComparisonQuestion(Pair.of(questionStatement, "path"), options);

        assertEquals(expected, comparisonQuestion.getOptions());
    }

    @Test
    void toStringComparisonQuestionTest() {
        String questionStatement = "Which of the following activities take more energy?";
        List<Pair<String, String>> options = new ArrayList<>();
        options.add(Pair.of("Mining 1 Bitcoin", "path"));
        options.add(Pair.of("Driving 1000km with a truck", "path"));
        options.add(Pair.of("Watching TV for 10 years", "path"));

        String expected = "Comparison question statement: Which of the following activities take more energy?\n" +
                "   Choice 0: Mining 1 Bitcoin\n" +
                "   Choice 1: Driving 1000km with a truck\n" +
                "   Choice 2: Watching TV for 10 years";

        ComparisonQuestion comparisonQuestion = new ComparisonQuestion(Pair.of(questionStatement, "path"), options);

        assertEquals(expected, comparisonQuestion.toString());
    }

    @Test
    void getAlternativeQuestionOptionsTest() {
        String questionStatement = "Which of the following has the closest consumption to using a computer for 20 years";
        List<Pair<String, String>> options = new ArrayList<>();
        options.add(Pair.of("Mining 1 Bitcoin", "path"));
        options.add(Pair.of("Driving 1000km with a truck", "path"));
        options.add(Pair.of("Watching TV for 10 years", "path"));

        List<Pair<String, String>> expected = new ArrayList<>();
        expected.add(Pair.of("Mining 1 Bitcoin", "path"));
        expected.add(Pair.of("Driving 1000km with a truck", "path"));
        expected.add(Pair.of("Watching TV for 10 years", "path"));

        AlternativeQuestion alternativeQuestion = new AlternativeQuestion(Pair.of(questionStatement, "path"), options);

        assertEquals(expected, alternativeQuestion.getOptions());
    }

    @Test
    void toStringAlternativeQuestionTest() {
        String questionStatement = "Which of the following has the closest consumption to using a computer for 20 years";
        List<Pair<String, String>> options = new ArrayList<>();
        options.add(Pair.of("Mining 1 Bitcoin", "path"));
        options.add(Pair.of("Driving 1000km with a truck", "path"));
        options.add(Pair.of("Watching TV for 10 years", "path"));

        String expected = "Alternative question statement: Which of the following has the closest consumption to using a computer for 20 years\n" +
                "   Choice 0: Mining 1 Bitcoin\n" +
                "   Choice 1: Driving 1000km with a truck\n" +
                "   Choice 2: Watching TV for 10 years";

        AlternativeQuestion alternativeQuestion = new AlternativeQuestion(Pair.of(questionStatement, "path"), options);

        assertEquals(expected, alternativeQuestion.toString());
    }
}