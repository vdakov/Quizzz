package client.logic;

import commons.Questions.KnowledgeQuestion;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionParsers {

    public static KnowledgeQuestion knowledgeQuestionParser(String json) {
        System.out.println(json);
        Scanner scanner = new Scanner(json);
        Scanner questionScanner = new Scanner(scanner.nextLine()).useDelimiter("Question :").useDelimiter(", ");
        Pair<String, String> question = Pair.of(questionScanner.next(), questionScanner.next());
        scanner.nextLine();
        List<String> options = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            options.add(scanner.nextLine());
        }


        return new KnowledgeQuestion(question, options);
    }
}
//{"question":{"Which one of the following is the energy consumption of power usage of ICE train per 1 km?":"58/train.png"},"options":["20000","32200","22800"]}