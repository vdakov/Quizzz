package commons;

public class GameContainer {
    private String gameId;
    private Integer numPlayers;


    public GameContainer(String gameId, Integer numPlayers) {
        this.gameId = gameId;
        this.numPlayers = numPlayers;
    }

    public String getGameId() {
        return gameId;
    }

    public Integer getNumPlayers() {
        return numPlayers;
    }
}

