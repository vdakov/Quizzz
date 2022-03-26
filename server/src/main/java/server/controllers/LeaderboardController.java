package server.controllers;

import commons.Leaderboard.LeaderboardEntry;
import org.springframework.web.bind.annotation.*;
import server.services.GameServices.LeaderboardService;

import java.util.List;

@RestController
@RequestMapping("api/leaderboard")
public class LeaderboardController {
    private LeaderboardService service;

    public LeaderboardController(LeaderboardService service) {
        this.service = service;
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
        temp = temp.subList(0, Integer.min(10, temp.size()));
        for (int i = 0; i < temp.size(); i++) temp.get(i).setRank(i + 1); //assign ranks
        return temp;
    }

    @PostMapping
    public LeaderboardEntry addLeaderboardEntry(@RequestBody LeaderboardEntry e) {
        return service.addLeaderboardEntry(e);
    }

    @DeleteMapping("/remove/{roomId}")
    public void removeEntries(@PathVariable String roomId) {
        service.removeEntries(roomId);
    }
}
