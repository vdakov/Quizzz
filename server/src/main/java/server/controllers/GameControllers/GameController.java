package server.controllers.GameControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.services.GameServices.MultiplayerGameService;
import server.services.GameServices.SinglePlayerGameService;

@RestController
@RequestMapping("api/{userName}/{gameType}")
public class GameController {

    private final SinglePlayerGameService singlePlayerGameService;
    private final MultiplayerGameService  multiplayerGameService;

    /**
     * Constructor for the game controller
     *
     * @param singlePlayerGameService the service for the singleplayer game features
     * @param multiplayerGameService  the service for the multiplayer  game features
     */
    public GameController(SinglePlayerGameService singlePlayerGameService, MultiplayerGameService multiplayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
        this.multiplayerGameService  = multiplayerGameService;
    }

    /**
     * Returns a response with the string whether the room was created or gives a corresponding error message
     *
     * @param userName the userName of the desired player
     * @param gameType the type of the game
     * @return the response entity with the needed data or the error message
     */
    @GetMapping("/createNewGame")
    public ResponseEntity<String> createNewGame(@PathVariable("userName") String userName, @PathVariable("gameType") String gameType) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                String roomId = singlePlayerGameService.createNewSinglePlayerGame(userName);
                if (roomId == null) {
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(roomId);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                String roomId = multiplayerGameService.createNewMultiPlayerGame(userName);
                if (roomId == null) {
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(roomId);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
