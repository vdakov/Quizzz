package server.entities;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class SinglePlayerGame extends Game {

    private int playerScore;

    public SinglePlayerGame(String gameId, String roomCreator, List<Pair<Question, String>> gameQuestionsWithAnswers) {
        super(gameId, roomCreator, gameQuestionsWithAnswers);
        this.playerScore = 0;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }
}
