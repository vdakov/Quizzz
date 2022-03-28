package server.entities;

import commons.GameContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RoomCatalog {

    private static RoomCatalog roomCatalog = null;

    private final HashMap<String, SingleplayerRoom> singleplayerRooms;
    private final HashMap<String, MultiplayerRoom>  multiplayerRooms;
    private MultiplayerRoom multiplayerRandomRoom;

    /**
     * Constructor that initialised this and only instance of GameCatalog ( in the server there can be only 1 room catalog,
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
     * @return the instance of room catalog
     */
    public static RoomCatalog getRoomCatalog() {
        if (roomCatalog == null) {
            roomCatalog = new RoomCatalog();
        }
        return roomCatalog;
    }

    /**
     * Adds a singleplayer room if possible or throw exception if one with the same id exists or the room that is wanted to be added is null
     *
     * @param  singleplayerRoom the singleplayer room that is wanted to be added
     * @throws IllegalArgumentException when the room is null, it's id is null or if a room with that id is already in progress
     */
    public void addSingleplayerRoom(SingleplayerRoom singleplayerRoom) throws IllegalArgumentException {
        if (singleplayerRoom == null || singleplayerRoom.getRoomId() == null) {
            throw new IllegalArgumentException("Singleplayer room or it's id is null");
        }

        if (singleplayerRooms.get(singleplayerRoom.getRoomId()) != null) {
            throw new IllegalArgumentException("Room with the same ID already exists");
        }

        singleplayerRooms.put(singleplayerRoom.getRoomId(), singleplayerRoom);
    }

    /**
     * Returns the singleplayer room
     *
     * @param  roomId the id of the room we are looking for
     * @return the singleplayer room with the given id or null if a room with that id does not exist
     */
    public SingleplayerRoom getSinglePlayerRoom(String roomId) {
        return singleplayerRooms.get(roomId);
    }

    /**
     * Adds a multiplayer room if possible or throw exception if one with the same id exists or the room that is wanted to be added is null
     *
     * @param  multiplayerRoom the singleplayer room that is wanted to be added
     * @throws IllegalArgumentException when the room is null, it's id is null or if a room with that id is already in progress
     */
    public void addMultiplayerRoom(MultiplayerRoom multiplayerRoom) throws IllegalArgumentException {
        if (multiplayerRoom == null || multiplayerRoom.getRoomId() == null) {
            throw new IllegalArgumentException("Multiplayer room or it's id is null");
        }

        if (multiplayerRooms.get(multiplayerRoom.getRoomId()) != null) {
            throw new IllegalArgumentException("Room with the same ID already exists");
        }

        multiplayerRooms.put(multiplayerRoom.getRoomId(), multiplayerRoom);
    }

    /**
     * Returns the multiplayer room
     *
     * @param roomId the id of the room we are looking for
     * @return the multiplayer room with the given id or null if a room with that id does not exist
     */
    public MultiplayerRoom getMultiPlayerRoom(String roomId) {
        if (roomId.equals(multiplayerRandomRoom.getRoomId())) {
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
     * Sets the multiplayer random room to the multiplayer room given as parameter
     * @param multiplayerRoom the room that will be set to be the new random room
     */
    public void setMultiplayerRandomRoom(MultiplayerRoom multiplayerRoom) {
        this.multiplayerRandomRoom = multiplayerRoom;
    }

    public List<GameContainer> getWaitingMultiplayerGames() {
        this.cleanEmptyGames();
        ArrayList<GameContainer> games = new ArrayList<>();
        Iterator<String> iterator1 = multiplayerRooms.keySet().iterator();
        Iterator<MultiplayerRoom> iterator2 = multiplayerRooms.values().iterator();
        while (iterator1.hasNext()) {
            int numPlayers = iterator2.next().getNumPlayers();
            String currentGameId = iterator1.next();

            if (this.getMultiPlayerRoom(currentGameId).getRoomStatus() == Room.RoomStatus.WAITING) {
                games.add(new GameContainer(currentGameId, numPlayers));
            }

        }

        return games;
    }

    /**
     * Method that ensures there are no empty games in server browser
     */
    public void cleanEmptyGames() {

        Iterator<MultiplayerRoom> gameIterator = multiplayerRooms.values().iterator();
        ArrayList<MultiplayerRoom> emptyGames = new ArrayList<>();

        while (gameIterator.hasNext()) {
            MultiplayerRoom game = gameIterator.next();
            if (game.getNumPlayers() == 0) {
                emptyGames.add(game);
            }
        }
        for (MultiplayerRoom game : emptyGames) {
            this.multiplayerRooms.remove(game.getRoomId());
        }
    }

}
