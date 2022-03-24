package client.logic;

import client.controllers.AddActionActivityCtrl;
import client.controllers.MainScreenActivityCtrl;
import client.controllers.MultiplayerControllers.ServerBrowserController;
import client.controllers.MultiplayerControllers.WaitingRoomController;
import client.controllers.QuestionControllers.*;
import client.controllers.SceneCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class ModuleConfig implements Module {

    /**
     * Binds every control to the class
     * @param binder the binder that is used to store all the binds
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(SceneCtrl.class).in(Scopes.SINGLETON);
        binder.bind(MainScreenActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AddActionActivityCtrl.class).in(Scopes.SINGLETON);

        binder.bind(OpenQuestionActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(KnowledgeQuestionActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AlternativeQuestionActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(ComparisonQuestionActivityCtrl.class).in(Scopes.SINGLETON);

        binder.bind(QuestionActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(WaitingRoomController.class).in(Scopes.SINGLETON);
        binder.bind(ServerBrowserController.class).in(Scopes.SINGLETON);
    }
}
