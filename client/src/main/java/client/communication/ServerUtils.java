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

import client.data.GameConfiguration;
import commons.Actions.Action;
import commons.Leaderboard.LeaderboardEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    // the server location
    private static final String SERVER = "http://localhost:8080/";

    /**
     * Creates a new SinglePlayer game with the room owner userName
     * @param userName the userName of the player that creates the room
     * @return the roomId for the recently created room
     */
    public String createNewSinglePlayerRoom(String userName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/singlePlayer/" + userName + "/startNewGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {});
    }

    public String createNewMultiPlayerRoom(String userName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiPlayer/" + userName + "/createNewGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {});
    }

    public void joinMultiPlayerRoom(String userName, String roomId) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiPlayer/" + userName + "/" + roomId + "/joinGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {});
    }

    /**
     * Get the id for the multiplayer room composed of random players
     * @param userName the userName of the player requesting this information
     * @return the roomId of the random multiPlayer room
     */
    public String getRandomMultiPlayerRoomId(String userName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiPlayer/" + userName + "/getRandomRoomCode")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {});
    }

    /**
     * Starts a multiPlayer game
     * @param userName
     * @param roomId
     */
    public void startMultiPlayerRoom(String userName, String roomId) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiPlayer/" + userName + "/" + roomId + "/startGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {});
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    private boolean startedGame = false;

    public boolean isGameStarted() {
        return startedGame;
    }
    /**
     *
     * @param userName
     * @param roomId
     * @return
     */
    public void waitForMultiPlayerRoomStart(String userName, String roomId) {
        EXEC.submit(() -> {
           var res = ClientBuilder.newClient(new ClientConfig())
                   .target(SERVER).path("api/multiPlayer/" + userName + "/" + roomId + "/waitForGameToStart")
                   .request(APPLICATION_JSON)
                   .accept(APPLICATION_JSON).get(Response.class);

           if (res.getStatus() == 204) {
               return;
           }

            startedGame = true;
        });

    }

    public String getQuestion() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        String gameType = (gameConfiguration.isSinglePlayer()) ? "singlePlayer" : "multiPlayer";


        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/" + gameType + "/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getRoomId() + "/" +
                        gameConfiguration.getCurrentQuestionNumber() + "/getQuestion")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(String.class);
    }

    public void updateScore(String answer) {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        String gameType = (gameConfiguration.isSinglePlayer()) ? "singlePlayer" : "multiPlayer";

        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/" + gameType + "/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getRoomId() + "/" +
                        gameConfiguration.getCurrentQuestionNumber() + "/updateAnswer")
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.text(answer));
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
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/add") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(a, APPLICATION_JSON), Action.class);
    }

    /**
     * Remove the original activity by the id
     * @param id
     */
    public void deleteActivity(String id) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/delete/" + id) //
                .request().delete(); //;
    }

    /**
     * Updates the original activity with new data
     * @param id
     * @param a
     */
    public void editActivity(String id, Action a) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/update/" + id) //
                .request() //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(a, APPLICATION_JSON), Action.class); //;
    }

    /**
     * Get a single activity by id
     * @param id
     * @return the action containing the given id
     */
    public Action getActivityById(String id) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/" + id) //
                .request() //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }

    public void alert() {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/alert") //
                .request().get(); //;
    }

    public String getAnswer() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        String gameType = (gameConfiguration.isSinglePlayer()) ? "singlePlayer" : "multiPlayer";

        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/" + gameType + "/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getRoomId() + "/" +
                        gameConfiguration.getCurrentQuestionNumber() + "/getAnswer")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(String.class);
    }

    public String getScore() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        String gameType = (gameConfiguration.isSinglePlayer()) ? "singlePlayer" : "multiPlayer";

        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/" + gameType + "/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getRoomId() + "/getScore")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(String.class);
    }


}