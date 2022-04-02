package server.controllers.GameControllers;

import commons.GameContainer;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import server.services.GameServices.MultiplayerGameService;
import server.services.GameServices.SingleplayerGameService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/{username}/{gameType}")
public class GameController {

    private final SingleplayerGameService singlePlayerGameService;
    private final MultiplayerGameService  multiplayerGameService;

    /**
     * Constructor for the game controller
     *
     * @param singlePlayerGameService the service for the singleplayer game features
     * @param multiplayerGameService  the service for the multiplayer  game features
     */
    public GameController(SingleplayerGameService singlePlayerGameService, MultiplayerGameService multiplayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
        this.multiplayerGameService  = multiplayerGameService;
    }

    /**
     * Returns a response with the string whether the room was created or gives a corresponding error message
     *
     * @param username the username of the desired player
     * @param gameType the type of the game
     * @return the response entity with the needed data or the error message
     */
    @GetMapping("/createNewRoom")
    public ResponseEntity<String> createNewGame(@PathVariable("username") String username, @PathVariable("gameType") String gameType) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                String roomId = singlePlayerGameService.createNewSinglePlayerGame(username);
                if (roomId == null) {
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
                }

                return ResponseEntity.ok(roomId);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                String roomId = multiplayerGameService.createNewMultiPlayerGame(username);
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

    @GetMapping("/getRandomRoom")
    public ResponseEntity<String> getRandomRoom(@PathVariable("username") String username, @PathVariable("gameType") String gameType) {
        if (gameType.equals("MULTIPLAYER")) {
            try {
                String roomId = multiplayerGameService.getMultiPlayerRandomRoom();
                if (roomId == null) {
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
                }

                return ResponseEntity.ok(roomId);
            } catch (Exception e) {
                System.out.println("An exception occurred when trying to join the room");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/getGames")
    public List<GameContainer> getGameIds() {
        return multiplayerGameService.getGameIds();
    }

    private static Map<Object, Consumer<GameContainer>> activeRoomsListeners = new HashMap<>();

    public static Map<Object, Consumer<GameContainer>> getActiveRoomsListeners() {
        return activeRoomsListeners;
    }

    @GetMapping("/updateRooms")
    public DeferredResult<ResponseEntity<GameContainer>> updateRoomList() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<GameContainer>>(50000L, noContent);

        var key = new Object();
        activeRoomsListeners.put(key, q -> {
            res.setResult(ResponseEntity.ok(q));
        });
        res.onCompletion(() -> {
            activeRoomsListeners.remove(key);
        });

        return res;
    }
}
