package server.controllers.GameControllers;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import server.services.GameServices.MultiplayerGameService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/{username}/MULTIPLAYER/{roomId}")
public class MultiplayerGameRoomController {

    private final MultiplayerGameService multiplayerGameService;

    /**
     * Constructor for the multiplayer game controller
     *
     * @param multiplayerGameService the service for the multiplayer  game features
     */
    public MultiplayerGameRoomController(MultiplayerGameService multiplayerGameService) {
        this.multiplayerGameService = multiplayerGameService;
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

    private static Map<Pair<Object, String>, Consumer<Boolean>> startingGameListeners = new HashMap<>();

    public static Map<Pair<Object, String>, Consumer<Boolean>> getListeners() {
        return startingGameListeners;
    }

    @GetMapping("/waitForGameToStart")
    public DeferredResult<ResponseEntity<Boolean>> waitForGameToStart(@PathVariable("username") String username, @PathVariable("roomId") String roomId) {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Boolean>>(50000L, noContent);

        var key = Pair.of(new Object(), roomId);
        startingGameListeners.put(key, q -> {
            res.setResult(ResponseEntity.ok(true));
        });
        res.onCompletion(() -> {
            startingGameListeners.remove(key);
        });

        return res;
    }

    private static Map<Pair<Object, String>, Consumer<Integer>> playerNumberListeners = new HashMap<>();

    public static Map<Pair<Object, String>, Consumer<Integer>> getPlayerNumberListeners() {
        return playerNumberListeners;
    }

    /**
     * Get the number of players in the multiplayer game
     * @param roomId    the id of the game that the user wants to join
     * @return give the number of players using asynchronous operation
     */
    @GetMapping("/getPlayerNumber")
    public DeferredResult<ResponseEntity<Integer>> getPlayerNumber(@PathVariable("roomId") String roomId) {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Integer>>(50000L, noContent);

        var key = Pair.of(new Object(), roomId);
        playerNumberListeners.put(key, q -> {
            res.setResult(ResponseEntity.ok(q));
            System.out.println("Am update result");
        });
        res.onCompletion(() -> {
            playerNumberListeners.remove(key);
        });

        return res;
    }

    /**
     * Removes a player(user) from the current multiplayer score leaderboard
     *
     * @param username the name of the removing player
     * @param roomId   the id of the game that the user wants to join
     */
    @GetMapping("/removePlayer")
    public void removePlayer(@PathVariable("username") String username, @PathVariable("roomId") String roomId) {
        System.out.println("The request is ok");
        this.multiplayerGameService.removePlayer(roomId, username);
    }

    /**
     * Gets the number of players in the current multiplayer room
     *
     * @param roomId the id of the game that the user wants to join
     * @return the number of players in terms of integer value
     */
    @GetMapping("/numPlayers")
    public Integer getNumPlayers(@PathVariable String roomId) {
        return (Integer) this.multiplayerGameService.getGame(roomId).getNumPlayers();
    }

    @MessageMapping("/emojis")  // /app/emojis
    @SendTo("/topic/emojis")
    public String addMessage(String chatEntry) {
        return chatEntry;
    }
}