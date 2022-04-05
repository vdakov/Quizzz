package server.services.GameServices;

import commons.Actions.ActionCatalog;
import commons.Exceptions.NotEnoughActivitiesException;
import commons.Questions.Question;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import server.entities.Room;
import server.entities.RoomCatalog;
import server.entities.SingleplayerRoom;
import server.repositories.ActivityRepository;
import server.services.QuestionGenerator.QuestionGenerator;

import java.util.List;
import java.util.Random;

@Service
public class SingleplayerGameService {

    private final ActivityRepository activityRepository;
    private final RoomCatalog        roomCatalog;

    /**
     * Constructor for the singleplayer game service
     *
     * @param activityRepository the database where all activities are stored
     */
    public SingleplayerGameService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
        roomCatalog             = RoomCatalog.getRoomCatalog();
    }

    /**
     * Creates a new singleplayer game with the player username given as parameter
     *
     * @param username the name of player that wants to create a room
     * @return the id of the newly generated room
     */
    public String createNewSinglePlayerGame(String username) {
        try {
            String roomId               = Util.getAlphaNumericString(10);
            ActionCatalog actionCatalog = new ActionCatalog(activityRepository.findAll());

            List<Pair<Question, String>> questionList = QuestionGenerator.generateQuestions(actionCatalog, 20, 2, 7, new Random());
            SingleplayerRoom newGame = new SingleplayerRoom(roomId, username, questionList);

            roomCatalog.addSingleplayerRoom(newGame);
            return roomCatalog.getSinglePlayerRoom(roomId).getRoomId();
        } catch (NotEnoughActivitiesException e) {
            System.out.println("Not enough activities");
            return null;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Starts the singleplayer room with the given id
     *
     * @param username the user that starts the room
     * @param roomId   the id of the room that is started
     * @return whether the room was successfully started
     */
    public boolean startSinglePlayerGame(String username, String roomId) {
        try {
            if (roomCatalog.getSinglePlayerRoom(roomId).getRoomStatus() != Room.RoomStatus.WAITING ||
                    !roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator().equals(username)) {
                return false;
            }

            roomCatalog.getSinglePlayerRoom(roomId).setRoomStatus(Room.RoomStatus.ONGOING);

            return roomCatalog.getSinglePlayerRoom(roomId).getRoomStatus() == Room.RoomStatus.ONGOING;
        } catch (Exception e) {
            System.out.println("An exception occurred");
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
    public Question getSinglePlayerQuestion(String username, String roomId, int questionNumber) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return null;
            }

            return roomCatalog.getSinglePlayerRoom(roomId).getQuestion(questionNumber);
        } catch (Exception e) {
            System.out.println("An exception occurred");
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
    public String getSinglePlayerAnswer(String username, String roomId, int answerNumber) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return null;
            }

            return roomCatalog.getSinglePlayerRoom(roomId).getAnswer(answerNumber);
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
    public Integer getSinglePlayerScore(String username, String roomId) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return null;
            }

            return roomCatalog.getSinglePlayerRoom(roomId).getPlayerScore();
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Calculated the points earned in this round
     * @return the points earned in this round
     */
    public Integer calculatePointsAdded(String username, String roomId) {
        if (getDoublePointJokerUsed(username, roomId)) {
            return 1000;
        } else {
            return 500;
        }
    }

    /**
     * Checks whether the hint joker is used or not
     * @param username  the user who used the hint joker
     * @param roomId    the id of the room the user is in
     * @return returns true when the hint joker is used
     */
    public Boolean getHintJokerUsed(String username, String roomId) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return null;
            }

            return roomCatalog.getSinglePlayerRoom(roomId).getHintJokerUsed();
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }

    /**
     * Checks whether the double point joker is used or not
     * @param username the user who used the double point joker
     * @param roomId the id of the room the user is in
     * @return returns true when the double point joker is used
     */
    public Boolean getDoublePointJokerUsed(String username, String roomId) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return null;
            }

            return roomCatalog.getSinglePlayerRoom(roomId).getDoublePointJokerUsed();
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
    public Boolean updateSinglePlayerScore(String username, String roomId, int questionNumber, String userAnswer) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return false;
            }

            if (userAnswer.equals(getSinglePlayerAnswer(username, roomId, questionNumber))) {
                roomCatalog.getSinglePlayerRoom(roomId).updatePlayerScore(this.calculatePointsAdded(username, roomId));
            }

            return true;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }
    public Boolean useHintJoker(String username, String roomId) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return false;
            }

            roomCatalog.getSinglePlayerRoom(roomId).useHintJoker();
            System.out.println("Hint Joker Used");

            return true;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }
    public Boolean useDoublePointJoker(String username, String roomId) {
        try {
            if (!username.equals(roomCatalog.getSinglePlayerRoom(roomId).getRoomCreator())) {
                return false;
            }

            roomCatalog.getSinglePlayerRoom(roomId).useDoublePointJoker();

            return true;
        } catch (Exception e) {
            System.out.println("An exception occurred");
            return null;
        }
    }
}

