package client.controllers;

import client.communication.ServerUtils;
import com.google.inject.Inject;

public class MainScreenActivityCtrl {


    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    //Constructor for the Main Screen Controller
    @Inject
    public MainScreenActivityCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    public void enterSoloGame() {
        sceneCtrl.showQuestionScene();
    }
}
