package client.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class SceneCtrl {

    private Stage primaryStage;

    private MainScreenActivityCtrl mainScreenActivityCtrl;
    private Scene mainScreenScene;

    private AddActionActivityCtrl addActionActivityCtrl;
    private Scene addActionScene;

    private QuestionSceneInsteadOfActivityCtrl questionSceneInsteadOfActivityCtrl;
    private Scene questionInsteadOfScene;

    private QuestionSceneHowMuchActivityCtrl questionSceneHowMuchActivityCtrl;
    private Scene questionHowMuchScene;

    private QuestionSceneWhatIsActivityCtrl questionSceneWhatIsActivityCtrl;
    private Scene questionWhatIsScene;

    private QuestionSceneGuessXActivityCtrl questionSceneGuessXActivityCtrl;
    private Scene questionGuessXScene;


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

    public void initializeQuestionScenes(Pair<QuestionSceneInsteadOfActivityCtrl, Parent> questionSceneInsteadOfActivityCtrlParentPair,
                                         Pair<QuestionSceneHowMuchActivityCtrl, Parent> questionSceneHowMuchActivityCtrlParentPair,
                                         Pair<QuestionSceneGuessXActivityCtrl, Parent> questionSceneGuessXActivityCtrlParentPair,
                                         Pair<QuestionSceneWhatIsActivityCtrl, Parent> questionSceneWhatIsActivityCtrlParentPair) {
        this.questionSceneInsteadOfActivityCtrl = questionSceneInsteadOfActivityCtrlParentPair.getKey();
        this.questionInsteadOfScene             = new Scene(questionSceneInsteadOfActivityCtrlParentPair.getValue());

        this.questionSceneHowMuchActivityCtrl   = questionSceneHowMuchActivityCtrlParentPair.getKey();
        this.questionHowMuchScene               = new Scene(questionSceneHowMuchActivityCtrlParentPair.getValue());

        this.questionSceneGuessXActivityCtrl    = questionSceneGuessXActivityCtrlParentPair.getKey();
        this.questionGuessXScene                = new Scene(questionSceneGuessXActivityCtrlParentPair.getValue());

        this.questionSceneWhatIsActivityCtrl    = questionSceneWhatIsActivityCtrlParentPair.getKey();
        this.questionWhatIsScene                = new Scene(questionSceneWhatIsActivityCtrlParentPair.getValue());

    }

    public void showQuestionInsteadOfScene() {
        primaryStage.setTitle("Question type 1");
        primaryStage.setScene(questionInsteadOfScene);
    }

    public void showQuestionHowMuchScene(String serverId, int questionNumber) {
        primaryStage.setTitle("Question type 2");
        questionSceneHowMuchActivityCtrl.setQuestion(serverId, questionNumber);
        primaryStage.setScene(questionHowMuchScene);
    }

    public void showQuestionGuessXScene() {
        primaryStage.setTitle("Question type 3");
        primaryStage.setScene(questionGuessXScene);
    }

    public void showQuestionWhatIsScene() {
        primaryStage.setTitle("Question type 4");
        primaryStage.setScene(questionWhatIsScene);
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
