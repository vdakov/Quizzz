package server.services.GameServices;

import commons.Leaderboard.LeaderboardEntry;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import server.repositories.SingleplayerLeaderboardRepository;

import java.util.List;

@Service
public class LeaderboardService {
    private SingleplayerLeaderboardRepository repository;

    public LeaderboardService(SingleplayerLeaderboardRepository repository) {
        this.repository = repository;
    }

    public List<LeaderboardEntry> list() {
        return repository.findAll(Sort.by(Sort.Order.desc("score")));
    }

    public LeaderboardEntry addSingleplayerLeaderboardEntry(LeaderboardEntry e) {
        return repository.save(e);
    }
}