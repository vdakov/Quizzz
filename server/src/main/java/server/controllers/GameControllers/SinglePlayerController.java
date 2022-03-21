package server.controllers.GameControllers;

import org.springframework.web.bind.annotation.*;
import server.services.GameServices.GameService;

@RestController
@RequestMapping("api/singlePlayer")
public class SinglePlayerController {

    private GameService gameService;

    public SinglePlayerController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{userName}/createNewGame")
    public String createNewGame(@PathVariable("userName") String userName) {
        return gameService.createNewGame(userName);
    }

    @GetMapping("/{userName}/{gameId}/{questionNumber}/getQuestion")
    public String getDesiredQuestion(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return gameService.getNeededQuestion(gameId, questionNumber);
    }

    @GetMapping("/{userName}/{gameId}/getScore")
    public int getScore(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId) {
        return gameService.getPlayerScore(userName, gameId);
    }

    @PostMapping("/{userName}/{gameId}/{questionNumber}")
    public void updateAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                             @PathVariable("questionNumber") int questionNumber, @RequestBody String answer) {
        gameService.updateScore(userName, gameId, questionNumber, answer);
    }
}
