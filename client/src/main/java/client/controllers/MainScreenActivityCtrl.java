package client.controllers;

import client.communication.ServerUtils;

import javax.inject.Inject;
import java.io.IOException;

public class MainScreenActivityCtrl {

    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    public void enterSoloGame () throws IOException {
        String userName = "cata";
        String serverId = server.createNewSinglePlayerRoom(userName);
        System.out.println(serverId);
        sceneCtrl.showQuestionHowMuchScene(serverId, 0);
    }
}
