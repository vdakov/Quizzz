package client.logic;

import client.controllers.AddActionActivityCtrl;
import client.controllers.OverviewActionsActivityCtrl;
import client.controllers.QuestionActivityCtrl;
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
        binder.bind(OverviewActionsActivityCtrl.class) .in(Scopes.SINGLETON);
        binder.bind(QuestionActivityCtrl.class).in(Scopes.SINGLETON);
        binder.bind(AddActionActivityCtrl.class)      .in(Scopes.SINGLETON);
    }
}
