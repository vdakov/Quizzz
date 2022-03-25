package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.entities.GameCatalog;
import server.entities.SinglePlayerGame;
import server.repositories.ActivityRepository;
import server.services.QuestionGenerator.QuestionGenerator;

import java.util.List;
import java.util.Random;

@Service
public class SinglePlayerGameService {

    private ActivityRepository activityRepository;
    private GameCatalog gameCatalog;

    public SinglePlayerGameService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        gameCatalog = GameCatalog.getGameCatalog();
    }

    public String createNewSinglePlayerGame(String userName) {
        String gameId = Util.getAlphaNumericString(10);
        ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

        List<Pair<Question, String>> questionList = null;
        try {
            questionList = QuestionGenerator.generateQuestions(actionCatalog, 21, 2, 7, new Random());
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
            return "GAME GENERATION FAILED";
        }
        SinglePlayerGame newGame = new SinglePlayerGame(gameId, userName, questionList);
        gameCatalog.addSinglePlayerGame(newGame);
        return gameId;
    }

    public String getSinglePlayerQuestion(String gameId, int number) {
        Question question = gameCatalog.getSinglePlayerGame(gameId).getQuestion(number);
        return (Util.getQuestionType(question) + ": " + question.toJsonString());
    }

    public int getSinglePlayerScore(String gameId) {
        return gameCatalog.getSinglePlayerGame(gameId).getPlayerScore();
    }

    public void updateSinglePlayerScore(String gameId, int questionNumber, String userAnswer) {
        if (userAnswer.equals(gameCatalog.getSinglePlayerGame(gameId).getQuestionAnswer(questionNumber))) {
            SinglePlayerGame singlePlayerGame = gameCatalog.getSinglePlayerGame(gameId);
            singlePlayerGame.setPlayerScore(singlePlayerGame.getPlayerScore() + 500);
        }
    }

    public String getAnswer(String userName, String gameId, int questionNumber) {
        return gameCatalog.getSinglePlayerGame(gameId).getQuestionAnswer(questionNumber);
    }


}

