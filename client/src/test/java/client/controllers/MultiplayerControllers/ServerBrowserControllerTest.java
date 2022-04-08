package client.controllers.MultiplayerControllers;

import client.communication.ServerUtils;
import client.controllers.SceneCtrl;

import java.util.concurrent.ExecutionException;

class ServerBrowserControllerTest {

    private final ServerBrowserController test = new ServerBrowserController(new ServerUtils(), new SceneCtrl());

    ServerBrowserControllerTest() throws ExecutionException, InterruptedException {
    }


}