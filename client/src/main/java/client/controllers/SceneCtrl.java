package client.controllers;

import client.communication.ServerUtils;
import client.controllers.QuestionControllers.AlternativeQuestionActivityCtrl;
import client.controllers.QuestionControllers.ComparisonQuestionActivityCtrl;
import client.controllers.QuestionControllers.KnowledgeQuestionActivityCtrl;
import client.controllers.QuestionControllers.OpenQuestionActivityCtrl;
import client.data.GameConfiguration;
import client.logic.QuestionParsers;
import commons.Questions.AlternativeQuestion;
import commons.Questions.ComparisonQuestion;
import commons.Questions.KnowledgeQuestion;
import commons.Questions.OpenQuestion;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.inject.Inject;
import java.util.Scanner;

public class SceneCtrl {

    private Stage primaryStage;

    private ServerUtils serverUtils;
    private GameConfiguration gameConfiguration;

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

    @Inject
    public void initialiseServer(ServerUtils serverUtils) {
        this.serverUtils = serverUtils;
        this.gameConfiguration = GameConfiguration.getConfiguration();
    }

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

    public void showNextQuestion() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        gameConfiguration.setCurrentQuestionNumber(gameConfiguration.getCurrentQuestionNumber() + 1);
        String response = serverUtils.getQuestion();
        Scanner scanner = new Scanner(response).useDelimiter(": ");
        String questionType = scanner.next();
        primaryStage.setTitle("Question #" + gameConfiguration.getCurrentQuestionNumber() + ": " + questionType);
        switch (questionType) {
            case "OpenQuestion": {
                this.showQuestionGuessXScene(QuestionParsers.openQuestionParser(scanner.next()));
                break;
            }
            case "KnowledgeQuestion": {
                this.showQuestionHowMuchScene(QuestionParsers.knowledgeQuestionParser(scanner.next()));
                break;
            }
            case "ComparisonQuestion": {
                this.showQuestionWhatIsScene(QuestionParsers.comparisonQuestionParser(scanner.next()));
                break;
            }
            case "AlternativeQuestion": {
                this.showQuestionInsteadOfScene(QuestionParsers.alternativeQuestionParser(scanner.next()));
                break;
            }
        }
    }

    public void showQuestionInsteadOfScene(AlternativeQuestion alternativeQuestion) {
        alternativeQuestionActivityCtrl.displayQuestion(alternativeQuestion);
        primaryStage.setScene(questionInsteadOfScene);
    }

    public void showQuestionHowMuchScene(KnowledgeQuestion knowledgeQuestion) {
        knowledgeQuestionActivityCtrl.displayQuestion(knowledgeQuestion);
        primaryStage.setScene(questionHowMuchScene);
    }

    public void showQuestionGuessXScene(OpenQuestion openQuestion) {
        openQuestionActivityCtrl.displayQuestion(openQuestion);
        primaryStage.setScene(questionGuessXScene);
    }

    public void showQuestionWhatIsScene(ComparisonQuestion comparisonQuestion) {
        comparisonQuestionActivityCtrl.displayQuestion(comparisonQuestion);
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
