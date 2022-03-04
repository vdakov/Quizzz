package server.services.QuestionGenerator;

import commons.Questions.OpenQuestion;
import commons.Questions.Question;
import org.springframework.data.util.Pair;
import server.entities.Actions.Action;

import java.util.ArrayList;
import java.util.List;

import static server.entities.Actions.ActionCatalog.*;
import static server.services.QuestionGenerator.QuestionGenerator.makeQuestionFromStatement;

public class OpenQuestionGenerator {

    public static List<Pair<Question, String>> openQuestionsGenerator(Integer numberOfNeededQuestions, List<Action> normalActions, List<Action> smartActions) {
        List<Pair<Question, String>> questionList = new ArrayList<>();

        for(int i = 0; i < numberOfNeededQuestions; i++) {
            questionList.add(generateOpenQuestionFromAction(shiftActionsLeft((i % 2 == 0) ? smartActions : normalActions)));
        }

        return questionList;
    }

    public static Pair<Question, String> generateOpenQuestionFromAction(Action action) {
        OpenQuestion openQuestion = new OpenQuestion(makeQuestionFromStatement(action.getTitle()));
        String correctAnswer = action.getConsumption();

        return Pair.of(openQuestion, correctAnswer);
    }
}
