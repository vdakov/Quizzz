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

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import commons.ActionOld;
import commons.Actions.Action;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

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
    public ActionOld addActivity(ActionOld a) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/activities") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(a, APPLICATION_JSON), ActionOld.class);
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