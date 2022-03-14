package client.logic;

import client.controllers.*;
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

        binder.bind(QuestionSceneGuessXActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuestionSceneHowMuchActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuestionSceneInsteadOfActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(QuestionSceneWhatIsActivityCtrl.class).in(Scopes.SINGLETON);
    }
}
