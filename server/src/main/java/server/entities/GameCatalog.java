package server.entities;

import commons.GameContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GameCatalog {

    private static GameCatalog gameCatalog = null;

    private HashMap<String, SinglePlayerGame> singlePlayerGames;
    private HashMap<String, MultiPlayerGame> multiPlayerGames;
    private MultiPlayerGame multiplayerRandomRoom;

    public GameCatalog() {
        this.singlePlayerGames = new HashMap<>();
        this.multiPlayerGames = new HashMap<>();
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
        System.out.println(multiplayerRandomRoom.getGameId() + "      " + gameId);
        if (gameId.equals(multiplayerRandomRoom.getGameId())) {
            return multiplayerRandomRoom;
        }

        return multiPlayerGames.get(gameId);
    }

    public MultiPlayerGame getMultiplayerRandomRoom() {
        return multiplayerRandomRoom;
    }

    public void setMultiplayerRandomRoom(MultiPlayerGame multiplayerRandomRoom) {
        this.multiplayerRandomRoom = multiplayerRandomRoom;
    }

    public List<GameContainer> getWaitingMultiplayerGames() {
        this.cleanEmptyGames();
        ArrayList<GameContainer> games = new ArrayList<>();
        Iterator<String> iterator1 = multiPlayerGames.keySet().iterator();
        Iterator<MultiPlayerGame> iterator2 = multiPlayerGames.values().iterator();
        while (iterator1.hasNext()) {
            int numPlayers = iterator2.next().getNumPlayers();
            String currentGameId = iterator1.next();

            if (!this.getMultiPlayerGame(currentGameId).isGameOngoing()) {
                games.add(new GameContainer(currentGameId, numPlayers));
            }

        }

        return games;

    }

    /**
     * Method that ensures there are no empty games in server browser
     */
    public void cleanEmptyGames() {

        Iterator<MultiPlayerGame> gameIterator = multiPlayerGames.values().iterator();
        ArrayList<MultiPlayerGame> emptyGames = new ArrayList<>();

        while (gameIterator.hasNext()) {
            MultiPlayerGame game = gameIterator.next();
            if (game.getNumPlayers() == 0) {
                emptyGames.add(game);
            }
        }
        for (MultiPlayerGame game : emptyGames) {
            this.multiPlayerGames.remove(game.getGameId());
        }
    }

}
