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

    /**
     * Transorms a json String into a new Open Question
     * @param json string input
     * @return a new Open Question
     */
    public static OpenQuestion openQuestionParser(String json) {
        Scanner scanner = new Scanner(json).useDelimiter("; ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());

        return new OpenQuestion(question);
    }

    /**
     * Transorms a json String into a new Knowledge Question
     * @param json string input
     * @return a new Knowledge Question
     */
    public static KnowledgeQuestion knowledgeQuestionParser(String json) {
        Scanner scanner = new Scanner(json).useDelimiter("; ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());
        List<String> options = new ArrayList<>();
        while (scanner.hasNext()) {
            options.add(scanner.next());
        }

        return new KnowledgeQuestion(question, options);
    }

    /**
     * Transorms a json String into a new Comparison Question
     * @param json string input
     * @return a new Comparison Question
     */
    public static ComparisonQuestion comparisonQuestionParser(String json) {
        Scanner scanner = new Scanner(json).useDelimiter("; ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());
        List<Pair<String, String>> options = new ArrayList<>();
        while (scanner.hasNext()) {
            options.add(Pair.of(scanner.next(), scanner.next()));
        }

        return new ComparisonQuestion(question, options);
    }

    /**
     * Transorms a json String into a new Alternative Question
     * @param json string input
     * @return a new Alternative Question
     */
    public static AlternativeQuestion alternativeQuestionParser(String json) {
        System.out.println(json);
        Scanner scanner = new Scanner(json).useDelimiter("; ");
        Pair<String, String> question = Pair.of(scanner.next(), scanner.next());
        List<Pair<String, String>> options = new ArrayList<>();
        while (scanner.hasNext()) {
            options.add(Pair.of(scanner.next(), scanner.next()));
        }

        return new AlternativeQuestion(question, options);
    }
}