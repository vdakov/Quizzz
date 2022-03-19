package commons;

public class GameContainer {
    private String gameId;
    private String numPlayers;


    public GameContainer(String gameId, String numPlayers) {
        this.gameId = gameId;
        this.numPlayers = numPlayers;
    }

    public String getGameId() {
        return gameId;
    }

    public String getNumPlayers() {
        return numPlayers;
    }
}

