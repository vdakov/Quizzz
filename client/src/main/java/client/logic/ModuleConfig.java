package client.logic;

import client.controllers.*;
import client.controllers.QuestionControllers.QuestionAlternativeCtrl;
import client.controllers.QuestionControllers.QuestionComparisonCtrl;
import client.controllers.QuestionControllers.QuestionKnowledgeCtrl;
import client.controllers.QuestionControllers.QuestionOpenCtrl;
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

        binder.bind(QuestionOpenCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuestionKnowledgeCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuestionAlternativeCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuestionComparisonCtrl.class).in(Scopes.SINGLETON);
    }
}
