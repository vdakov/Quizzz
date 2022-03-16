package client.controllers;

import client.controllers.QuestionControllers.AlternativeQuestionActivityCtrl;
import client.controllers.QuestionControllers.ComparisonQuestionActivityCtrl;
import client.controllers.QuestionControllers.KnowledgeQuestionActivityCtrl;
import client.controllers.QuestionControllers.OpenQuestionActivityCtrl;
import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
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

    private AlternativeQuestionActivityCtrl alternativeQuestionActivityCtrl;
    private Scene questionInsteadOfScene;

    private KnowledgeQuestionActivityCtrl knowledgeQuestionActivityCtrl;
    private Scene questionHowMuchScene;

    private ComparisonQuestionActivityCtrl comparisonQuestionActivityCtrl;
    private Scene questionWhatIsScene;

    private OpenQuestionActivityCtrl openQuestionActivityCtrl;
    private Scene questionGuessXScene;

    private TimerCtrl timerCtrl;   // testing timer
    private Scene timerScene;


    public void initializeMainScenes(Stage primaryStage, Pair<MainScreenActivityCtrl, Parent> mainScreenActivityCtrlParentPair,
                                                         Pair<AddActionActivityCtrl, Parent> addActionActivityCtrlParentPair) {
        this.primaryStage = primaryStage;

        this.mainScreenActivityCtrl             = mainScreenActivityCtrlParentPair.getKey();
        this.mainScreenScene                    = new Scene(mainScreenActivityCtrlParentPair.getValue());

        this.addActionActivityCtrl              = addActionActivityCtrlParentPair.getKey();
        this.addActionScene                     = new Scene(addActionActivityCtrlParentPair.getValue());


        showMainScreen();
        //showTimerScreen();
        primaryStage.show();
    }

    public void initializeQuestionScenes(Pair<AlternativeQuestionActivityCtrl, Parent> questionSceneInsteadOfActivityCtrlParentPair,
                                         Pair<KnowledgeQuestionActivityCtrl, Parent> questionSceneHowMuchActivityCtrlParentPair,
                                         Pair<OpenQuestionActivityCtrl, Parent> questionSceneGuessXActivityCtrlParentPair,
                                         Pair<ComparisonQuestionActivityCtrl, Parent> questionSceneWhatIsActivityCtrlParentPair) {
        this.alternativeQuestionActivityCtrl = questionSceneInsteadOfActivityCtrlParentPair.getKey();
        this.questionInsteadOfScene             = new Scene(questionSceneInsteadOfActivityCtrlParentPair.getValue());

        this.knowledgeQuestionActivityCtrl = questionSceneHowMuchActivityCtrlParentPair.getKey();
        this.questionHowMuchScene               = new Scene(questionSceneHowMuchActivityCtrlParentPair.getValue());

        this.openQuestionActivityCtrl = questionSceneGuessXActivityCtrlParentPair.getKey();
        this.questionGuessXScene                = new Scene(questionSceneGuessXActivityCtrlParentPair.getValue());

        this.comparisonQuestionActivityCtrl = questionSceneWhatIsActivityCtrlParentPair.getKey();
        this.questionWhatIsScene                = new Scene(questionSceneWhatIsActivityCtrlParentPair.getValue());

    }

//    public void initializeTimerScene(Pair<TimerCtrl, Parent> TimerCtrlParentPair) {
//        //this.primaryStage = primaryStage;
//        this.timerCtrl = TimerCtrlParentPair.getKey();
//        this.timerScene             = new Scene(TimerCtrlParentPair.getValue());
//
//    }

    public void showQuestionInsteadOfScene(AlternativeQuestion alternativeQuestion, int questionNo, String userName, String roomId) {
        primaryStage.setTitle("Question type 1");
        alternativeQuestionActivityCtrl.setQuestion(alternativeQuestion, questionNo, userName, roomId);
        alternativeQuestionActivityCtrl.startTimer();
        primaryStage.setScene(questionInsteadOfScene);

    }

    public void showQuestionHowMuchScene(KnowledgeQuestion knowledgeQuestion, int questionNo, String userName, String roomId) {
        primaryStage.setTitle("Question type 2");
        knowledgeQuestionActivityCtrl.setQuestion(knowledgeQuestion, questionNo, userName, roomId);
        knowledgeQuestionActivityCtrl.startTimer();
        primaryStage.setScene(questionHowMuchScene);
    }

    public void showQuestionGuessXScene(OpenQuestion openQuestion, int questionNo, String userName, String roomId) {
        primaryStage.setTitle("Question type 3");
        openQuestionActivityCtrl.setQuestion(openQuestion, questionNo, userName, roomId);
        openQuestionActivityCtrl.startTimer();
        primaryStage.setScene(questionGuessXScene);
    }

    public void showQuestionWhatIsScene(ComparisonQuestion comparisonQuestion, int questionNo, String userName, String roomId) {
        primaryStage.setTitle("Question type 4");
        comparisonQuestionActivityCtrl.setQuestion(comparisonQuestion, questionNo, userName, roomId);
        comparisonQuestionActivityCtrl.startTimer();
        primaryStage.setScene(questionWhatIsScene);
    }

    public void showMainScreen() {
        primaryStage.setTitle("Main Screen");
        primaryStage.setScene(mainScreenScene);
    }
    public void showTimerScreen() {
        primaryStage.setTitle("Timer Screen");
        primaryStage.setScene(timerScene);
    }

    public void showAddActionScene() {
        primaryStage.setTitle("Add actions");
        primaryStage.setScene(addActionScene);
    }
}
