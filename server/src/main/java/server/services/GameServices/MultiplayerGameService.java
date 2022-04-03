package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.GameContainer;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.controllers.GameControllers.GameController;
import server.controllers.GameControllers.MultiplayerGameRoomController;
import server.entities.MultiplayerRoom;
import server.entities.Room;
import server.entities.RoomCatalog;
import server.repositories.ActivityRepository;
import server.services.QuestionGenerator.QuestionGenerator;

import java.util.List;
import java.util.Random;

@Service
public class MultiplayerGameService {

    private final ActivityRepository activityRepository;
    private final RoomCatalog        roomCatalog;

    /**
     * Constructor for the multiplayer game service
     *
     * @param activityRepository the database where all activities are stored
     */
    public MultiplayerGameService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        roomCatalog             = RoomCatalog.getRoomCatalog();

        createNewMultiPlayerGame(null);
    }

    /**
     * Returns the id of the multiplayer random game room
     *
     * @return the id of the multiplayer random game room
     */
    public String getMultiPlayerRandomRoom() {
        try {
            System.out.println("Result: " + roomCatalog.getMultiplayerRandomRoom().getRoomId());
            return roomCatalog.getMultiplayerRandomRoom().getRoomId();
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Creates a new multiplayer game with the room creator username given as parameter
     *
     * @param username the name of player that wants to create a room
     * @return the id of the newly generated room
     */
    public String createNewMultiPlayerGame(String username) {
        try {
            String roomId               = Util.getAlphaNumericString(5);
            ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

            List<Pair<Question, String>> questionList = QuestionGenerator.generateQuestions(actionCatalog, 20, 2, 7, new Random());
            MultiplayerRoom newGame = new MultiplayerRoom(roomId, username, questionList);

            if (username == null) {
                roomCatalog.setMultiplayerRandomRoom(newGame);
            } else {
                roomCatalog.addMultiplayerRoom(newGame);

                joinMultiPlayerGame(username, newGame.getRoomId());
            }

            return roomCatalog.getMultiPlayerRoom(roomId).getRoomId();
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
            return null;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Adds a player to a specified game
     *
     * @param username the name of the player that joins the game
     * @param roomId   the id of the room
     * @return whether the user was successfully added to the game
     */
    public Boolean joinMultiPlayerGame(String username, String roomId) {
        try {
            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.WAITING ||
                roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) != null) {
                return false;
            }

            roomCatalog.getMultiPlayerRoom(roomId).addPlayer(username);

            MultiplayerGameRoomController.getPlayerNumberListeners().forEach((k, l) -> {
                if (k.getValue().equals(roomId)) {
                    l.accept(roomCatalog.getMultiPlayerRoom(roomId).getNumPlayers());
                }
            });

            GameController.getActiveRoomsListeners().forEach((k, l) -> {
                l.accept(new GameContainer(roomId, roomCatalog.getMultiPlayerRoom(roomId).getNumPlayers()));
            });

            return true;
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to join the game");
            return null;
        }
    }

    /**
     * Starts the multiplayer room with the given id
     *
     * @param username the user that starts the room
     * @param roomId   the id of the room that is started
     * @return whether the room was successfully started
     */
    public boolean startMultiPlayerGame(String username, String roomId) {
        try {
            if (roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
                return false;
            }

            roomCatalog.getMultiPlayerRoom(roomId).setRoomStatus(Room.RoomStatus.ONGOING);

            // if the random room is starting, we have to generate another one
            if (roomId.equals(roomCatalog.getMultiplayerRandomRoom().getRoomId())) {
                roomCatalog.addMultiplayerRoom(roomCatalog.getMultiplayerRandomRoom());
                createNewMultiPlayerGame(null);
            }

            MultiplayerGameRoomController.getListeners().forEach((k, l) -> {
                if (k.getValue().equals(roomId)) {
                    l.accept(true);
                }
            });


            return roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() == Room.RoomStatus.ONGOING;
        } catch (Exception e) {
            System.out.println("An exception occurred when trying to start the game");
            return false;
        }
    }

    /**
     * Returns the desired question for the specified game if the user is part of that game
     *
     * @param username         the user that requests the question
     * @param roomId           the id of the room the user is in
     * @param questionNumber   the question number needed by the user
     * @return the desired question needed by the player or null if that is not possible
     */
    public Question getMultiPlayerQuestion(String username, String roomId, int questionNumber) {
        try {
            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
                //return null;
            }

            return roomCatalog.getMultiPlayerRoom(roomId).getQuestion(questionNumber);
        } catch (Exception e) {
            System.out.println("An exception occurred when getting a question");
            return null;
        }
    }

    /**
     * Returns the answer to the desired questions for the specified game
     *
     * @param username     the user that requests the answer
     * @param roomId       the id of the room the user is in
     * @param answerNumber the answer number needed by the user
     * @return the desired answer needed by the player or null if that is not possible
     */
    public String getMultiPlayerAnswer(String username, String roomId, int answerNumber) {
        try {
            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
                return null;
            }

            return roomCatalog.getMultiPlayerRoom(roomId).getAnswer(answerNumber);
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Returns the score of the user playing in the specified game or null if it does exist
     *
     * @param username the user that requests the score
     * @param roomId   the id of the room the user is in
     * @return the score of the player or null if that score does not exist
     */
    public Integer getMultiPlayerScore(String username, String roomId) {
        try {
//            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
//                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
//                return null;
//            }

            return roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username);
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Updated the score of the player or returns null / false if some error occurs
     *
     * @param username       the user that needs the score update
     * @param roomId         the id of the room the user is in
     * @param questionNumber the question number answered by the user
     * @param userAnswer     the answer user
     */
    public Boolean updateMultiPlayerScore(String username, String roomId, int questionNumber, String userAnswer) {
        try {
            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
                //return null;
            }

            if (userAnswer.equals(getMultiPlayerAnswer(username, roomId, questionNumber))) {
                roomCatalog.getMultiPlayerRoom(roomId).updatePlayerScore(username, 500);
            }

            return true;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    public List<GameContainer> getGameIds() {
        //this.roomCatalog.cleanEmptyGames();
        return roomCatalog.getWaitingMultiplayerGames();
    }

    public MultiplayerRoom getGame(String gameId) {
        return this.roomCatalog.getMultiPlayerRoom(gameId);
    }

    /**
     * Removing specific player
     * @param roomId    the id of the room the user is in
     * @param userName  the user that needs the score update
     */
    public void removePlayer(String roomId, String userName) {
        MultiplayerGameRoomController.getPlayerNumberListeners().forEach((k, l) -> {
            if (k.getValue().equals(roomId)) {
                l.accept(roomCatalog.getMultiPlayerRoom(roomId).getNumPlayers());
            }
        });

        this.getGame(roomId).removePlayer(userName);
    }

    public void removeNotAnsweringPlayer(String username, String roomId, int questionNumber, String userAnswer) {
        //if the player does not answer for three consequtive questions, then the player is removed from the game
    }

    /**
     * Changes the room status as finished when there are no more players left in the game after the multiplayer leaderboard
     * @param roomId    the id of the room the user is in
     */
    public void changeRoomStatusAsFinished(String roomId) {
        if (roomCatalog.getMultiPlayerRoom(roomId).getNumPlayers() == 0) {
            roomCatalog.getMultiPlayerRoom(roomId).setRoomStatus(Room.RoomStatus.FINISHED);
        }
    }
}
