package server.controllers;

import commons.Leaderboard.LeaderboardEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.services.GameServices.LeaderboardService;

import java.util.Comparator;
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
        temp.sort(new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry o1, LeaderboardEntry o2) {
                return o1.getScore() - o2.getScore();
            }
        });
        return temp;
    }
}
