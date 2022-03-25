package server.controllers.GameControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.services.GameServices.MultiplayerGameService;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

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
    public boolean joinMultiplayerGame(@PathVariable("userName") String userName, @PathVariable("roomId") String roomId) {
        multiplayerGameService.joinMultiPlayerGame(userName, roomId);
        return true;
    }

    @GetMapping("/{gameId}/startGame")
    public boolean startMultiPlayerGame(@PathVariable("gameId") String gameId) {
        multiplayerGameService.startMultiPlayerGame(gameId);
        listeners.forEach((k, l) -> l.accept(true));
        return true;
    }

    private HashMap<String, Consumer<Boolean>> listeners = new HashMap<>();

    @GetMapping("/{gameid}/waitForGameToStart")
    public DeferredResult<ResponseEntity<Boolean>> waitForGameToStart(@PathVariable("gameId") String gameId) {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Boolean>>(5000L, noContent);

        listeners.put(gameId, q -> {
            res.setResult(ResponseEntity.ok(q));
        });
        res.onCompletion(() -> {
            listeners.remove(gameId);
        });

        return res;
    }


    @GetMapping("/getRandomRoomCode")
    public String getRandomRoomCode() {
        return multiplayerGameService.getMultiPlayerRandomGame();
    }

    @GetMapping("/{gameId}/{questionNumber}/getQuestion")
    public String getDesiredQuestionMultiplayer(@PathVariable("gameId") String gameId, @PathVariable("questionNumber") int questionNumber) {
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

    @GetMapping("/{gameId}/{questionNumber}/getAnswer")
    public String getAnswer(@PathVariable("userName") String userName, @PathVariable("gameId") String gameId,
                            @PathVariable("questionNumber") int questionNumber) {
        return multiplayerGameService.getAnswer(userName, gameId, questionNumber);
    }

    @GetMapping("/getGameIds")
    public List<String> getGameIds(){
        return multiplayerGameService.getGameIds();
    }
}
