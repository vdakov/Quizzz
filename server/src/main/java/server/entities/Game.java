package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private String gameId;
    private List<Pair<Question, String>> gameQuestionsWithAnswers;
    private List<Pair<String, Integer>> player_Score;


    public Game(String gameId, List<Pair<Question, String>> gameQuestionsWithAnswers) {
        this.gameId = gameId;
        this.gameQuestionsWithAnswers = gameQuestionsWithAnswers;
        this.player_Score = new ArrayList<>();
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
        player_Score.add(Pair.of(user, 0));
    }

    public void removeUser(String userName) {
        for (int i = 0; i < player_Score.size(); i++) {
            if (player_Score.get(i).getKey().equals(userName)) {
                player_Score.remove(i);
            }
        }
    }

    public Question getQuestion(int number) {
        return gameQuestionsWithAnswers.get(number).getKey();
    }

    public String getQuestionAnswer(int number) {
        return gameQuestionsWithAnswers.get(number).getValue();
    }

    public int getPlayerScore(String userName) {
        for (Pair<String, Integer> score : player_Score) {
            if (score.getKey().equals(userName)) {
                return score.getValue();
            }
        }
        return -1;
    }

    public void updatePlayerScore(String userName, int addedScore) {
        for (int i = 0; i < player_Score.size(); i++) {
            if (player_Score.get(i).getKey().equals(userName)) {
                int newValue = player_Score.get(i).getValue() + addedScore;
                player_Score.remove(i);
                player_Score.add(Pair.of(userName, newValue));
            }
        }
    }

    public int getNumPlayers() {
        return this.player_Score.size();
    }


}
