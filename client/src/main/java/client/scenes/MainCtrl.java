/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private QuoteOverviewCtrl quoteOverviewCtrl;
    private Scene quoteOverview;

    private AddQuoteCtrl addCtrl;
    private Scene addQuote;

    private ActivityOverviewCtrl actOverviewCtrl;
    private Scene actOverview;

    private AddActivityCtrl addActivityCtrl;
    private Scene addActivity;

    public void initialize(Stage primaryStage,
                           Pair<QuoteOverviewCtrl, Parent> quoteOverview,
                           Pair<AddQuoteCtrl, Parent> add,
                           Pair<ActivityOverviewCtrl, Parent> actOverview,
                           Pair<AddActivityCtrl, Parent> addAct) {

        this.primaryStage = primaryStage;

        this.quoteOverviewCtrl = quoteOverview.getKey();
        this.quoteOverview = new Scene(quoteOverview.getValue());

        this.addCtrl = add.getKey();
        this.addQuote = new Scene(add.getValue());

        this.actOverviewCtrl = actOverview.getKey();
        this.actOverview = new Scene(actOverview.getValue());

        this.addActivityCtrl = addAct.getKey();
        this.addActivity = new Scene(addAct.getValue());

        showQuotesOverview();
        primaryStage.show();
    }

    public void showQuotesOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(quoteOverview);
        quoteOverviewCtrl.refresh();
    }

    public void showActivitiesOverview() {
        primaryStage.setTitle("Activities: Overview");
        primaryStage.setScene(actOverview);
        quoteOverviewCtrl.refresh();
    }

    public void showAddQuote() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(addQuote);
        addQuote.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    public void showAddActivity() {
        primaryStage.setTitle("Activities: Adding Activity");
        primaryStage.setScene(addActivity);
        addActivity.setOnKeyPressed(e -> addActivityCtrl.keyPressed(e));
    }
}