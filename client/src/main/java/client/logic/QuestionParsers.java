package client.logic;

import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionParsers {

    public static OpenQuestion openQuestionParser(String json) {
        Scanner scanner = new Scanner(json).useDelimiter(", ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());

        return new OpenQuestion(question);
    }

    public static KnowledgeQuestion knowledgeQuestionParser(String json) {
        Scanner scanner = new Scanner(json).useDelimiter(", ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());
        List<String> options = new ArrayList<>();
        while (scanner.hasNext()) {
            options.add(scanner.next());
        }

        return new KnowledgeQuestion(question, options);
    }

    public static ComparisonQuestion comparisonQuestionParser(String json) {
        Scanner scanner = new Scanner(json).useDelimiter(", ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());
        List<Pair<String, String>> options = new ArrayList<>();
        while (scanner.hasNext()) {
            options.add(Pair.of(scanner.next(), scanner.next()));
        }

        return new ComparisonQuestion(question, options);
    }

    public static AlternativeQuestion alternativeQuestionParser(String json) {
        Scanner scanner = new Scanner(json).useDelimiter(", ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());
        List<Pair<String, String>> options = new ArrayList<>();
        while (scanner.hasNext()) {
            options.add(Pair.of(scanner.next(), scanner.next()));
        }

        return new AlternativeQuestion(question, options);
    }
}