package server.controllers.GameControllers;

import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
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

    @GetMapping("/{gameId}/{questionNumber}/getQuestionType")
    public String getDesiredQuestionType(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return singlePlayerGameService.getNeededQuestionType(gameId, questionNumber);
    }

    @GetMapping("/{gameId}/{questionNumber}/getQuestion")
    public String getDesiredQuestion(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return singlePlayerGameService.getNeededQuestion(gameId, questionNumber);
    }

    @GetMapping("/{gameId}/getScore")
    public int getScore(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId) {
        return singlePlayerGameService.getPlayerScore(userName, gameId);
    }

    @PostMapping("/{gameId}/{questionNumber}")
    public void updateAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                             @PathVariable("questionNumber") int questionNumber, @RequestBody String answer) {
        singlePlayerGameService.updateScore(userName, gameId, questionNumber, answer);
    }
}
