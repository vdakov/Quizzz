package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import commons.GameContainer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class ServerBrowserControllerTest {

    private final ServerBrowserController test = new ServerBrowserController(new ServerUtils(), new SceneCtrl());

    ServerBrowserControllerTest() throws ExecutionException, InterruptedException {
    }


    @Test
    void UserNameTest() {
        assertTrue(test.checkUsername("test"));
        try {
            assertFalse(test.checkUsername(""));
        } catch (ExceptionInInitializerError e) {
            System.out.println("Not suitable for UI testing");
        }

    }


}