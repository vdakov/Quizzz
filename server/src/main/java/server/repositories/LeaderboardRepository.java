package server.repositories;

import commons.Leaderboard.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Integer> {
    List<LeaderboardEntry> getLeaderboardEntriesByRoomIdOrderByScoreDesc(String roomId);

    long deleteLeaderboardEntriesByRoomId(String roomId);

    List<LeaderboardEntry> getLeaderboardEntriesBySingleplayerIsTrueOrderByScoreDesc();
}
