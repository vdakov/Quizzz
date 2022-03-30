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

import client.Chat.ChatEntry;
import client.data.GameConfiguration;
import commons.Actions.Action;
import commons.GameContainer;
import commons.Leaderboard.LeaderboardEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.tomcat.util.codec.binary.Base64;
import org.glassfish.jersey.client.ClientConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;


public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

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
                    return  null;
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
     * Starts the desired room and returns true is successful or false/null if some error occurred
     *
     * @return true if the room was successfully started or false / null otherwise
     */
    public Boolean startRoom() {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig())
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/startRoom")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON).get(Response.class);

            switch (response.getStatus()) {
                case 200: {
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
                    return  null;
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

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    public void waitForMultiPlayerRoomStart(Consumer<String> startedGame) {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        EXEC.submit(() -> {
            while (!Thread.interrupted()) {

                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/"
                                + gameConfiguration.getRoomId() + "/waitForGameToStart")
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON).get(Response.class);

                if (res.getStatus() == 204) {
                    continue;
                }

                String gameInProgress = res.readEntity(String.class);
                startedGame.accept(gameInProgress);
                this.stop();
            }
        });
    }

    public void stop() {
        EXEC.shutdownNow();
    }

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
                    return  null;
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

    public void updateScore(String answer) {
        try {
            GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();

            Response response = ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/" + gameConfiguration.getGameTypeString() + "/" + gameConfiguration.getRoomId() + "/" +
                            gameConfiguration.getCurrentQuestionNumber() + "/postAnswer")
                    .request(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .post(Entity.text(answer));

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
            System.out.println("An exception occurred when trying to update the score");
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

    public List<LeaderboardEntry> getLeaderboard(String roomId) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/leaderboard/" + roomId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<>() {
                });
    }

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

    public List<ChatEntry> getPlayersActivity() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("topic/emojis")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }


    public ChatEntry addChatEntry(String name, ImageView imageView) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("topic/emojis")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(new ChatEntry(name, imageView), APPLICATION_JSON), ChatEntry.class);
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


    public void deleteActivity(String id) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/delete/" + id) //
                .request().delete(); //;
    }

    public void editActivity(String id, Action a) {
        ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities/update") //
                .request() //
                .accept(APPLICATION_JSON) //
                .put(Entity.entity(a, APPLICATION_JSON), Action.class); //;
    }

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
                    return null;
                    // something failed, show an apology message ?
                }
                case 400: {
                    System.out.println("The request was invalid when trying to get the answer");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to get the answer");
        }

        return null;
    }

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
                    return  null;
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

    public void removePlayer(String userName, String gameID) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/MULTIPLAYER/" + userName + "/" + gameID + "/" + "removePlayer")
                .request().get();
    }

    public int getNumPlayers() {
        GameConfiguration gameConfiguration = GameConfiguration.getConfiguration();
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("api/" + gameConfiguration.getUserName() + "/MULTIPLAYER/" + gameConfiguration.getRoomId() + "/numPlayers")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }
}