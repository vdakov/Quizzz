package client;

import client.controllers.*;
import client.controllers.QuestionControllers.AlternativeQuestionActivityCtrl;
import client.controllers.QuestionControllers.ComparisonQuestionActivityCtrl;
import client.controllers.QuestionControllers.KnowledgeQuestionActivityCtrl;
import client.controllers.QuestionControllers.OpenQuestionActivityCtrl;
import client.logic.FXMLConfig;
import client.logic.ModuleConfig;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.inject.Guice.createInjector;


public class QuizzzClient extends Application {

    private static final Injector INJECTOR = createInjector(new ModuleConfig());
    private static final FXMLConfig FXML_CONFIG = new FXMLConfig(INJECTOR);


    /**
     * The main method that launches the client-side app
     *
     * @param args the arguments for running the app
     * @throws URISyntaxException is thrown when a URI without the correct format is parsed
     * @throws IOException        is thrown when an error regarding Input or Output occurs while the app runs
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch(QuizzzClient.class, args);
    }

    /**
     * Initializes the first stage of the app with all the needed scenes
     *
     * @param primaryStage represents the main stage of the app
     * @throws IOException is thrown when an error regarding Input or Output occurs while the app runs
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        var mainScene     = FXML_CONFIG.load(MainScreenActivityCtrl.class, "scenes", "MainScreenScene.fxml");
        var addActionScene = FXML_CONFIG.load(AddActionActivityCtrl.class, "scenes", "AddActionScene.fxml");

        var questionScreenWhatIsActivity      = FXML_CONFIG.load(ComparisonQuestionActivityCtrl.class, "scenes/QuestionScene", "ComparisonQuestionScene.fxml");
        var questionScreenGuessXActivity      = FXML_CONFIG.load(OpenQuestionActivityCtrl.class, "scenes/QuestionScene", "OpenQuestionScene.fxml");
        var questionScreenHowMuchActivity   = FXML_CONFIG.load(KnowledgeQuestionActivityCtrl.class, "scenes/QuestionScene", "KnowledgeQuestionScene.fxml");
        var questionScreenInsteadOfActivity = FXML_CONFIG.load(AlternativeQuestionActivityCtrl.class, "scenes/QuestionScene", "AlternativeQuestionScene.fxml");


        var sceneCtrl = INJECTOR.getInstance(SceneCtrl.class);
        sceneCtrl.initializeMainScenes(primaryStage, mainScene, addActionScene);
        sceneCtrl.initializeQuestionScenes(questionScreenInsteadOfActivity, questionScreenHowMuchActivity, questionScreenGuessXActivity, questionScreenWhatIsActivity);
    }
}
