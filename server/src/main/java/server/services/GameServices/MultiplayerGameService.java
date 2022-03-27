package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.GameContainer;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.entities.GameCatalog;
import server.entities.MultiPlayerGame;
import server.repositories.ActivityRepository;
import server.services.QuestionGenerator.QuestionGenerator;

import java.util.List;
import java.util.Random;

@Service
public class MultiplayerGameService {

    private ActivityRepository activityRepository;
    private GameCatalog gameCatalog;

    public MultiplayerGameService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        gameCatalog = GameCatalog.getGameCatalog();

        createNewMultiplayerRandomGame();
    }

    public void createNewMultiplayerRandomGame() {
        String gameId = Util.getAlphaNumericString(10);
        ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

        List<Pair<Question, String>> questionList = null;
        try {
            questionList = QuestionGenerator.generateQuestions(actionCatalog, 21, 2, 7, new Random());
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
            return;
        }
        MultiPlayerGame multiPlayerRandomGame = new MultiPlayerGame(gameId, "GROUP30ooPP", questionList);
        gameCatalog.setMultiplayerRandomRoom(multiPlayerRandomGame);
    }

    public String getMultiPlayerRandomGame() {
        return gameCatalog.getMultiplayerRandomRoom().getGameId();
    }

    public String createNewMultiPlayerGame(String userName) {
        String gameId = Util.getAlphaNumericString(10);
        ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

        List<Pair<Question, String>> questionList = null;
        try {
            questionList = QuestionGenerator.generateQuestions(actionCatalog, 20, 2, 7, new Random());
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
            return null;
        }
        MultiPlayerGame multiPlayerGame = new MultiPlayerGame(gameId, userName, questionList);
        gameCatalog.addMultiPlayerGame(multiPlayerGame);
        return gameId;
    }

    public boolean joinMultiPlayerGame(String userName, String roomId) {
        System.out.println("RoomId:    " + gameCatalog.getMultiPlayerGame(roomId));
        if(gameCatalog.checkUsernameExists(userName, roomId)){
            System.out.println("UserName exists!!!");
            return false;
        }
        gameCatalog.getMultiPlayerGame(roomId).addUser(userName);
        return true;
    }

    public String getMultiPlayerQuestion(String gameId, int number) {
        Question question = gameCatalog.getMultiPlayerGame(gameId).getQuestion(number);
        return (Util.getQuestionType(question) + ": " + question.toJsonString());
    }

    public void updateMultiPlayerScore(String userName, String gameId, int questionNumber, String userAnswer) {
        if (userAnswer.equals(gameCatalog.getMultiPlayerGame(gameId).getQuestionAnswer(questionNumber))) {
            MultiPlayerGame multiPlayerGame = gameCatalog.getMultiPlayerGame(gameId);
            multiPlayerGame.updatePlayerScore(userName, 500);
        }
    }

    public int getMultiPlayerScore(String userName, String gameId) {
        return gameCatalog.getMultiPlayerGame(gameId).getPlayerScore(userName);
    }

    public void startMultiPlayerGame(String gameId) {
        gameCatalog.getMultiPlayerGame(gameId).setGameStatusOngoing();
    }

    public String getAnswer(String userName, String gameId, int questionNumber) {
        return gameCatalog.getMultiPlayerGame(gameId).getQuestionAnswer(questionNumber);
    }

    public List<GameContainer> getGameIds() {
        this.gameCatalog.cleanEmptyGames();
        return gameCatalog.getWaitingMultiplayerGames();
    }

    public MultiPlayerGame getGame(String gameId) {
        return this.gameCatalog.getMultiPlayerGame(gameId);
    }

    public void removePlayer(String gameId, String userName) {
        this.getGame(gameId).removePlayer(userName);
    }
}
