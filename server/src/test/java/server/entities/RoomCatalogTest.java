package server.entities;

import commons.GameContainer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class RoomCatalogTest {

    private RoomCatalog rc = new RoomCatalog();
    private SingleplayerRoom s = new SingleplayerRoom("1", "1", null);
    private MultiplayerRoom m = new MultiplayerRoom("2", "1", null);
    private MultiplayerRoom multiplayerRandomRoom;
    private HashMap<String, SingleplayerRoom> singleplayerRooms = new HashMap<>();
    private HashMap<String, MultiplayerRoom>  multiplayerRooms = new HashMap<>();

    @Test
    void testGetterAndSetterOfSinglePlayerRoom() {
        rc.addSingleplayerRoom(s);
        assertEquals(s, rc.getSinglePlayerRoom("1"));
    }

    @Test
    void testGetterAndSetterOfMultiPlayerRoom() {
        MultiplayerRoom mNull = new MultiplayerRoom(null, "1", null);
        assertThrows(IllegalArgumentException.class, () -> {
            rc.addMultiplayerRoom(mNull);
        }, "Multiplayer room or it's id is null");

        rc.addMultiplayerRoom(m);
        rc.setMultiplayerRandomRoom(m);
        assertEquals(m, rc.getMultiplayerRandomRoom());
        assertEquals(m, rc.getMultiPlayerRoom("2"));

        MultiplayerRoom m2 = new MultiplayerRoom("2", "1", null);
        assertThrows(IllegalArgumentException.class, () -> {
            rc.addMultiplayerRoom(m2);
        }, "Room with the same ID already exists");
    }

    @Test
    void testRemoveFinished() {
        MultiplayerRoom m2 = new MultiplayerRoom("3", "1", null);

        m.addPlayer("User1");
        m2.addPlayer("User1");
        rc.addMultiplayerRoom(m);
        rc.addMultiplayerRoom(m2);
        rc.setMultiplayerRandomRoom(m);
        rc.setMultiplayerRandomRoom(m2);

        m.setRoomStatus(Room.RoomStatus.ONGOING);
        m2.setRoomStatus(Room.RoomStatus.FINISHED);

        multiplayerRooms.put("2", m);
        rc.removeFinishedMultiplayerGames();
        assertEquals(m, rc.getMultiPlayerRoom("2"));
        assertEquals(multiplayerRooms, rc.getMultiPlayerRooms());
    }

    @Test
    void testCleanEmptyGames() {
        MultiplayerRoom m2 = new MultiplayerRoom("3", "1", null);

        m.addPlayer("User1");
        m2.addPlayer("User1");
        m2.removePlayer("User1");
        rc.addMultiplayerRoom(m);
        rc.addMultiplayerRoom(m2);
        rc.setMultiplayerRandomRoom(m);
        rc.setMultiplayerRandomRoom(m2);

        multiplayerRooms.put("2", m);
        rc.cleanEmptyGames();
        assertEquals(multiplayerRooms, rc.getMultiPlayerRooms());
    }

    @Test
    void testGetWaitingMultiplayerGames() {
        MultiplayerRoom m2 = new MultiplayerRoom("3", "1", null);
        MultiplayerRoom m3 = new MultiplayerRoom("4", "1", null);

        m.addPlayer("User1");
        m2.addPlayer("User1");
        m3.addPlayer("User1");
        m2.removePlayer("User1");
        rc.addMultiplayerRoom(m);
        rc.addMultiplayerRoom(m2);
        rc.addMultiplayerRoom(m3);
        rc.setMultiplayerRandomRoom(m);
        rc.setMultiplayerRandomRoom(m2);
        rc.setMultiplayerRandomRoom(m3);
        multiplayerRooms.put("2", m);

        m.setRoomStatus(Room.RoomStatus.WAITING);
        m2.setRoomStatus(Room.RoomStatus.ONGOING);
        m3.setRoomStatus(Room.RoomStatus.FINISHED);

        ArrayList<GameContainer> games = new ArrayList<>();
        GameContainer g = new GameContainer(multiplayerRooms.keySet().iterator().next(), 1);
        games.add(g);

        assertEquals(games.size(), rc.getWaitingMultiplayerGames().size());
    }
}