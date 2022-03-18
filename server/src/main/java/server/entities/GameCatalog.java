package server.entities;

import java.util.HashMap;

public class GameCatalog {

    private static GameCatalog gameCatalog = null;

    private HashMap<String, SinglePlayerGame> singlePlayerGames;
    private HashMap<String, MultiPlayerGame>  multiPlayerGames;
    private MultiPlayerGame multiplayerRandomRoom;

    public GameCatalog() {
        this.singlePlayerGames     = new HashMap<>();
        this.multiPlayerGames      = new HashMap<>();
        this.multiplayerRandomRoom = null;
    }

    public static GameCatalog getGameCatalog() {
        if (gameCatalog == null) {
            gameCatalog = new GameCatalog();
        }
        return gameCatalog;
    }

    public void addSinglePlayerGame(SinglePlayerGame singlePlayerGame) {
        singlePlayerGames.put(singlePlayerGame.getGameId(), singlePlayerGame);
    }

    public SinglePlayerGame getSinglePlayerGame(String gameId) {
        return singlePlayerGames.get(gameId);
    }

    public void addMultiPlayerGame(MultiPlayerGame multiPlayerGame) {
        multiPlayerGames.put(multiPlayerGame.getGameId(), multiPlayerGame);
    }

    public MultiPlayerGame getMultiPlayerGame(String gameId) {
        return multiPlayerGames.get(gameId);
    }

    public MultiPlayerGame getMultiplayerRandomRoom() {
        return multiplayerRandomRoom;
    }

    public void setMultiplayerRandomRoom(MultiPlayerGame multiplayerRandomRoom) {
        this.multiplayerRandomRoom = multiplayerRandomRoom;
    }
}
