package client.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class SceneCtrl {

    private Stage primaryStage;

    private MainScreenActivityCtrl mainScreenActivityCtrl;
    private Scene mainScreenScene;

    private OverviewActionsActivityCtrl overviewActionsActivityCtrl;
    private Scene overviewActionsScene;

    private AddActionActivityCtrl addActionActivityCtrl;
    private Scene addActionScene;

    private QuestionActivityCtrl questionActivityCtrl;
    private Scene questionScene;

    /**
     * Initialising the app scene with the primary stage and every scene that will be used in this stage
     *
     * @param primaryStage                          the main stage of the app
     * @param overviewActionsActivityCtrlParentPair the loaded FXML scene with control for displaying all actions
     * @param addActionActivityCtrlParentPair       the loaded FXML scene with control for adding a new action
     * @param questionsActivityCtrlParentPair       the loaded FXML scene with control for displaying a question
     */
    public void initialize(Stage primaryStage, Pair<MainScreenActivityCtrl, Parent> mainScreenCtrlParentPair,
                                               Pair<OverviewActionsActivityCtrl, Parent> overviewActionsActivityCtrlParentPair,
                                               Pair<AddActionActivityCtrl, Parent> addActionActivityCtrlParentPair,
                                               Pair<QuestionActivityCtrl, Parent> questionsActivityCtrlParentPair) {
        this.primaryStage = primaryStage;

        this.mainScreenActivityCtrl = mainScreenCtrlParentPair.getKey();
        this.mainScreenScene             = new Scene(mainScreenCtrlParentPair.getValue());

        this.overviewActionsActivityCtrl = overviewActionsActivityCtrlParentPair.getKey();
        this.overviewActionsScene = new Scene(overviewActionsActivityCtrlParentPair.getValue());

        this.addActionActivityCtrl = addActionActivityCtrlParentPair.getKey();
        this.addActionScene = new Scene(addActionActivityCtrlParentPair.getValue());

        this.questionActivityCtrl = questionsActivityCtrlParentPair.getKey();
        this.questionScene = new Scene(questionsActivityCtrlParentPair.getValue());

         showOverviewActionsScene();
        //showQuestionScene();
        //showMainScreenScene();
        primaryStage.show();
    }

    /**
     * Displays the main screen
     */

    public void showMainScreenScene()
    {
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(mainScreenScene);
        Stage stage = (Stage) mainScreenScene.getWindow();
        stage.show();
    }

    /**
     * Displays the scene with all actions
     */
    public void showOverviewActionsScene() {
        primaryStage.setTitle("Action: Overview");
        primaryStage.setScene(overviewActionsScene);
        overviewActionsActivityCtrl.refresh();
    }

    /**
     * Displays the scene with add action
     */
    public void showAddActionScene() {
        primaryStage.setTitle("Action: Adding Action");
        primaryStage.setScene(addActionScene);
        addActionScene.setOnKeyPressed(e -> addActionActivityCtrl.keyPressed(e));
    }

    /**
     * Displays the scene with add question
     */
    public void showQuestionScene() {
        primaryStage.setTitle("Question Screen");
        primaryStage.setScene(questionScene);
        Stage stage = (Stage) questionScene.getWindow();
        questionActivityCtrl.initialize();
        stage.show();
    }
}
