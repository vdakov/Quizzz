package commons.Leaderboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeaderboardEntryTest {

    private final LeaderboardEntry leaderboardEntry = new LeaderboardEntry("delia", "123", 500, true);

    @Test
    void setRank() {
        leaderboardEntry.setRank(1);
        assertEquals(1, leaderboardEntry.getRank());
    }

    @Test
    void getRank() {
       assertEquals(0, leaderboardEntry.getRank());
    }

    @Test
    void setScore() {
        leaderboardEntry.setScore(1);
        assertEquals(1, leaderboardEntry.getScore());
    }

    @Test
    void getScore() {
        assertEquals(500, leaderboardEntry.getScore());
    }

    @Test
    void getUsername() {
        assertEquals("delia", leaderboardEntry.getUsername());
    }

    @Test
    void getRoomId() {
        assertEquals("123", leaderboardEntry.getRoomId());
    }

    @Test
    void isSingleplayer() {
        assertEquals(true, leaderboardEntry.isSingleplayer());
    }

    @Test
    void getId() {
        assertEquals(0, leaderboardEntry.getId());
    }

    @Test
    void setId() {
        leaderboardEntry.setId(1);
        assertEquals(1, leaderboardEntry.getId());
    }
}