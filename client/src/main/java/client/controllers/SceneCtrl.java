package client.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class SceneCtrl {

    private Stage primaryStage;

    private MainScreenActivityCtrl mainScreenActivityCtrl;
    private Scene mainScreenScene;

    private QuestionSceneInsteadOfActivityCtrl questionSceneInsteadOfActivityCtrl;
    private Scene questionInsteadOfScene;

    private QuestionSceneHowMuchActivityCtrl questionSceneHowMuchActivityCtrl;
    private Scene questionHowMuchScene;

    private QuestionSceneWhatIsActivityCtrl questionSceneWhatIsActivityCtrl;
    private Scene questionWhatIsScene;

    private QuestionSceneGuessXActivityCtrl questionSceneGuessXActivityCtrl;
    private Scene questionGuessXScene;

    private OverviewActionsActivityCtrl overviewActionsActivityCtrl;
    private Scene overviewActionsScene;

    private AddActionActivityCtrl addActionActivityCtrl;
    private Scene addActionScene;

    private QuestionActivityCtrl questionActivityCtrl;
    private Scene questionScene;

    /**
     * Initialising the app scene with the primary stage and every scene that will be used in this stage
     *
     * @param primaryStage                                  the main stage of the app
     * @param overviewActionsActivityCtrlParentPair         the loaded FXML scene with control for displaying all actions
     * @param addActionActivityCtrlParentPair               the loaded FXML scene with control for adding a new action
     * @param questionsActivityCtrlParentPair               the loaded FXML scene with control for displaying a question
     * @param mainScreenCtrlParentPair                      the loaded FXML scene with control for displaying the main screen
     * @param questionSceneWhatIsActivityCtrlParentPair     the loaded FXML scene with control for displaying the question screen of type "What is"
     * @param questionSceneGuessXActivityCtrlParentPair     the loaded FXML scene with control for displaying the question screen of type "Guess X"
     * @param questionSceneHowMuchActivityCtrlParentPair    the loaded FXML scene with control for displaying the question screen of type "How Much"
     * @param questionSceneInsteadOfActivityCtrlParentPair  the loaded FXML scene with control for displaying the question screen of type "Instead of X"
     */
    public void initialize(Stage primaryStage, Pair<MainScreenActivityCtrl, Parent> mainScreenCtrlParentPair,
                                               Pair<QuestionSceneInsteadOfActivityCtrl, Parent> questionSceneInsteadOfActivityCtrlParentPair,
                                               Pair<QuestionSceneHowMuchActivityCtrl, Parent> questionSceneHowMuchActivityCtrlParentPair,
                                               Pair<QuestionSceneGuessXActivityCtrl, Parent> questionSceneGuessXActivityCtrlParentPair,
                                               Pair<QuestionSceneWhatIsActivityCtrl, Parent> questionSceneWhatIsActivityCtrlParentPair,
                                               Pair<OverviewActionsActivityCtrl, Parent> overviewActionsActivityCtrlParentPair,
                                               Pair<AddActionActivityCtrl, Parent> addActionActivityCtrlParentPair,
                                               Pair<QuestionActivityCtrl, Parent> questionsActivityCtrlParentPair) {
        this.primaryStage = primaryStage;

        this.mainScreenActivityCtrl             = mainScreenCtrlParentPair.getKey();
        this.mainScreenScene                    = new Scene(mainScreenCtrlParentPair.getValue());

        this.questionSceneInsteadOfActivityCtrl = questionSceneInsteadOfActivityCtrlParentPair.getKey();
        this.questionInsteadOfScene             = new Scene(questionSceneInsteadOfActivityCtrlParentPair.getValue());

        this.questionSceneHowMuchActivityCtrl   = questionSceneHowMuchActivityCtrlParentPair.getKey();
        this.questionHowMuchScene               = new Scene(questionSceneHowMuchActivityCtrlParentPair.getValue());

        this.questionSceneGuessXActivityCtrl    = questionSceneGuessXActivityCtrlParentPair.getKey();
        this.questionGuessXScene                = new Scene(questionSceneGuessXActivityCtrlParentPair.getValue());

        this.questionSceneWhatIsActivityCtrl    = questionSceneWhatIsActivityCtrlParentPair.getKey();
        this.questionWhatIsScene                = new Scene(questionSceneWhatIsActivityCtrlParentPair.getValue());

        this.overviewActionsActivityCtrl        = overviewActionsActivityCtrlParentPair.getKey();
        this.overviewActionsScene               = new Scene(overviewActionsActivityCtrlParentPair.getValue());

        this.addActionActivityCtrl              = addActionActivityCtrlParentPair.getKey();
        this.addActionScene                     = new Scene(addActionActivityCtrlParentPair.getValue());

        this.questionActivityCtrl               = questionsActivityCtrlParentPair.getKey();
        this.questionScene                      = new Scene(questionsActivityCtrlParentPair.getValue());


        showQuestionScene();
        showQuestionWhatIsScene();
        showQuestionGuessXScene();
        showQuestionHowMuchScene();
        showMainScreenScene();
        showQuestionInsteadOfScene();
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
     * Displays the screen for the question type "Instead of X"
     */

    public void showQuestionInsteadOfScene()
    {
        primaryStage.setTitle("Question Screen Instead of X");
        primaryStage.setScene(questionInsteadOfScene);
        Stage stage = (Stage) questionInsteadOfScene.getWindow();
        stage.show();
    }

    /**
     * Displays the screen for the question type "How much"
     */

    public void showQuestionHowMuchScene()
    {
        primaryStage.setTitle("Question Screen How Much");
        primaryStage.setScene(questionHowMuchScene);
        Stage stage = (Stage) questionHowMuchScene.getWindow();
        stage.show();
    }

    /**
     *  Displays the screen for the question type "Guess X"
     */

    public void showQuestionGuessXScene()
    {
        primaryStage.setTitle("Question Screen Guess X");
        primaryStage.setScene(questionGuessXScene);
        Stage stage = (Stage) questionGuessXScene.getWindow();
        stage.show();
    }

    /**
     * Displays the screen for the question type "What is..."
     */

    public void showQuestionWhatIsScene()
    {
        primaryStage.setTitle("Question Screen What Is");
        primaryStage.setScene(questionWhatIsScene);
        Stage stage = (Stage) questionWhatIsScene.getWindow();
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
