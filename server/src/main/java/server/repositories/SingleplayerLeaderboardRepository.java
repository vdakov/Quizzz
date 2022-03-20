package server.repositories;

import commons.Leaderboard.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SingleplayerLeaderboardRepository extends JpaRepository<LeaderboardEntry, Integer> {

}
