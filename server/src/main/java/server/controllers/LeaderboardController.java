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

    static private final HashMap<String, ArrayList<Consumer<List<LeaderboardEntry>>>> listeners = new HashMap<>();

    @GetMapping("/filledLeaderboard/{roomId}")
    public DeferredResult<ResponseEntity<List<LeaderboardEntry>>> waitForFilledLeaderboard(@PathVariable("roomId") String roomId) {
        System.out.println("Waiting for leaderboard");
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<List<LeaderboardEntry>>>(50000L, noContent);
        Consumer<List<LeaderboardEntry>> listener = q -> res.setResult(ResponseEntity.ok(q));

        if (!listeners.containsKey(roomId)) listeners.put(roomId, new ArrayList<>());

        listeners.get(roomId).add(listener);
        res.onCompletion(() -> {
            listeners.get(roomId).remove(listener);
        });

        return res;
    }

    @GetMapping("/checkLeaderboardFilled/{roomId}")
    public void checkLeaderboardFilled(@PathVariable("roomId") String roomId) {
        System.out.println("Checking if filled");
        ArrayList<Consumer> toAccept = new ArrayList<>();
        var entries = service.getByRoomId(roomId);
        if (entries.size() == multiplayerGameService.getGame(roomId).getNumPlayers()) {
            listeners.get(roomId).forEach(listener -> toAccept.add(listener));

            for (Consumer c : toAccept) {
                c.accept(entries);
            }
            listeners.remove(roomId);
            service.removeEntries(roomId);
        }

    }
}
