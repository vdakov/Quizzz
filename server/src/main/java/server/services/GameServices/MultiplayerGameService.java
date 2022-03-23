package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.entities.RoomCatalog;
import server.entities.MultiplayerRoom;
import server.repositories.ActivityRepository;
import server.services.QuestionGenerator.QuestionGenerator;

import java.util.List;
import java.util.Random;

@Service
public class MultiplayerGameService {

    private ActivityRepository activityRepository;
    private RoomCatalog roomCatalog;

    public MultiplayerGameService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        roomCatalog = RoomCatalog.getGameCatalog();

        createNewMultiplayerRandomGame();
    }

    public void createNewMultiplayerRandomGame() {
        String gameId = Util.getAlphaNumericString(10);
        ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

        List<Pair<Question, String>> questionList = null;
        try {
            questionList = QuestionGenerator.generateQuestions(actionCatalog, 20, 2, 7, new Random());
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
            return;
        }
        MultiplayerRoom multiPlayerRandomGame = new MultiplayerRoom(gameId, "GROUP30ooPP", questionList);
        roomCatalog.setMultiplayerRandomRoom(multiPlayerRandomGame);
    }

    public String getMultiPlayerRandomGame() {
        return roomCatalog.getMultiplayerRandomRoom().getGameId();
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
        MultiplayerRoom multiPlayerGame = new MultiplayerRoom(gameId, userName, questionList);
        roomCatalog.addMultiplayerGame(multiPlayerGame);
        return gameId;
    }

    public boolean joinMultiPlayerGame(String userName, String roomId) {
        System.out.println("RoomId:    " + roomCatalog.getMultiPlayerGame(roomId));
        roomCatalog.getMultiPlayerGame(roomId).addPlayer(userName);
        return true;
    }

    public Question getMultiPlayerQuestion(String gameId, int number) {
        Question question = roomCatalog.getMultiPlayerGame(gameId).getQuestion(number);
        System.out.println((Util.getQuestionType(question) + ": " + question.toJsonString()));
        return question;
    }

    public void updateMultiPlayerScore(String userName, String gameId, int questionNumber, String userAnswer) {
        if (userAnswer.equals(roomCatalog.getMultiPlayerGame(gameId).getQuestionAnswer(questionNumber))) {
            MultiplayerRoom multiPlayerGame = roomCatalog.getMultiPlayerGame(gameId);
            multiPlayerGame.updatePlayerScore(userName, 500);
        }
    }

    public Integer getMultiPlayerScore(String userName, String gameId) {
        return roomCatalog.getMultiPlayerGame(gameId).getPlayerScore(userName);
    }

    public boolean startMultiPlayerGame(String gameId) {
        roomCatalog.getMultiPlayerGame(gameId).setGameStatusOngoing();
        return true;
    }

    public String getMultiPlayerAnswer(String userName, String gameId, int questionNumber) {
        return roomCatalog.getMultiPlayerGame(gameId).getQuestionAnswer(questionNumber);
    }
}
