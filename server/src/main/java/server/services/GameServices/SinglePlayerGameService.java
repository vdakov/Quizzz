package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.entities.RoomCatalog;
import server.entities.SingleplayerRoom;
import server.repositories.ActivityRepository;
import server.services.QuestionGenerator.QuestionGenerator;

import java.util.List;
import java.util.Random;

@Service
public class SinglePlayerGameService {

    private ActivityRepository activityRepository;
    private RoomCatalog roomCatalog;

    public SinglePlayerGameService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        roomCatalog = RoomCatalog.getGameCatalog();
    }

    public String createNewSinglePlayerGame(String userName) {
        String gameId               = Util.getAlphaNumericString(10);
        ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

        List<Pair<Question, String>> questionList = null;
        try {
            questionList = QuestionGenerator.generateQuestions(actionCatalog, 20, 2, 7, new Random());
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
            return "GAME GENERATION FAILED";
        }
        SingleplayerRoom newGame = new SingleplayerRoom(gameId, userName, questionList);
        roomCatalog.addSingleplayerGame(newGame);
        return gameId;
    }

    public boolean startSinglePlayerGame(String userName, String roomId) {
        return true;
    }

    public Question getSinglePlayerQuestion(String gameId, int number) {
        Question question = roomCatalog.getSinglePlayerGame(gameId).getQuestion(number);
        System.out.println((Util.getQuestionType(question) + ": " + question.toJsonString()));
        return question;
    }

    public Integer getSinglePlayerScore(String gameId) {
        return roomCatalog.getSinglePlayerGame(gameId).getPlayerScore();
    }

    public void updateSinglePlayerScore(String gameId, int questionNumber, String userAnswer) {
        if (userAnswer.equals(roomCatalog.getSinglePlayerGame(gameId).getQuestionAnswer(questionNumber))) {
            SingleplayerRoom singlePlayerGame = roomCatalog.getSinglePlayerGame(gameId);
            singlePlayerGame.setPlayerScore(singlePlayerGame.getPlayerScore() + 500);
        }
    }

    public String getSinglePlayerAnswer(String userName, String gameId, int questionNumber) {
        return roomCatalog.getSinglePlayerGame(gameId).getQuestionAnswer(questionNumber);
    }
}

