package commons.Questions;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnowledgeQuestionTest {
    private final Question question = new KnowledgeQuestion(Pair.of("1", "2"), List.of("a", "b"));



    @Test
    void testToString() {
       // Question question = new KnowledgeQuestion(Pair.of("1", "2"), List.of("a", "b"));
        assertEquals("Knowledge question statement: 1\n" +
                "   Choice 0: a\n" +
                "   Choice 1: b", question.toString());
    }

    @Test
    void toJsonString() {
        assertEquals("1; 2; a; b", question.toJsonString());
    }
}