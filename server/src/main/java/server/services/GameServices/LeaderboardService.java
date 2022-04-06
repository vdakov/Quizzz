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

    public LeaderboardEntry addOrUpdateLeaderboardEntry(LeaderboardEntry e) {
        LeaderboardEntry entryInDB = repository.getLeaderboardEntryByUsernameAndRoomId(e.getUsername(), e.getRoomId()); //uniquely identify by username and roomId
        if (entryInDB != null) { //if this entry was already in the database, just update the score
            entryInDB.setScore(e.getScore());
            e = entryInDB;
        }
        return repository.save(e);
    }

    public List<LeaderboardEntry> getByRoomId(String roomId) {
        System.out.println("Getting leaderboard for roomId " + roomId);
        List<LeaderboardEntry> entries = repository.getLeaderboardEntriesByRoomIdOrderByScoreDesc(roomId);

        if (roomId.equals("null") || (entries.size() == 1 && entries.get(0).isSingleplayer())) //if this is a singleplayer entry, show every other singleplayer entry
            return repository.getLeaderboardEntriesBySingleplayerIsTrueOrderByScoreDesc();
        else
            return entries;
    }

    @Transactional
    public void removeEntries(String roomId) {
        repository.deleteLeaderboardEntriesByRoomId(roomId);
    }
}
