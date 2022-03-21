package server.controllers.GameControllers;

import org.springframework.web.bind.annotation.*;
import server.services.GameServices.SinglePlayerGameService;

@RestController
@RequestMapping("api/singlePlayer/{userName}")
public class SinglePlayerGameController {

    private SinglePlayerGameService singlePlayerGameService;

    public SinglePlayerGameController(SinglePlayerGameService singlePlayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
    }

    @GetMapping("/startNewGame")
    public String startNewGame(@PathVariable("userName") String userName) {
        return singlePlayerGameService.createNewSinglePlayerGame(userName);
    }

    @GetMapping("/{gameId}/{questionNumber}/getQuestion")
    public String getDesiredQuestion(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return singlePlayerGameService.getSinglePlayerQuestion(gameId, questionNumber);
    }

    @PostMapping("/{gameId}/{questionNumber}/updateAnswer")
    public void updateAnswer(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber, @RequestBody String answer) {
        singlePlayerGameService.updateSinglePlayerScore(gameId, questionNumber, answer);
    }

    @GetMapping("/{gameId}/getScore")
    public int getScore(@PathVariable("gameId") String gameId) {
        return singlePlayerGameService.getSinglePlayerScore(gameId);
    }

    @GetMapping("/{gameId}/{questionNumber}/getAnswer")
    public String getAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                            @PathVariable("questionNumber") int questionNumber) {
        return singlePlayerGameService.getAnswer(userName, gameId, questionNumber);
    }

}
