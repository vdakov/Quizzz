package server.entities;

import java.util.ArrayList;
import java.util.List;

public class GameCatalog {
    List<Game> gameList;

    public GameCatalog() {
        this.gameList = new ArrayList<>();
    }

    public GameCatalog(List<Game> gameList) {
        this.gameList = gameList;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    public void addGame(Game game) {
        gameList.add(game);
    }

    public Game getGame(String id) {
        for(Game game : gameList) {
            if(game.getGameId().equals(id)) {
                return game;
            }
        }
        return null;
    }
}
