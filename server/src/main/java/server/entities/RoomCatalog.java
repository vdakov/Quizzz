package server.entities;

import java.util.HashMap;

public class RoomCatalog {

    private static RoomCatalog roomCatalog = null;

    private final HashMap<String, SingleplayerRoom> singleplayerRooms;
    private final HashMap<String, MultiplayerRoom>  multiplayerRooms;
    private MultiplayerRoom multiplayerRandomRoom;

    /**
     * Constructor that initialised this and only instance of GameCatalog ( in the server there can be only 1 game catalog,
     * that is why we chose to use the singleton design pattern to implement this functionality
     */
    public RoomCatalog() {
        this.singleplayerRooms     = new HashMap<>();
        this.multiplayerRooms      = new HashMap<>();
        this.multiplayerRandomRoom = null;
    }

    /**
     * Returns the instance of game catalog
     *
     * @return the instance of game catalog
     */
    public static RoomCatalog getGameCatalog() {
        if (roomCatalog == null) {
            roomCatalog = new RoomCatalog();
        }
        return roomCatalog;
    }

    /**
     * Adds a singleplayer game if possible or throw exception if one with the same id exists or the game that is wanted to be added is null
     *
     * @param singleplayerGame the singleplayer game that is wanted to be added
     * @throws IllegalArgumentException when the game is null, it's id is null or if a game with that id is already in progress
     */
    public void addSingleplayerGame(SingleplayerRoom singleplayerGame) throws IllegalArgumentException {
        if (singleplayerGame == null || singleplayerGame.getGameId() == null) {
            throw new IllegalArgumentException("Singleplayer game or it's id is null");
        }

        if (singleplayerRooms.get(singleplayerGame.getGameId()) != null) {
            throw new IllegalArgumentException("Game with the same ID already exists");
        }

        singleplayerRooms.put(singleplayerGame.getGameId(), singleplayerGame);
    }

    /**
     * Returns the singleplayer game
     *
     * @param roomId the id of the game we are looking for
     * @return the singleplayer game with the given id or null if a game with that id does not exist
     */
    public SingleplayerRoom getSinglePlayerGame(String roomId) {
        return singleplayerRooms.get(roomId);
    }

    /**
     * Adds a multiplayer game if possible or throw exception if one with the same id exists or the game that is wanted to be added is null
     *
     * @param multiplayerGame the singleplayer game that is wanted to be added
     * @throws IllegalArgumentException when the game is null, it's id is null or if a game with that id is already in progress
     */
    public void addMultiplayerGame(MultiplayerRoom multiplayerGame) throws IllegalArgumentException {
        if (multiplayerGame == null || multiplayerGame.getGameId() == null) {
            throw new IllegalArgumentException("Multiplayer game or it's id is null");
        }

        if (multiplayerRooms.get(multiplayerGame.getGameId()) != null) {
            throw new IllegalArgumentException("Game with the same ID already exists");
        }

        multiplayerRooms.put(multiplayerGame.getGameId(), multiplayerGame);
    }

    /**
     * Returns the multiplayer game
     *
     * @param roomId the id of the game we are looking for
     * @return the multiplayer game with the given id or null if a game with that id does not exist
     */
    public MultiplayerRoom getMultiPlayerGame(String roomId) {
        if (roomId.equals(multiplayerRandomRoom.getGameId())) {
            return multiplayerRandomRoom;
        }

        return multiplayerRooms.get(roomId);
    }

    /**
     * Returns the multiplayer room where random people gather
     *
     * @return the multiplayer random room or null if it does not exist
     */
    public MultiplayerRoom getMultiplayerRandomRoom() {
        return multiplayerRandomRoom;
    }

    /**
     * Sets the multiplayer random room to the multiplayer game given as parameter
     * @param multiplayerRoom the room that will be set to be the new random room
     */
    public void setMultiplayerRandomRoom(MultiplayerRoom multiplayerRoom) {
        this.multiplayerRandomRoom = multiplayerRoom;
    }
}
