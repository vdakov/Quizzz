

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

    @GetMapping("/createNewGame/{userName}")
    public String createNewGame(@PathVariable("userName") String userName) {
        String gameId = gameService.createNewGame(userName);
        System.out.println("CREATED GAME WITH ID " + gameId);
        return gameId;
    }

    @GetMapping("/joinGame/{userName}/{gameId}")
    public void joinGame(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId) {
        gameService.joinGame(userName, gameId);
        System.out.println(userName + " JOINED GAME " + gameId);
    }

    @GetMapping("/{gameId}/{questionNumber}")
    public String getDesiredQuestion(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return gameService.getNeededQuestion(gameId, questionNumber);
    }


    @PostMapping("/{gameId}/{userName}/{questionNumber}")
    public void updateAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                             @PathVariable("questionNumber") int questionNumber, @RequestBody String answer) {
        gameService.updateScore(userName, gameId, questionNumber, answer);
    }

    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable("gameId") String gameId) {
        return gameService.getGame(gameId);
    }


    @GetMapping("/{gameId}/numPlayers")
    public int getNumPlayer(@PathVariable("gameId") String gameId) {
        return gameService.getNumPlayers(gameId);
    }

    @GetMapping("/currentGames")
    public ArrayList<GameContainer> getCurrentGames() {

        gameService.getCurrentGames().cleanEmptyGames();

        ArrayList<GameContainer> currentGames = new ArrayList<>();
        for (int i = 0; i < gameService.getCurrentGames().getGameList().size(); i++) {

            currentGames.add(
                    new GameContainer(gameService.getCurrentGames()
                            .getGameList().get(i).getGameId(),
                            gameService.getCurrentGames().getGameList().
                                    get(i).getNumPlayers())
            );


        }


        return currentGames;
    }

    @GetMapping("/gameIDList")
    public ArrayList<String> gameIDList() {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < this.getCurrentGames().size(); i++) {
            ids.add(this.getCurrentGames().get(i).getGameId());
        }
        return ids;
    }

    @GetMapping("/removePlayer/{userName}/{gameId}")
    public void removePlayer(@PathVariable String userName, @PathVariable String gameId) {
        this.getGame(gameId).removeUser(userName);
    }

}

