package server.controllers.GameControllers;

import org.springframework.web.bind.annotation.*;
import server.services.GameServices.SinglePlayerGameService;

@RestController
@RequestMapping("api/singlePlayer/{userName}")
public class SinglePlayerController {

    private SinglePlayerGameService singlePlayerGameService;

    public SinglePlayerController(SinglePlayerGameService singlePlayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
    }

    @GetMapping("/createNewGame")
    public String createNewGame(@PathVariable("userName") String userName) {
        return singlePlayerGameService.createNewGame(userName);
    }

    @GetMapping("/{gameId}/{questionNumber}/getQuestion")
    public String getDesiredQuestion(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return singlePlayerGameService.getNeededQuestion(gameId, questionNumber);
    }

    @GetMapping("/{gameId}/getScore")
    public int getScore(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId) {
        return singlePlayerGameService.getPlayerScore(userName, gameId);
    }

    @PostMapping("/{gameId}/{questionNumber}/getAnswer")
    public String updateAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                             @PathVariable("questionNumber") int questionNumber, @RequestBody String answer) {
        singlePlayerGameService.updateScore(userName, gameId, questionNumber, answer);
        return singlePlayerGameService.getAnswer(userName, gameId, questionNumber);
    }
}
