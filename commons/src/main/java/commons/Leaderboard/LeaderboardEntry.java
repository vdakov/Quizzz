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
    private String roomId;
    @Transient
    private int rank;

    /**
     * Constructor for a leaderboard entry
     *
     * @param username the name of the player
     * @param roomId   the room this player is in
     * @param score    the score the player got
     */
    public LeaderboardEntry(String username, String roomId, int score) {
        this.score = score;
        this.username = username;
        this.roomId = roomId;
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

    public String getRoomId() {
        return roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
