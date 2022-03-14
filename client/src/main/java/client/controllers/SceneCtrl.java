package client.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.*;

public class SceneCtrl {

    private Stage primaryStage;

    private MainScreenActivityCtrl mainScreenActivityCtrl;
    private Scene mainScreenScene;

    private AddActionActivityCtrl addActionActivityCtrl;
    private Scene addActionScene;

    public void initializeMainScenes(Stage primaryStage, Pair<MainScreenActivityCtrl, Parent> mainScreenActivityCtrlParentPair,
                                                         Pair<AddActionActivityCtrl, Parent> addActionActivityCtrlParentPair) {
        this.primaryStage = primaryStage;

        this.mainScreenActivityCtrl             = mainScreenActivityCtrlParentPair.getKey();
        this.mainScreenScene                    = new Scene(mainScreenActivityCtrlParentPair.getValue());

        this.addActionActivityCtrl              = addActionActivityCtrlParentPair.getKey();
        this.addActionScene                     = new Scene(addActionActivityCtrlParentPair.getValue());


        showMainScreen();
        primaryStage.show();
    }

    public void showMainScreen() {
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(mainScreenScene);
    }

    public void showAddActionScene() {
        primaryStage.setTitle("Add actions");
        primaryStage.setScene(addActionScene);
    }
}
