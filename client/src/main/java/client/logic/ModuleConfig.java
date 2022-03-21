package client.logic;

import client.controllers.AddActionActivityCtrl;
import client.controllers.MainScreenActivityCtrl;
import client.controllers.QuestionControllers.AlternativeQuestionActivityCtrl;
import client.controllers.QuestionControllers.ComparisonQuestionActivityCtrl;
import client.controllers.QuestionControllers.KnowledgeQuestionActivityCtrl;
import client.controllers.QuestionControllers.OpenQuestionActivityCtrl;
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
    }
}
