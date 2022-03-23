package server.entities;

import java.util.HashMap;

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
}
