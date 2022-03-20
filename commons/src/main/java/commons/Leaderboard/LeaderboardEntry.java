package commons.Leaderboard;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class LeaderboardEntry {
    @Id
    @GeneratedValue
    private int id;
    private int score;
    private String username;
    @Transient
    private int rank;

    public LeaderboardEntry(String username, int score) {
        this.score = score;
        this.username = username;
    }

    private LeaderboardEntry() {

    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
