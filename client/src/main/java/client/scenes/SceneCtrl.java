package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class SceneCtrl {

    private Stage primaryStage;

    private ActivityOverviewCtrl actOverviewCtrl;
    private Scene actOverview;

    private AddActivityCtrl addActivityCtrl;
    private Scene addActivity;

    public void initialize(Stage primaryStage, Pair<ActivityOverviewCtrl, Parent> actOverview, Pair<AddActivityCtrl, Parent> addAct) {
        this.primaryStage = primaryStage;

        this.actOverviewCtrl = actOverview.getKey();
        this.actOverview = new Scene(actOverview.getValue());

        this.addActivityCtrl = addAct.getKey();
        this.addActivity = new Scene(addAct.getValue());

        showActivitiesOverview();
        primaryStage.show();
    }

    public void showActivitiesOverview() {
        primaryStage.setTitle("Activities: Overview");
        primaryStage.setScene(actOverview);
        actOverviewCtrl.refresh();
    }

    public void showAddActivity() {
        primaryStage.setTitle("Activities: Adding Activity");
        primaryStage.setScene(addActivity);
        addActivity.setOnKeyPressed(e -> addActivityCtrl.keyPressed(e));
    }
}
