package server.controllers.GameControllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.services.GameServices.MultiplayerGameService;

@RestController
@RequestMapping("api/{username}/MULTIPLAYER/{roomId}")
public class MultiplayerGameRoomController {

    private final MultiplayerGameService  multiplayerGameService;

    /**
     * Constructor for the multiplayer game controller
     *
     * @param multiplayerGameService  the service for the multiplayer  game features
     */
    public MultiplayerGameRoomController(MultiplayerGameService multiplayerGameService) {
        this.multiplayerGameService  = multiplayerGameService;
    }

    /**
     * Allows a user to join a desired multiplayer game or gives a corresponding error message
     *
     * @param username the username of the player that wants to start the game
     * @param roomId   the id of the game that the user wants to join
     * @return whether the player has successfully joined the room
     */
    @GetMapping("/joinGame")
    public ResponseEntity<Object> joinGame(@PathVariable("username") String username, @PathVariable("roomId") String roomId) {
        try {
            return (multiplayerGameService.joinMultiPlayerGame(username, roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}
