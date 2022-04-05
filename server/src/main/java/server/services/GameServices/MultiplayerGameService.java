package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.GameContainer;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
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
            String roomId               = Util.getAlphaNumericString(10);
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

            MultiplayerGameRoomController.getListeners().forEach((k, l) -> l.accept(roomId));


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
     * Calculated the points added on this round
     * @return the points earned in this round
     */
    public int calculatePointsAdded(String username, String roomId, long timeLeft) {
        return roomCatalog.getMultiPlayerRoom(roomId).calculateAddedPoints(username, timeLeft);
    }


    public int getAddedPoints(String username, String roomId) {
        return roomCatalog.getMultiPlayerRoom(roomId).getAddedPoints(username);
    }

    /**
     * Returns whether the hint joker was used by this player in this game or null if it doesnt exist
     *
     * @param username the user that requests the score
     * @param roomId   the id of the room the user is in
     * @return returns true when the hint joker is used
     */
    public Boolean getHintJokerUsed(String username, String roomId) {
        try {
//            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
//                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
//                return null;
//            }
            return roomCatalog.getMultiPlayerRoom(roomId).getHintJokerUsed(username);
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Returns whether the double point joker was used by this player in this game or null if it doesnt exist
     *
     * @param username the user that requests the score
     * @param roomId   the id of the room the user is in
     * @return returns true when the double point joker is used
     */
    public Boolean getDoublePointJokerUsed(String username, String roomId) {
        try {
//            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
//                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
//                return null;
//            }
            return roomCatalog.getMultiPlayerRoom(roomId).getDoublePointJokerUsed(username);
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Returns whether the time joker was used by this player in this game or null if it doesnt exist
     *
     * @param username the user that requests the score
     * @param roomId   the id of the room the user is in
     */
    public Boolean getTimeJokerUsed(String username, String roomId) {
        try {
//            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
//                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
//                return null;
//            }
            return roomCatalog.getMultiPlayerRoom(roomId).getTimeJokerUsed(username);
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * "Uses" the hint joker, sets hintJokerUsed to true
     *
     * @param username       the user that needs the score update
     * @param roomId         the id of the room the user is in
     */
    public Boolean useHintJoker(String username, String roomId) {
        try {
//            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
//                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
//                //return null;
//            }

        roomCatalog.getMultiPlayerRoom(roomId).useHintJoker(username);
        return true;

        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * "Uses" the double point joker, sets doublePointJokerUsed to true
     *
     * @param username       the user that needs the score update
     * @param roomId         the id of the room the user is in
     */
    public Boolean useDoublePointJoker(String username, String roomId) {
        try {
//            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
//                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
//                //return null;
//            }

            roomCatalog.getMultiPlayerRoom(roomId).useDoublePointJoker(username);
            return true;

        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * "Uses" the time joker, sets timeJokerUSed to true
     *
     * @param username       the user that needs the score update
     * @param roomId         the id of the room the user is in
     */
    public Boolean useTimeJoke(String username, String roomId) {
        try {
//            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
//                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
//                //return null;
//            }

            roomCatalog.getMultiPlayerRoom(roomId).useTimeJoker(username);
            return true;

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
    public Boolean updateMultiPlayerScore(String username, String roomId, int questionNumber, String userAnswer, Long timeLeft) {
        try {
            if (roomCatalog.getMultiPlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.ONGOING ||
                    roomCatalog.getMultiPlayerRoom(roomId).getPlayerScore(username) == null) {
                //return null;
            }

            if (userAnswer.equals(getMultiPlayerAnswer(username, roomId, questionNumber))) {
                roomCatalog.getMultiPlayerRoom(roomId).updatePlayerScore(username, this.calculatePointsAdded(username, roomId, timeLeft));
            }

            return true;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Get all multiplayer games that is currently in waiting status
     * @return list of all multiplayer games that is in waiting status
     */
    public List<GameContainer> getGameIds() {
        //this.roomCatalog.cleanEmptyGames();
        return roomCatalog.getWaitingMultiplayerGames();
    }

    /**
     * Gets the multiplayer game room
     * @param roomId the id of the room the user is in
     * @return the multiplayer room with the given id or null if a room with that id does not exist
     */
    public MultiplayerRoom getGame(String roomId) {
        return this.roomCatalog.getMultiPlayerRoom(roomId);
    }

    /**
     * Removes the player with given name at given room
     * @param roomId the id of the room the user is in
     * @param userName the name of the removing player
     */
    public void removePlayer(String roomId, String userName) {
        this.getGame(roomId).removePlayer(userName);
    }

    /**
     * Resets the points added as 10, to prevent the points getting doubled everytime after the double point joker is used
     * @param username  the user that needs the score update
     * @param roomId    the id of the room the user is in
     * @return true is the reset is successfully done
     */
    public Boolean resetAddedPointAfterDoublePointJoker(String username, String roomId) {
        try {
            if (!username.equals(roomCatalog.getMultiPlayerRoom(roomId).getRoomCreator())) {
                return false;
            }
            roomCatalog.getMultiPlayerRoom(roomId).resetAddedPointAfterDoublePointJoker(username);
            return true;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }
}
