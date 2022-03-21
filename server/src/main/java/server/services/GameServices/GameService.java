package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.GameContainer;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.entities.Game;
import server.entities.GameCatalog;
import server.repositories.ActivityRepository;
import server.services.QuestionGenerator.QuestionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private ActivityRepository activityRepository;
    private GameCatalog gameCatalog;

    public GameService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        this.gameCatalog = new GameCatalog();
    }

    /**
     * Creates new game for the provided user
     *
     * @param userName the provided user string
     * @return the game ID of the new game
     */
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

    /**
     * Returns the requested question for the given game
     *
     * @param gameId the game for the question to be given to
     * @param number the question number that is being asked for
     * @return
     */
    public String getNeededQuestion(String gameId, int number) {
        Question question = gameCatalog.getGame(gameId).getQuestion(number);
        return (Util.getQuestionType(question) + ": " + question.toJsonString());
    }

    /**
     * Returns the score of a given player
     *
     * @param userName the name of the player evaluated
     * @param gameId   the game that player belongs to
     * @return an integer of their score
     */
    public int getPlayerScore(String userName, String gameId) {
        return gameCatalog.getGame(gameId).getPlayerScore(userName);
    }

    /**
     * Updates a player's score
     *
     * @param userName       the username of the updated playert
     * @param gameId         the id of the game they belong to
     * @param questionNumber the question number of the game
     * @param userAnswer     the answer they gave
     */
    public void updateScore(String userName, String gameId, int questionNumber, String userAnswer) {
        System.out.println(userName + "   " + gameId + "   " + questionNumber + "   " + userAnswer);
        if (userAnswer.equals(gameCatalog.getGame(gameId).getQuestionAnswer(questionNumber))) {
            gameCatalog.getGame(gameId).updatePlayerScore(userName, 500);
        }
    }


    public int getNumPlayers(String gameId) {
        return gameCatalog.getGame(gameId).getNumPlayers();
    }

    public Game getGame(String gameId) {
        return gameCatalog.getGame(gameId);
    }

    /**
     * Service method for a user to join a game
     *
     * @param userName the username of the user joining
     * @param gameId   the game they are joining
     * @return the Game object of that game
     */
    public Game joinGame(String userName, String gameId) {
        gameCatalog.getGame(gameId).addUser(userName);
        return gameCatalog.getGame(gameId);
    }

    /**
     * Returns a container class list of all the current games
     *
     * @return GameContainer Arraylist
     */
    public ArrayList<GameContainer> getCurrentGames() {

        this.gameCatalog.cleanEmptyGames();

        ArrayList<GameContainer> currentGames = new ArrayList<>();
        for (int i = 0; i < this.gameCatalog.getGameList().size(); i++) {

            currentGames.add(
                    new GameContainer(this.gameCatalog
                            .getGameList().get(i).getGameId(),
                            this.gameCatalog.getGameList().
                                    get(i).getNumPlayers())
            );
        }
        return currentGames;
    }

    /**
     * Returns a list of all curent game IDs being played
     *
     * @return String Arraylist
     */
    public ArrayList<String> gameIDList() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < this.getCurrentGames().size(); i++) {
            ids.add(this.getCurrentGames().get(i).getGameId());
        }
        return ids;
    }

}

