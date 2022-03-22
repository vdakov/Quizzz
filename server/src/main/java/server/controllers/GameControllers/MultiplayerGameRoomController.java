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
@RequestMapping("api/{userName}/MULTIPLAYER/{roomId}")
public class MultiplayerGameRoomController {

    private final SinglePlayerGameService singlePlayerGameService;
    private final MultiplayerGameService  multiplayerGameService;

    /**
     * Constructor for the game controller
     *
     * @param singlePlayerGameService the service for the singleplayer game features
     * @param multiplayerGameService  the service for the multiplayer  game features
     */
    public MultiplayerGameRoomController(SinglePlayerGameService singlePlayerGameService, MultiplayerGameService multiplayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
        this.multiplayerGameService  = multiplayerGameService;
    }

    /**
     * Allows a user to join a desired multiplayer game or gives a corresponding error message
     *
     * @param userName the userName of the player that wants to start the game
     * @param gameType the type of the game
     * @param roomId   the id of the game that the user wants to join
     * @return whether the player has successfully joined the room
     */
    @GetMapping("/joinGame")
    public ResponseEntity<Object> joinGame(@PathVariable("userName") String userName, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("MULTIPLAYER")) {
            try {
                return (multiplayerGameService.joinMultiPlayerGame(userName, roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
