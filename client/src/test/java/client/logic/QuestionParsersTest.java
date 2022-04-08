package client.logic;

import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionParsersTest {

    @Test
    void openQuestionParser() {
        OpenQuestion openQuestion =  QuestionParsers.openQuestionParser("1; 2; a; b; c; d; e; f");
        assertEquals("1", openQuestion.toString());
    }

    @Test
    void knowledgeQuestionParser() {
        KnowledgeQuestion knowledgeQuestion = QuestionParsers.knowledgeQuestionParser("1; 2; a; b");
        assertEquals("Knowledge question statement: 1\n" +
                "   Choice 0: a\n" +
                "   Choice 1: b", knowledgeQuestion.toString());
    }

    @Test
    void comparisonQuestionParser() {
        ComparisonQuestion comparisonQuestion = QuestionParsers.comparisonQuestionParser("1; 2; a; b; c; d; e; f");
        assertEquals("Comparison question statement: 1\n" +
                "   Choice 0: a\n" +
                "   Choice 1: c\n" +
                "   Choice 2: e", comparisonQuestion.toString());
    }

    @Test
    void alternativeQuestionParser() {
        AlternativeQuestion alternativeQuestion = QuestionParsers.alternativeQuestionParser("1; 2; a; b; c; d; e; f");
        assertEquals("Alternative question statement: 1\n" +
                "   Choice 0: a\n" +
                "   Choice 1: c\n" +
                "   Choice 2: e", alternativeQuestion.toString());
    }
}