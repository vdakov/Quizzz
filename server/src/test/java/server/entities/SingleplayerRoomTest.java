package server.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SingleplayerRoomTest {



    @Test
    void resetAddedPointTest() {
        try{
        SingleplayerRoom s = new SingleplayerRoom("1", "1", null);

        assertEquals(0, s.getAddedPoints());
    } catch (Exception e) {
        System.out.println("Error");
    };
    }


}