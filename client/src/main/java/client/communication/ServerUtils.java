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
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

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
                .target(SERVER).path("api/singlePlayer/" + userName + "/createNewGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {});
    }

    /**
     * Requests a question formatted as a String from the server
     * @param userName the userName of the player requesting the information
     * @param roomId   the id of the room the user is playing in
     * @param number   the number of the desired question
     * @return the question parsed as a String
     */
    public String getSinglePlayerQuestion(String userName, String roomId, int number) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/singlePlayer/" + userName + "/" + roomId + "/" + number + "/getQuestion")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(String.class);
    }

    /**
     * Creates a new MultiPlayer game with the room owner userName
     * @param userName the userName of the player that creates the room
     * @return the roomId for the recently created room
     */
    public String createNewMultiPlayerRoom(String userName) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiPlayer/" + userName + "/createNewGame")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(new GenericType<>() {});
    }

    /**
     * Join a multiplayer room with the given roomId
     * @param userName the userName of the player that wants to join the room
     * @param roomId the id of the room the player wants to join
     */
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

    /**
     *
     * @param userName
     * @param roomId
     * @return
     */
    public boolean waitForMultiPlayerRoomStart(String userName, String roomId) {
        //TODO
        return false;
    }

    /**
     *
     * @param userName
     * @param roomId
     * @param number
     * @return
     */
    public String getMultiPlayerQuestion(String userName, String roomId, int number) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/multiPlayer/" + userName + "/" + roomId + "/" + number + "/getQuestion")
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

    public void alert() {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/alert") //
                .request().get(); //;
    }
}