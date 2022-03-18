package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String                       gameId;
    private String                       roomCreator;
    private List<Pair<Question, String>> gameQuestionsWithAnswers;


    public Game(String gameId, String roomCreator, List<Pair<Question, String>> gameQuestionsWithAnswers) {
        this.gameId = gameId;
        this.roomCreator = roomCreator;
        this.gameQuestionsWithAnswers = gameQuestionsWithAnswers;
    }

    public String getGameId() {
        return this.gameId;
    }

    public String getRoomCreator() {
        return roomCreator;
    }

    public Question getQuestion(int number) {
        return this.gameQuestionsWithAnswers.get(number).getKey();
    }

    public String getQuestionAnswer(int number) {
        return this.gameQuestionsWithAnswers.get(number).getValue();
    }
}
