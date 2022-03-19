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
package client.communication;

import commons.Actions.Action;
import commons.GameContainer;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.glassfish.jersey.client.ClientConfig;

import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "fw";

    public String createNewSinglePlayerRoom(String userName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/singlePlayer/" + userName + "/createNewGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {

                });
    }

    public String getQuestion(String userName, String serverId, int number) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/singlePlayer/" + userName + "/" + serverId + "/" + number + "/getQuestion")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(String.class);
    }

    /**
     * Gets the activities from the server
     *
     * @return the list of activities from the server
     */
    public List<Action> getActivities() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/list") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {});
    }

    public Action getRandomAction() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/random") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }

    /**
     * Sends a new activity to the server
     *
     * @param a the action that will be added
     * @return the action that was added
     */
    public Action addActivity(Action a) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(a, APPLICATION_JSON), Action.class);
    }

    public void deleteActivity(String id) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/delete/" + id) //
                .request().delete(); //;
    }

    public void alert(String input) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/alert") //
                .request(APPLICATION_JSON)
                .buildPost(Entity.entity(input, APPLICATION_JSON)).invoke(); //;
    }

    public ObservableList<GameContainer> listOfCurrentGames(){
       ArrayList<GameContainer> games=  ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayer/currentGames") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {});

       ObservableList<GameContainer> currentGames= FXCollections.observableArrayList(games);

       return currentGames;
    }

    public ArrayList<String> listOfAllGameIds(){
        ArrayList<String> gameIds=  ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/multiplayer/gameIDList") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {});

        return gameIds;
    }

    public String createNewMultiplayerGame(String playerId){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/createNewGame/"+playerId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>(){});
    }

    public void joinExistingMultiplayerGame(String playerId, String gameID){
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/joinGame/"+playerId +"/" + gameID)
                .request().get();
    }

    public int getNumPlayersInGame(String gameID){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/"+gameID+"/numPlayers")
                .request().get(new GenericType<>(){});
    }

    public void removePlayer(String userName, String gameID){
         ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/"+userName+"/"+gameID)
                .request().get();
    }
}