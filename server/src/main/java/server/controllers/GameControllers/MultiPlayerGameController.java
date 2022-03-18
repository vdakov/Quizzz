package server.controllers.GameControllers;

import org.springframework.web.bind.annotation.*;
import server.services.GameServices.MultiplayerGameService;

@RestController
@RequestMapping("api/multiPlayer/{userName}")
public class MultiPlayerGameController {

    private MultiplayerGameService multiplayerGameService;

    public MultiPlayerGameController(MultiplayerGameService multiplayerGameService) {
        this.multiplayerGameService = multiplayerGameService;
    }

    @GetMapping("/createNewGame")
    public String createNewMultiplayerGame(@PathVariable("userName") String userName) {
        return multiplayerGameService.createNewMultiPlayerGame(userName);
    }

    // maybe a post request
    @GetMapping("/{roomId}/joinGame")
    public void joinMultiplayerGame(@PathVariable("userName") String userName, @PathVariable("roomId") String roomId) {
        multiplayerGameService.joinMultiPlayerGame(userName, roomId);
    }

    @GetMapping("/{gameId}/startGame")
    public void startMultiPlayerGame() {
        // need more thinking how to do this properly
    }

    @GetMapping("/getRandomRoomCode")
    public String getRandomRoomCode() {
        return multiplayerGameService.getMultiPlayerRandomGame();
    }

    @GetMapping("/{gameId}/{questionNumber}/getQuestion")
    public String getDesiredQuestion(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
        return multiplayerGameService.getMultiPlayerQuestion(gameId, questionNumber);
    }

    @PostMapping("/{gameId}/{questionNumber}/sendAnswer")
    public void updateAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                             @PathVariable("questionNumber") int questionNumber, @RequestBody String answer) {
        multiplayerGameService.updateMultiPlayerScore(userName, gameId, questionNumber, answer);
    }

    @GetMapping("/{gameId}/getScore")
    public int getScore(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId) {
        return multiplayerGameService.getMultiPlayerScore(userName, gameId);
    }



}
