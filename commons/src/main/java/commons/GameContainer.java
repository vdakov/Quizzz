package commons;

public class GameContainer {
    private String gameId;
    private Integer numPlayers;

    public GameContainer() {
        this.gameId = "";
        this.numPlayers = 0;
    }

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

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setNumPlayers(Integer numPlayers) {
        this.numPlayers = numPlayers;
    }
}

