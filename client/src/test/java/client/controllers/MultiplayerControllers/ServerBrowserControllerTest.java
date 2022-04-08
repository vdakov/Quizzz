package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class ServerBrowserControllerTest {

    @Test
    void checkUsername() throws ExecutionException, InterruptedException {
        ServerBrowserController serverBrowserController = new ServerBrowserController(new ServerUtils(), new SceneCtrl());
        assertEquals(true, serverBrowserController.checkUsername("delia"));
    }

    @Test
    void checkUsername2() throws ExecutionException, InterruptedException {
        ServerBrowserController serverBrowserController = new ServerBrowserController(new ServerUtils(), new SceneCtrl());
        String string = new String("delia");
        if (string.isBlank())
            assertEquals(false, serverBrowserController.checkUsername(string));
        else
            assertEquals(true, serverBrowserController.checkUsername(string));
    }
}