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

    private QuestionSceneInsteadOfActivityCtrl questionSceneInsteadOfActivityCtrl;
    private Scene questionInsteadOfScene;

    private QuestionSceneHowMuchActivityCtrl questionSceneHowMuchActivityCtrl;
    private Scene questionHowMuchScene;

   // private QuestionSceneWhatIsActivityCtrl questionSceneWhatIsActivityCtrl;
  //  private Scene questionWhatIsScene;

    private QuestionSceneGuessXActivityCtrl questionSceneGuessXActivityCtrl;
    private Scene questionGuessXScene;

//    private TransitionSceneShortAnswerCtrl transitionSceneShortAnswerCtrl;
    private Scene transitionShortAnswerScene;

    private OverviewActionsActivityCtrl overviewActionsActivityCtrl;
    private Scene overviewActionsScene;

    private AddActionActivityCtrl addActionActivityCtrl;
    private Scene addActionScene;


    private SingleplayerLeaderboardCtrl singleplayerLeaderboardCtrl;
    private Scene singleplayerLeaderboardScene;

    /**
     * Initialising the app scene with the primary stage and every scene that will be used in this stage
     *
     * @param primaryStage                                  the main stage of the app
     * @param overviewActionsActivityCtrlParentPair         the loaded FXML scene with control for displaying all actions
     * @param addActionActivityCtrlParentPair               the loaded FXML scene with control for adding a new action
     * @param mainScreenCtrlParentPair                      the loaded FXML scene with control for displaying the main screen
    // * @param questionSceneWhatIsActivityCtrlParentPair     the loaded FXML scene with control for displaying the question screen of type "What is"
     * @param questionSceneGuessXActivityCtrlParentPair     the loaded FXML scene with control for displaying the question screen of type "Guess X"
     * @param questionSceneHowMuchActivityCtrlParentPair    the loaded FXML scene with control for displaying the question screen of type "How Much"
     * @param questionSceneInsteadOfActivityCtrlParentPair  the loaded FXML scene with control for displaying the question screen of type "Instead of X"
     */
    public void initialize(Stage primaryStage,  Pair<MainScreenActivityCtrl, Parent> mainScreenCtrlParentPair,
                                               Pair<QuestionSceneInsteadOfActivityCtrl, Parent> questionSceneInsteadOfActivityCtrlParentPair,
                                               Pair<QuestionSceneHowMuchActivityCtrl, Parent> questionSceneHowMuchActivityCtrlParentPair,
                                               Pair<QuestionSceneGuessXActivityCtrl, Parent> questionSceneGuessXActivityCtrlParentPair,
//                                                Pair<TransitionSceneShortAnswerCtrl, Parent> transitionScreenShortAnswerActivityCtrlParentPair,
                                             //  Pair<QuestionSceneWhatIsActivityCtrl, Parent> questionSceneWhatIsActivityCtrlParentPair,
                                               Pair<OverviewActionsActivityCtrl, Parent> overviewActionsActivityCtrlParentPair,
                                               Pair<AddActionActivityCtrl, Parent> addActionActivityCtrlParentPair )

    {
        this.primaryStage = primaryStage;

        this.mainScreenActivityCtrl             = mainScreenCtrlParentPair.getKey();
        this.mainScreenScene                    = new Scene(mainScreenCtrlParentPair.getValue());

        this.questionSceneInsteadOfActivityCtrl = questionSceneInsteadOfActivityCtrlParentPair.getKey();
        this.questionInsteadOfScene             = new Scene(questionSceneInsteadOfActivityCtrlParentPair.getValue());

        this.questionSceneHowMuchActivityCtrl   = questionSceneHowMuchActivityCtrlParentPair.getKey();
        this.questionHowMuchScene               = new Scene(questionSceneHowMuchActivityCtrlParentPair.getValue());

        this.questionSceneGuessXActivityCtrl    = questionSceneGuessXActivityCtrlParentPair.getKey();
        this.questionGuessXScene                = new Scene(questionSceneGuessXActivityCtrlParentPair.getValue());

      //  this.questionSceneWhatIsActivityCtrl    = questionSceneWhatIsActivityCtrlParentPair.getKey();
      //  this.questionWhatIsScene                = new Scene(questionSceneWhatIsActivityCtrlParentPair.getValue());

        this.overviewActionsActivityCtrl        = overviewActionsActivityCtrlParentPair.getKey();
        this.overviewActionsScene               = new Scene(overviewActionsActivityCtrlParentPair.getValue());

        this.addActionActivityCtrl              = addActionActivityCtrlParentPair.getKey();
        this.addActionScene                     = new Scene(addActionActivityCtrlParentPair.getValue());

//        this.transitionSceneShortAnswerCtrl     = transitionScreenShortAnswerActivityCtrlParentPair.getKey();
        this.transitionShortAnswerScene         = new Scene(questionSceneGuessXActivityCtrlParentPair.getValue());

//        showOverviewActionsScene();
//        showMainScreenScene();

        showQuestionGuessXScene();
//        showQuestionHowMuchScene();
//        showQuestionInsteadOfScene();
//        showTransitionShortAnswer();
        primaryStage.show();

    }

    /**
     * Displays the main screen
     */

    public void showMainScreenScene() {
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(mainScreenScene);
        primaryStage.show();
    }

    /**
     * Displays the screen for the question type "Instead of X"
     */


    public void showQuestionInsteadOfScene()
    {
        primaryStage.setTitle("Question Screen Instead of X");
        primaryStage.setScene(questionInsteadOfScene);
        Stage stage = (Stage) questionInsteadOfScene.getWindow();
        questionSceneInsteadOfActivityCtrl.initialize();
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
        questionSceneHowMuchActivityCtrl.initialize();
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
        questionSceneGuessXActivityCtrl.initialize();
        stage.show();
    }

    /**
     *  Displays the transition screen for the question type "Guess X"
     */

    public void showTransitionShortAnswer()
    {
        primaryStage.setTitle("Transition Screen Guess X");
        primaryStage.setScene(transitionShortAnswerScene);
        Stage stage = (Stage) transitionShortAnswerScene.getWindow();
        questionSceneGuessXActivityCtrl.initialize();
        stage.show();
    }

    /**
     * Displays the screen for the question type "What is..."
     */

    /*
    public void showQuestionWhatIsScene()
    {
        primaryStage.setTitle("Question Screen What Is");
        primaryStage.setScene(questionWhatIsScene);
        Stage stage = (Stage) questionWhatIsScene.getWindow();
        stage.show();
    }
*/

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


    public void showSingleplayerLeaderboardScene() {
        primaryStage.setTitle("Singleplayer Leaderboard");
        primaryStage.setScene(singleplayerLeaderboardScene);
        Stage stage = (Stage) singleplayerLeaderboardScene.getWindow();
        stage.show();
    }
}
