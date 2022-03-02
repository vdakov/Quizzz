package client.logic;

import client.scenes.ActivityOverviewCtrl;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class ModuleConfig implements Module {

    /**
     * Binding everything for the
     * @param binder
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(ActivityOverviewCtrl.class).in(Scopes.SINGLETON);
    }
}
