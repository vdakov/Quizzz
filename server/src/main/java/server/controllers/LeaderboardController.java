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

    @GetMapping("/singleplayer")
    public List<LeaderboardEntry> getSingleplayerLeaderboard() {
        List<LeaderboardEntry> temp = service.list();

        temp.sort((a, b) -> b.getScore() - a.getScore()); //sorts the list by comparing the scores
        for (int i = 0; i < temp.size(); i++) temp.get(i).setRank(i + 1); //assign ranks
        return temp;
    }

    @PostMapping("/singleplayer")
    public LeaderboardEntry addSingleplayerLeaderboardEntry(@RequestBody LeaderboardEntry e) {
        return service.addSingleplayerLeaderboardEntry(e);
    }
}
