package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public String gameId;
    public List<Pair<Question, String>> gameQuestionsWithAnswers;
    public List<Pair<String, Integer>> scores;

    public Game(String gameId, List<Pair<Question, String>> gameQuestionsWithAnswers) {
        this.gameId = gameId;
        this.gameQuestionsWithAnswers = gameQuestionsWithAnswers;
        this.scores = new ArrayList<>();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public List<Pair<Question, String>> getGameQuestionsWithAnswers() {
        return gameQuestionsWithAnswers;
    }

    public void setGameQuestionsWithAnswers(List<Pair<Question, String>> gameQuestionsWithAnswers) {
        this.gameQuestionsWithAnswers = gameQuestionsWithAnswers;
    }

    public void addUser(String user) {
        scores.add(Pair.of(user, 0));
    }

    public Question getQuestion(int number) {
        return gameQuestionsWithAnswers.get(number).getKey();
    }

    public String getQuestionAnswer(int number) {
        return gameQuestionsWithAnswers.get(number).getValue();
    }

    public int getPlayerScore(String userName) {
        for(Pair<String, Integer> score : scores) {
            if(score.getKey().equals(userName)) {
                return score.getValue();
            }
        }
        return -1;
    }

    public void updatePlayerScore(String userName, int addedScore) {
        System.out.println(userName + "    " + addedScore);
        for(int i = 0; i < scores.size(); i++) {
            if(scores.get(i).getKey().equals(userName)) {
                scores.get(i).setValue(scores.get(i).getValue() + addedScore);
            }
        }
    }


}
