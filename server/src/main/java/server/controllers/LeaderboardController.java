package server.controllers;

import commons.Leaderboard.LeaderboardEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.services.GameServices.LeaderboardService;
import server.services.GameServices.MultiplayerGameService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/leaderboard")
public class LeaderboardController {
    private LeaderboardService service;
    private MultiplayerGameService multiplayerGameService;

    public LeaderboardController(LeaderboardService service, MultiplayerGameService multiplayerGameService) {
        this.service = service;
        this.multiplayerGameService = multiplayerGameService;
    }

    /**
     * Gets the leaderboard for a specific room
     *
     * @param roomId the room to get its leaderboard from
     * @return a list with the entries, already sorted and ranked
     */
    @GetMapping("/{roomId}")
    public List<LeaderboardEntry> getLeaderboard(@PathVariable String roomId) {
        List<LeaderboardEntry> temp = service.getByRoomId(roomId);
        for (int i = 0; i < temp.size(); i++) temp.get(i).setRank(i + 1); //assign ranks
        return temp;
    }

    @PostMapping
    public LeaderboardEntry addOrUpdateLeaderboardEntry(@RequestBody LeaderboardEntry e) {
        return service.addOrUpdateLeaderboardEntry(e);
    }

    @DeleteMapping("/remove/{roomId}")
    public void removeEntries(@PathVariable String roomId) {
        service.removeEntries(roomId);
    }

    //This is a HashMap that maps the roomId to a list of listeners for that room.
    static private final HashMap<String, ArrayList<Consumer<List<LeaderboardEntry>>>> listeners = new HashMap<>();

    /**
     * This method creates a listener to eventually return the leaderboard and adds it to the list of listeners of that room
     *
     * @param roomId the room for which to get the leaderboard
     * @return a list of LeaderboardEntry's in order of rank
     */
    @GetMapping("/filledLeaderboard/{roomId}")
    public DeferredResult<ResponseEntity<List<LeaderboardEntry>>> waitForFilledLeaderboard(@PathVariable("roomId") String roomId) {
        System.out.println("Waiting for leaderboard");
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<List<LeaderboardEntry>>>(50000L, noContent);

        Consumer<List<LeaderboardEntry>> listener = q -> res.setResult(ResponseEntity.ok(q));

        if (!listeners.containsKey(roomId)) listeners.put(roomId, new ArrayList<>());

        listeners.get(roomId).add(listener);
        res.onCompletion(() -> listeners.get(roomId).remove(listener));

        return res;
    }

    /**
     * Checks if everyone in a particular room has submitted their score to the server. If they have, then everyone will be sent the leaderboard
     *
     * @param roomId
     */
    @GetMapping("/checkLeaderboardFilled/{roomId}")
    public void checkLeaderboardFilled(@PathVariable("roomId") String roomId) {
        System.out.println("Checking if filled");
        ArrayList<Consumer> toAccept = new ArrayList<>();
        var entries = service.getByRoomId(roomId); //all of the leaderboard entries in the DB for this room

        if (entries.size() == multiplayerGameService.getGame(roomId).getNumPlayers()) { //make sure everyone has submitted their score
            listeners.get(roomId).forEach(listener -> toAccept.add(listener));

            for (Consumer c : toAccept) { //this avoids a ConcurrentModificationException
                c.accept(entries);
            }

            //remove the listeners, and clear the entries from the DB
            listeners.remove(roomId);
            service.removeEntries(roomId);
        }

    }
}
