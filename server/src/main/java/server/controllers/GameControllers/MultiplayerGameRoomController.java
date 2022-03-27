package server.controllers.GameControllers;

import commons.GameContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import server.entities.MultiplayerRoom;
import server.services.GameServices.MultiplayerGameService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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

    private static Map<Object, Consumer<String>> listeners = new HashMap<>();

    public static Map<Object, Consumer<String>> getListeners() {
        return listeners;
    }

    @GetMapping("/{gameId}/waitForGameToStart")
    public DeferredResult<ResponseEntity<String>> waitForGameToStart(@PathVariable("gameId") String gameId) {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<String>>(50000L, noContent);

        var key = new Object();
        listeners.put(key, q -> {
            System.out.println("AM reusit sa dau update");
            res.setResult(ResponseEntity.ok(q));
        });
//        res.onCompletion(() -> {
//            listeners.remove(key);
//        });

        System.out.println("Listeners size: " + listeners.size());

        return res;
    }

    @GetMapping("/getGames")
    public List<GameContainer> getGameIds() {
        return multiplayerGameService.getGameIds();
    }

    @GetMapping("/{gameId}/removePlayer")
    public void removePlayer(@PathVariable String userName, @PathVariable String gameId) {
        System.out.println("Hello");
        this.getGame(gameId).removePlayer(userName);
    }

    @GetMapping("/{gameId}")
    public MultiplayerRoom getGame(@PathVariable String gameId) {
        return this.multiplayerGameService.getGame(gameId);
    }

    @GetMapping("/{gameId}/numPlayers")
    public Integer getNumPlayers(@PathVariable String gameId) {
        return (Integer) this.getGame(gameId).getNumPlayers();
    }

}
