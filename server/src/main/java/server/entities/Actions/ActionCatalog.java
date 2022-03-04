package server.entities.Actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static server.entities.Actions.Action.isSmart;

public class ActionCatalog {
    private List<Action> normalActions;
    private List<Action> smartActions;

    public ActionCatalog() {
        this.normalActions = new ArrayList<>();
        this.smartActions  = new ArrayList<>();
    }

    public ActionCatalog(List<Action> normalActions, List<Action> smartActions) {
        this.normalActions = normalActions;
        this.smartActions = smartActions;
    }

    public List<Action> getNormalActions() {
        return normalActions;
    }

    public List<Action> getSmartActions() {
        return smartActions;
    }

    public List<Action> getShuffledNormalActions() {
        Collections.shuffle(normalActions);
        return normalActions;
    }

    public List<Action> getShuffledSmartActions() {
        Collections.shuffle(smartActions);
        return smartActions;
    }

    public boolean addAction(Action action) {
        return (isSmart(action) == true) ? smartActions.add(action) : normalActions.add(action);
    }

    public static Action shiftActionsLeft(List<Action> actionsList) {
        Action action = actionsList.remove(0);
        actionsList.add(action);
        return action;
    }
}
