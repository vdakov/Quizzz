package commons.Questions;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlternativeQuestionTest {

    private final Question question = new ComparisonQuestion(Pair.of("1", "2"), List.of(Pair.of("a", "b"),
            Pair.of("c","d"), Pair.of("e", "f")));

    @Test
    void testToString() {
        assertEquals("Comparison question statement: 1\n" +
                "   Choice 0: a\n" +
                "   Choice 1: c\n" +
                "   Choice 2: e", question.toString());
    }

    @Test
    void toJsonString() {
        assertEquals("1; 2; a; b; c; d; e; f", question.toJsonString());
    }
}