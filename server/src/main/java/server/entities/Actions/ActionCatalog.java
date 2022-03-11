package server.entities.Actions;

import commons.Actions.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ActionCatalog {
    private List<Action> normalActions;
    private List<Action> smartActions;

    private List<Action> usedSmartActions;

    public ActionCatalog() {
        this.normalActions    = new ArrayList<>();
        this.smartActions     = new ArrayList<>();
        this.usedSmartActions = new ArrayList<>();
    }

    public List<Action> getNormalActions() {
        return this.normalActions;
    }

    public List<Action> getSmartActions() {
        return this.smartActions;
    }

    public List<Action> getUsedSmartActions() {
        return this.usedSmartActions;
    }

    public List<Action> shuffleNormalActions() {
        Collections.shuffle(normalActions);
        return this.normalActions;
    }

    public List<Action> shuffleSmartActions() {
        Collections.shuffle(smartActions);
        return this.smartActions;
    }

    public List<Action> shuffleUsedSmartActions() {
        Collections.shuffle(usedSmartActions);
        return this.usedSmartActions;
    }

    public boolean addAction(Action action) {
        return (action.isSmart() == true) ? smartActions.add(action) : normalActions.add(action);
    }

    public Action getNormalAction() {
        return this.normalActions.remove(0);
    }

    public Action getSmartAction() {
        Action temp = this.smartActions.remove(0);
        this.usedSmartActions.add(temp);
        return temp;
    }

    public Action getUsedSmartAction() {
        Action temp = this.usedSmartActions.remove(0);
        this.usedSmartActions.add(temp);
        return temp;
    }

    public Action getAction(int referencePoint, int lowerBoundDifferencePercentage, int upperBoundDifferencePercentage, Random random) {
        // start searching lower
        int  firstNewLowerBound = (int) (referencePoint - (upperBoundDifferencePercentage * 1.0 / 100) * referencePoint);
        int  firstNewUpperBound = (int) (referencePoint - (lowerBoundDifferencePercentage * 1.0 / 100) * referencePoint);

        Action searchResult = searchForConsumption(firstNewLowerBound, firstNewUpperBound);
        if (searchResult != null) {
            return searchResult;
        }

        int  secondNewLowerBound = (int) (referencePoint + (upperBoundDifferencePercentage * 1.0 / 100) * referencePoint);
        int  secondNewUpperBound = (int) (referencePoint + (lowerBoundDifferencePercentage * 1.0 / 100) * referencePoint);

        searchResult = searchForConsumption(secondNewLowerBound, secondNewUpperBound);
        if (searchResult != null) {
            return searchResult;
        }

        return getSmartAction();
    }

    public Action searchForConsumption(int lowerBound, int upperBound) {
        for (Action action : normalActions) {
            if (lowerBound < action.getConsumption() && action.getConsumption() < upperBound) {
                return action;
            }
        }

        for (Action action : smartActions) {
            if (lowerBound < action.getConsumption() && action.getConsumption() < upperBound) {
                return action;
            }
        }

        for (Action action : usedSmartActions) {
            if (lowerBound < action.getConsumption() && action.getConsumption() < upperBound) {
                return action;
            }
        }

        return null;
    }
}
