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
import commons.Leaderboard.LeaderboardEntry;
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

    private static final String SERVER = "http://localhost:8080";

    /**
     * Creates a new SinglePlayerRomm (Multiplayer Game with one player)
     * @param userName The username of the player in the waiting room for future storing in the database
     * @return
     */
    public String createNewSinglePlayerRoom(String userName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/singlePlayer/" + userName + "/createNewGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {
                });
    }

    /**
     * Sends a question to the player
     * @param userName The username of the plazer
     * @param serverId the current gameID
     * @param number the number of the question requested
     * @return
     */
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
                .get(new GenericType<>() {
                });
    }

    public List<LeaderboardEntry> getSingleplayerLeaderboard() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/leaderboard/singleplayer") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }

    public LeaderboardEntry addSingleplayerLeaderboardEntry(String name, int points) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/leaderboard/singleplayer") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new LeaderboardEntry(name, points), APPLICATION_JSON), LeaderboardEntry.class);
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
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(a, APPLICATION_JSON), Action.class);
    }

    /**
     * Deletes an activity from the database
     * @param id the id of the activity to be deleted
     */
    public void deleteActivity(String id) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activities/delete/" + id)
                .request().delete();
    }

    /**
     * Alerts the survor of a custom message (Basically a System.out.println())
     * @param input what is printed out to the server
     */
    public void alert(String input) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activities/alert")
                .request(APPLICATION_JSON)
                .buildPost(Entity.entity(input, APPLICATION_JSON)).invoke();
    }

    /**
     * Gets a list of the current games to display them on the server browser
     * @return the list of current games
     */
    public ObservableList<GameContainer> listOfCurrentGames() {
        ArrayList<GameContainer> games = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/currentGames")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });

        return FXCollections.observableArrayList(games);
    }

    /**
     * Returns a list of all of the gameIDs- used to find whether
     * the given gameID in the server browser is valid
     * @return list of game ids
     */
    public ArrayList<String> listOfAllGameIds() {
        ArrayList<String> gameIds = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/gameIDList")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });

        return gameIds;
    }

    /**
     * Creates a new mulitplayer game waiting room, but does not start the game yet
     * @param playerId the playerID of the owner having created the game
     * @return the game id
     */
    public String createNewMultiplayerGame(String playerId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/createNewGame/" + playerId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }

    /**
     * Allows a player to join a multiplayer game with a provided gameId
     * @param playerId the username of the player joining
     * @param gameID the id of the game to be joined
     */
    public void joinExistingMultiplayerGame(String playerId, String gameID) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/joinGame/" + playerId + "/" + gameID)
                .request().get();
    }

    /**
     * Returns the number of player in the current game - used to update waiting room styling
     * @param gameID the gameID for which the amount of players is checked
     * @return the number of players in the current game
     */
    public int getNumPlayersInGame(String gameID) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/" + gameID + "/numPlayers")
                .request().get(new GenericType<>() {
                });
    }

    /**
     * Removes a player from the current game
     * @param userName the username of the player to be removed- used to locate them on the server
     * @param gameID- the gameID of the game they are removed from
     */
    public void removePlayer(String userName, String gameID) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/removePlayer/" + userName + "/" + gameID)
                .request().get();
    }

    /**
     * TODO implementation
     * Should communicate to a user whether they are the new owner or not
     * but it is debatable how to do it since we currently do not have Webscockets
     * @param gameID the gameID for which a new owner is provided
     * @return the username of the new gameowner
     */
    public String getNewGameOwner(String gameID) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiplayer/" + gameID + "/newGameOwner")
                .request().get(new GenericType<>() {
                });
    }
}