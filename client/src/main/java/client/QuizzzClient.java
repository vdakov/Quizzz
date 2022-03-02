package client;

import static com.google.inject.Guice.createInjector;
import javafx.application.Application;

import client.logic.FXMLConfig;
import client.logic.ModuleConfig;
import client.scenes.*;
import com.google.inject.Injector;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;


public class QuizzzClient extends Application {

    private static final Injector INJECTOR = createInjector(new ModuleConfig());
    private static final FXMLConfig FXML_CONFIG = new FXMLConfig(INJECTOR);

    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
        //Application.launch(QuizzzClient.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var addActivity = FXML_CONFIG.load(AddActivityCtrl.class, "client", "scenes", "AddActivity.fxml");
        var actOverview = FXML_CONFIG.load(ActivityOverviewCtrl.class, "client", "scenes", "ActivityOverview.fxml");

        var sceneCtrl = INJECTOR.getInstance(SceneCtrl.class);
        sceneCtrl.initialize(primaryStage, actOverview, addActivity);
    }
}
