package client;

import client.controllers.*;
import client.controllers.MultiplayerControllers.ServerBrowserController;
import client.controllers.MultiplayerControllers.WaitingRoomController;
import client.controllers.QuestionControllers.AlternativeQuestionCtrl;
import client.controllers.QuestionControllers.ComparisonQuestionCtrl;
import client.controllers.QuestionControllers.KnowledgeQuestionCtrl;
import client.controllers.QuestionControllers.OpenQuestionCtrl;
import client.logic.FXMLConfig;
import client.logic.ModuleConfig;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

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

    private final Pair<String, Class>[] scenePairs = new Pair[]{
            new Pair("MainScreenScene.fxml", MainScreenActivityCtrl.class),
            new Pair("AddActionScene.fxml", AddActionActivityCtrl.class),
            new Pair("ComparisonQuestionScene.fxml", ComparisonQuestionCtrl.class),
            new Pair("OpenQuestionScene.fxml", OpenQuestionCtrl.class),
            new Pair("KnowledgeQuestionScene.fxml", KnowledgeQuestionCtrl.class),
            new Pair("AlternativeQuestionScene.fxml", AlternativeQuestionCtrl.class),
            new Pair("ServerBrowserScene.fxml", ServerBrowserController.class),
            new Pair("WaitingRoomScene.fxml", WaitingRoomController.class)
    };

    @Override
    public void start(Stage primaryStage) {
        //make a hashmap to easily look up any scene using its filename (without the .fxml extension)
        HashMap<String, Pair<Object, Scene>> scenesMap = new HashMap<>();

        for (int i = 0; i < scenePairs.length; i++) {
            var scenePair = scenePairs[i];

            //Because the Controllers are not from the same class, they are stored as Objects and should be cast to the correct type when retrieving them
            Pair<Object, Parent> controllerParentPair = FXML_CONFIG.load(scenePair.getValue(), "scenes", scenePair.getKey());
            Pair<Object, Scene> value = new Pair<>(controllerParentPair.getKey(), new Scene(controllerParentPair.getValue())); //Pair consisting of Controller and corresponding Scene
            String key = scenePair.getKey().replace("Scene.fxml", ""); //name of the scene

            scenesMap.put(key, value);
        }

        var sceneCtrl = INJECTOR.getInstance(SceneCtrl.class);
        sceneCtrl.initialize(primaryStage, scenesMap);
    }
}
