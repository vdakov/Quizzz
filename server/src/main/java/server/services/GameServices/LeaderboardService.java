package server.services.GameServices;

import commons.Leaderboard.LeaderboardEntry;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.repositories.LeaderboardRepository;

import java.util.List;

@Service
public class LeaderboardService {
    private LeaderboardRepository repository;

    public LeaderboardService(LeaderboardRepository repository) {
        this.repository = repository;
    }

    public List<LeaderboardEntry> list() {
        return repository.findAll(Sort.by(Sort.Order.desc("score")));
    }

    public LeaderboardEntry addLeaderboardEntry(LeaderboardEntry e) {
        return repository.save(e);
    }

    public List<LeaderboardEntry> getByRoomId(String roomId) {
        System.out.println("Getting leaderboard for roomId " + roomId);
        return repository.getLeaderboardEntriesByRoomIdOrderByScoreDesc(roomId);
    }

    @Transactional
    public void removeEntries(String roomId) {
        repository.deleteLeaderboardEntriesByRoomId(roomId);
    }
}
