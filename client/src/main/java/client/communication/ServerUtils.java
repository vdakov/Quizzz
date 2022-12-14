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
import commons.GameContainer;
import commons.Leaderboard.LeaderboardEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.tomcat.util.codec.binary.Base64;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class ServerUtils {

    protected String SERVER = "http://localhost:8080/"; // game config

    public ServerUtils() throws ExecutionException, InterruptedException {
    }

    /**
     * Creates a new room based on the player's choices
     *
     * @return the id of the room the player has created or null if an exception occurred
     */
    public String createNewRoom() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/createNewRoom")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    return response.readEntity(String.class);
                }
                case 417: {
                    System.out.println("Expectation failed when creating a new room");
                    return null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when creating a new room");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to create a new room");
        }

        return null;
    }

    /**
     * Sets the server
     * @param server the server in String format
     */
    public void setSERVER(String server) {
        this.SERVER = server;
        System.out.println(this.SERVER);
    }

    /**
     * Starts the desired room and returns true is successful or false/null if some error occurred
     *
     * @return true if the room was successfully started or false / null otherwise
     */
    public Boolean startRoom() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            System.out.println("I pressed the button");

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/startRoom")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    stopWaitForRoomThread();
                    return true;
                }
                case 417: {
                    System.out.println("Expectation failed when trying to start the room");
                    return  false;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to start the room");
                    return false;
                }
            }

        } catch (Exception e) {
            System.out.println("An exception occurred when trying to start the room");
        }

        return null;
    }

    /**
     * Indicates whether a multiplayer room was joined succesfully
     * @return a boolean value, true if the room was joined, false otherwise
     */
    public Boolean joinMultiPlayerRoom() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/joinGame")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    return true;
                }
                case 417: {
                    System.out.println("Expectation failed when trying to join a room");
                    return false;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to join a room");
                    return false;
                }
            }

        } catch (Exception e) {
            System.out.println("An exception occurred when trying to join a room");
        }

        return false;
    }

    /**
     * Get the id for the multiplayer room composed of random players
     *
     * @return the roomId of the random multiPlayer room
     */
    public String getRandomMultiPlayerRoomId() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/getRandomRoom")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    return response.readEntity(String.class);
                }
                case 417: {
                    System.out.println("Expectation failed when trying to get the random room");
                    return null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to get the random room");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to get the random room");
        }

        return null;
    }

    private static ExecutorService EXEC_WAIT_FOR_START = Executors.newSingleThreadExecutor();

    /**
     * Waits for the a  Multiplayer game to be started
     * @param startedGame a boolean value that indicates whether a game was started
     */
    public void waitForMultiPlayerRoomStart(Consumer<Boolean> startedGame) {
        if (EXEC_WAIT_FOR_START.isShutdown()) {
            EXEC_WAIT_FOR_START = Executors.newSingleThreadExecutor();
        }

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

        EXEC_WAIT_FOR_START.submit(() -> {
            while (!Thread.interrupted()) {

                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/"
                                + gameConfiguration.getRoomId() + "/waitForGameToStart")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).get(Response.class);

                if (res.getStatus() == 204) {
                    continue;
                }

                Boolean gameInProgress = res.readEntity(Boolean.class);

                if (gameInProgress) {
                    System.out.println("Game started 1234567890");
                    startedGame.accept(true);
                    //this.stopWaitForRoomThread();
                }
            }
        });
    }

    public void stopWaitForRoomThread() {
        EXEC_WAIT_FOR_START.shutdownNow();
    }

    private static ExecutorService EXEC_GET_PLAYER_NUMBER = Executors.newSingleThreadExecutor();

    /**
     * Updates the player number
     * @param playerNumber the current player number
     */
    public void updatePlayerNumber(Consumer<Integer> playerNumber) {
        if (EXEC_GET_PLAYER_NUMBER.isShutdown()) {
            EXEC_GET_PLAYER_NUMBER = Executors.newSingleThreadExecutor();
        }

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        EXEC_GET_PLAYER_NUMBER.submit(() -> {
            while (!Thread.interrupted()) {

                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/"
                                + gameConfiguration.getRoomId() + "/getPlayerNumber")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).get(Response.class);

                if (res.getStatus() == 204) {
                    continue;
                }

                Integer gameInProgress = res.readEntity(Integer.class);
                playerNumber.accept(gameInProgress);
            }
        });
    }

    /**
     * Stops updating the player number
     */
    public void stopUpdatePlayerNumber() {
        EXEC_GET_PLAYER_NUMBER.shutdownNow();
    }

    private static ExecutorService EXEC_UPDATE_AVAILABLE_ROOMS = Executors.newSingleThreadExecutor();

    /**
     * Updates the available rooms
     * @param roomUpdate a value of type Game Container
     */
    public void updateAvailableRooms(Consumer<GameContainer> roomUpdate) {
        if (EXEC_UPDATE_AVAILABLE_ROOMS.isShutdown()) {
            EXEC_UPDATE_AVAILABLE_ROOMS = Executors.newSingleThreadExecutor();
        }

        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        EXEC_UPDATE_AVAILABLE_ROOMS.submit(() -> {
            while (!Thread.interrupted()) {

                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/updateRooms")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).get(Response.class);

                if (res.getStatus() == 204) {
                    continue;
                }

                GameContainer gameInProgress = res.readEntity(GameContainer.class);

                roomUpdate.accept(gameInProgress);
            }
        });
    }

    /**
     * Stops updating the availble rooms
     */
    public void stopUpdateAvailableRooms() {
        EXEC_UPDATE_AVAILABLE_ROOMS.shutdownNow();
    }

    private static ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * Waits for the leaderboard to be filled
     * @param l List containing leaderboard entries
     */
    public void waitForFilledLeaderboard(Consumer<List<LeaderboardEntry>> l) {
        if (EXEC.isShutdown()) EXEC = Executors.newSingleThreadExecutor();
        EXEC.submit(() -> {
            while (!Thread.interrupted()) {

                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("api/leaderboard/filledLeaderboard/" + GameConfiguration.getConfiguration().getRoomId())
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).get(Response.class);

                if (res.getStatus() == 204) {
                    continue;
                }

                List<LeaderboardEntry> leaderboard = res.readEntity(List.class);
                l.accept(leaderboard);
                stop();
            }
        });
    }

    /**
     * Cjecks if the leaderboard is full
     */
    public void checkLeaderboardFilled() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/leaderboard/checkLeaderboardFilled/" + gameConfiguration.getRoomId())
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get();
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to check if leaderboard is ready");
        }
    }

    public void stop() {
        EXEC.shutdownNow();
    }


    /**
     * Gets the question from the server
     * @return the question as a String
     */
    public String getQuestion() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/" +
                            gameConfiguration.getCurrentQuestionNumber() + "/getQuestion")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    return response.readEntity(String.class);
                }
                case 417: {
                    System.out.println("Expectation failed when trying to get the question");
                    return null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to get the question");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to get the question");
        }

        return null;
    }

    /**
     * Updates the player score stored in server by adding points earned in that question
     * @param answer    the user's answer that should be compared with the correct answer
     */
    public void updateScore(String answer) {
        if (answer == null) {
            return;
        }

        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            System.out.println("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() +
                    "/" + gameConfiguration.getRoomId() + "/" + gameConfiguration.getCurrentQuestionNumber() + "/" +
                    "postAnswer");

            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() +
                            "/" + gameConfiguration.getRoomId() + "/" + gameConfiguration.getCurrentQuestionNumber() + "/" +
                            "postAnswer")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .post(Entity.text(answer));


            System.out.println("Update Score : " + response.getStatus());
            switch (response.getStatus()) {
                case 200: {
                    break;
                }
                case 417: {
                    System.out.println("Expectation failed when trying to update the score");
                    break;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to update the score");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An exception occurred when trying to update the score");
        }
    }

    /**
     * Uses the hint joker
     */
    public void useHintJoker() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" +
                            gameConfiguration.getRoomId() + "/useHintJoker")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get();

            System.out.println("Response status: " + response.getStatus());


        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
    }

    /**
     * Uses the double point joker
     */
    public void useDoublePointJoker() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" +
                            gameConfiguration.getRoomId() + "/useDoublePointJoker")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get();

            System.out.println("Response status: " + response.getStatus());

        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
    }



    /**
     * Uses the time joker
     */
    public void useTimeJoker() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/" +  gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" +
                            gameConfiguration.getRoomId() + "/" + gameConfiguration.getCurrentQuestionNumber() + "/useTimeJoker")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .get();

        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
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

    /**
     * Gets the leaderboard for a particular room by searching for its id
     * @param roomId the roomId of the room we want to retrieve the leaderboard
     * @return a list with leaderboard entries
     */
    public List<LeaderboardEntry> getLeaderboard(String roomId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/leaderboard/" + roomId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }

    /**
     * Adds or Updates a Leaderboard entry
     * @param name the name of the player
     * @param roomId the roomId for which we want to update the leaderboard
     * @param points the number of points they acquired
     * @return a leaderboard entry
     */
    public LeaderboardEntry addOrUpdateLeaderboardEntry(String name, String roomId, int points) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/leaderboard/") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(new LeaderboardEntry(name, roomId, points, GameConfiguration.getConfiguration().isSinglePlayer()), APPLICATION_JSON), LeaderboardEntry.class);
    }

    /**
     * Deletes entries that belong to a room with this roomId
     *
     * @param roomId the roomId of the entries to delete
     */
    public void deleteEntries(String roomId) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/leaderboard/remove/" + roomId) //
                .request(APPLICATION_JSON) //
                .delete();
    }

    // Where we connect to the websocket

    private StompSession session = connect("ws://localhost:8080/websocket");

    /**
     * Methods that creates the connection
     * @param url where we get connected
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private StompSession connect(String url) throws ExecutionException, InterruptedException {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);

        stomp.setMessageConverter(new MappingJackson2MessageConverter());
        return stomp.connect(url, new StompSessionHandlerAdapter() {
        }).get();
    }

    /**
     * We are subscribed for a whenever there is a message on the destination path
     *
     * @param destination /topic/emojis
     * @param consumer    that is informed whenever a new message is received
     */
    public void registerForMessages(String destination, Consumer<List<String>> consumer) {
        try {
            session.subscribe(destination, new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return List.class;                                    // the type of message we expect to receive
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    consumer.accept((List<String>) payload);
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * Method that sends the message
     *
     * @param destination /topic/emojis
     * @param o           the payload
     */
    public void send(String destination, Object o) {
        session.send(destination, o);
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
                .put(Entity.entity(a, APPLICATION_JSON), Action.class);
    }


    /**
     * deltes an activity
     * @param id the id of the activity
     */
    public void deleteActivity(String id) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/delete/" + id) //
                .request().delete(); //;
    }

    /**
     * Edits an activity
     * @param id the id of the activity
     * @param a the action
     */
    public void editActivity(String id, Action a) {
        System.out.println(id);
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/update") //
                .request() //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(a, APPLICATION_JSON), Action.class); //;
    }

    /**
     *Gets an activity by searching fo its id
     * @param id the id of the activity we want to retrieve
     * @return an action
     */
    public Action getActivityById(String id) {
        try {
            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/activities/" + id) //
                    .request() //
                    .accept(APPLICATION_JSON) //
                    .get(new GenericType<>() {
                    });

            switch (response.getStatus()) {
                case 200: {
                    System.out.println("Am intrat");
                    return response.readEntity(Action.class);
                }
                case 417: {
                    System.out.println("Could not get activity by id");
                    return null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
        return null;
    }

    public void alert() {
        try {
            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/activities/alert") //
                    .request().get(); //;

            switch (response.getStatus()) {
                case 200: {
                    System.out.println("Am intrat");
                }
                case 417: {
                    System.out.println("Expectation failed");
                }
                case 400: {
                    System.out.println("The request was invalid");
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
    }

    /**
     * Gets an answer from the server
     * @return the answer as a String
     */
    public String getAnswer() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/" +
                            gameConfiguration.getCurrentQuestionNumber() + "/getAnswer")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);


            switch (response.getStatus()) {
                case 200: {
                    return response.readEntity(String.class);
                }
                case 417: {
                    System.out.println("Expectation failed when trying to get the answer");
                    System.out.println("Could not get answer of question");
                    return null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to get the answer DA");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to get the answer");
        }

        return null;
    }

    /**
     * Gets the current score of the player
     * @return current score of the player
     */
    public String getScore() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/getScore")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    return response.readEntity(String.class);
                }
                case 417: {
                    System.out.println("Expectation failed when trying to get the player's score");
                    return null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to get the player's score");
                    return "0";
                    //return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to get the player's score");
        }

        return null;
    }

    public Integer getTimeClient() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/"
                            + gameConfiguration.getRoomId() + "/" + gameConfiguration.getCurrentQuestionNumber() + "/getTimeClient")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);
            return response.readEntity(Integer.class);

        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
        return 10;
    }

    /**
     * Gets the point player earned for that specific question
     * @return integer value of points earned
     */
    public Integer getAddedPoints() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/getAddedPoints")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    return response.readEntity(Integer.class);
                }
                case 417: {
                    System.out.println("Expectation failed");
                    return  null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid");
                    return 0;
                    //return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred");
        }

        return null;
    }

    /**
     * Checks whether the hint joker is used or not
     * @return true when the hint joker is used
     */
    public Boolean getHintJokerUsed() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/getHintJokerUsed")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);
            return response.readEntity(Boolean.class);

        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
        return true;
    }

    /**
     * Checks whether the double point joker is used or not
     * @return true when the double point joker is used
     */
    public Boolean getDoublePointJokerUsed() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/getDoublePointJokerUsed")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);
            return response.readEntity(Boolean.class);

        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
        return true;
    }

    /**
     * Sets the time left
     * @return a integer value of the time left
     */
    public Integer setTimeLeft() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() +
                            "/" + gameConfiguration.getRoomId() + "/" + gameConfiguration.getTimeLeft() + "/setTimeLeft")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
                    return response.readEntity(Integer.class);
                }
                case 417: {
                    System.out.println("Expectation failed");
                    return  null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid");
                    return 0;
                    //return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred");
        }

        return null;
    }

    /**
     Checks whether the time joker is used or not
     * @return true when the time joker is used
     */
    public Boolean getTimeJokerUsed() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/getTimeJokerUsed")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);
            return response.readEntity(Boolean.class);

        } catch (Exception e) {
            System.out.println("An exception occurred");
        }
        return true;
    }

    /**
     * HTTP request for receiving a byte array containing the image from the server
     *
     * @param imagePath the path on the server where the image is stored
     * @return a byte array of images that is later converted to an image displayed in the actual game
     */
    public byte[] getQuestionImage(String imagePath) {

        String base64 = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activities/sendImage/" + imagePath)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON).get(String.class);

        return Base64.decodeBase64(base64);

    }

    /**
     * PUT HTTP request that will later be used to send images to the server from the adming interface
     *
     * @param base64Image the base64 string of the image
     * @param imageName   the name as which the image will be saved as
     */
    public void sendImage(String base64Image, String imageName) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/receiveImage/" + imageName) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(base64Image, APPLICATION_JSON), String.class);

    }

    /**
     * PUT HTTP request that will later be used to send images to the server from the adming interface
     *
     * @param base64Image the base64 string of the image
     * @param imageName   the name as which the image will be saved as
     */
    public void sendImageEdit(String base64Image, String imageName, String imageFolder) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/receiveImageEdit/" + imageName + "/" + imageFolder) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(base64Image, APPLICATION_JSON), String.class);

    }


    /**
     * Gets a list of the current games to display them on the server browser
     *
     * @return the list of current games
     */
    public ObservableList<GameContainer> listOfCurrentGames(String username) {

        ArrayList<GameContainer> games = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/" + username + "/MULTIPLAYER" + "/getGames")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });

        return FXCollections.observableArrayList(games);
    }

    /**
     * Removes a player
     */
    public void removePlayer() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        System.out.println("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/" + gameConfiguration.getRoomId() + "/removePlayer");
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/" + gameConfiguration.getRoomId() + "/removePlayer")
                .request().get();
    }

    /**
     * Gets the number of players
     * @return the number of players as a int
     */
    public int getNumPlayers() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();


        System.out.println("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/" + gameConfiguration.getRoomId() + "/numPlayers");
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/" + gameConfiguration.getRoomId() + "/numPlayers")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }

    /**
     * Restores the activity bank
     */
    public void restoreActivityBank() {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/activities/restoreActivityBank")
                .request().get();
    }


}