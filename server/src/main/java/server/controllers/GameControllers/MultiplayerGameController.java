

package server.controllers.GameControllers;

import commons.GameContainer;
import org.springframework.web.bind.annotation.*;
import server.entities.Game;
import server.services.GameServices.GameService;
import java.util.ArrayList;


@RestController
@RequestMapping("api/multiplayer")
public class MultiplayerGameController {

    private GameService gameService;

    public MultiplayerGameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Endpoint for creating a new game lobby
     * @param userName username of the player joining
     * @return the game ID of the created game
     */
    @GetMapping("/createNewGame/{userName}")
    public String createNewGame(@PathVariable("userName") String userName) {
        String gameId = gameService.createNewGame(userName);
        System.out.println("CREATED GAME WITH ID " + gameId);
        return gameId;
    }

    /**
     * Endpoint for joining a game
     * @param userName The username of the player joining
     * @param gameId the id of the game being joined
     */
    @GetMapping("/joinGame/{userName}/{gameId}")
    public void joinGame(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId) {
        gameService.joinGame(userName, gameId);
        System.out.println(userName + " JOINED GAME " + gameId);
    }

    /**
     * Endpoint for returning a new question
     * @param gameId the id of the game to give a question to
     * @param questionNumber the number of the question requested
     * @return
     */
    @GetMapping("/{gameId}/{questionNumber}")
    public String getDesiredQuestion(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return gameService.getNeededQuestion(gameId, questionNumber);
    }

    /**
     * Updates an answer of a player
     * @param userName the name of the player that gave the answer
     * @param gameId the name of the game ID
     * @param questionNumber the question num answered
     * @param answer the amswer given
     */
    @PostMapping("/{gameId}/{userName}/{questionNumber}")
    public void updateAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                             @PathVariable("questionNumber") int questionNumber, @RequestBody String answer) {
        gameService.updateScore(userName, gameId, questionNumber, answer);
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable("gameId") String gameId) {
        return gameService.getGame(gameId);
    }

    /**
     * Returns the number of players in the selected game
     * @param gameId the id from which to select players
     * @return int of the num of players
     */
    @GetMapping("/{gameId}/numPlayers")
    public int getNumPlayer(@PathVariable("gameId") String gameId) {
        return gameService.getNumPlayers(gameId);
    }

    /**
     * Returns a list of all gamecontainers of the games being currently played and stored in the GameCatalog
     * @return a list of GameContainers
     */
    @GetMapping("/currentGames")
    public ArrayList<GameContainer> getCurrentGames() {

        return gameService.getCurrentGames();
    }

    /**
     * Endpoint of a list of all game IDs
     * @return a list of  Strings with all game IDs
     */
    @GetMapping("/gameIDList")
    public ArrayList<String> gameIDList() {
        return gameService.gameIDList();
    }

    /**
     * Endpoint to remove a player
     * @param userName the username of the removed player
     * @param gameId the id from which they are removed
     */
    @GetMapping("/removePlayer/{userName}/{gameId}")
    public void removePlayer(@PathVariable String userName, @PathVariable String gameId) {
        this.getGame(gameId).removeUser(userName);
    }

    /**
     * gets a random player from the game to make into an owner
     * @param gameId the id of the game to retrieve a new player from
     */
    @GetMapping("/{gameId}/newGameOwner")
    public void getNewGameOwner( @PathVariable String gameId) {
        this.getGame(gameId).getRandomPlayer();
    }

}

