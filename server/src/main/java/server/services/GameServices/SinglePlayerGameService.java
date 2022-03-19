package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.entities.Game;
import server.entities.GameCatalog;
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
        this.gameCatalog = new GameCatalog();
    }

    public String createNewGame(String userName) {
        String id = Util.getAlphaNumericString(10);
        ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

        List<Pair<Question, String>> questionList = null;
        try {
            questionList = QuestionGenerator.generateQuestions(actionCatalog, 20, 2, 7, new Random());
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
        }
        Game newGame = new Game(id, questionList);
        newGame.addUser(userName);
        gameCatalog.addGame(newGame);
        return id;
    }

    public String getNeededQuestion(String gameId, int number) {
        Question question = gameCatalog.getGame(gameId).getQuestion(number);
        return (Util.getQuestionType(question) + ": " + question.toJsonString());
    }

    public int getPlayerScore(String userName, String gameId) {
        return gameCatalog.getGame(gameId).getPlayerScore(userName);
    }

    public void updateScore(String userName, String gameId, int questionNumber, String userAnswer) {
        System.out.println(userName + "   " + gameId + "   " + questionNumber + "   " + userAnswer);
        if (userAnswer.equals(gameCatalog.getGame(gameId).getQuestionAnswer(questionNumber))) {
            gameCatalog.getGame(gameId).updatePlayerScore(userName, 500);
        }
    }

    public String getAnswer(String userName, String gameId, int questionNumber){
        return gameCatalog.getGame(gameId).getQuestionAnswer(questionNumber);
    }
}

