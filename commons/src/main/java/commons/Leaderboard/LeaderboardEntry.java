package commons.Leaderboard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LeaderboardEntry {
    @Id
    @GeneratedValue
    private String id;
    private int score;
    private String username;

    public LeaderboardEntry(int score, String username) {
        this.score = score;
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
