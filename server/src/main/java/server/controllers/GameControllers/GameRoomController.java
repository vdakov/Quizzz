package server.controllers.GameControllers;

import commons.Questions.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.services.GameServices.MultiplayerGameService;
import server.services.GameServices.SingleplayerGameService;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/{username}/{gameType}/{roomId}")
public class GameRoomController {

    private final SingleplayerGameService singlePlayerGameService;
    private final MultiplayerGameService  multiplayerGameService;

    /**
     * Constructor for the game room controller
     *
     * @param singlePlayerGameService the service for the singleplayer game features
     * @param multiplayerGameService  the service for the multiplayer  game features
     */
    public GameRoomController(SingleplayerGameService singlePlayerGameService, MultiplayerGameService multiplayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
        this.multiplayerGameService  = multiplayerGameService;
    }

    /**
     * Starts the game with the given room or gives a corresponding error message
     *
     * @param username the username of the player that is starting the game
     * @param gameType the type of the game
     * @param roomId   the id of the game that is wanted to be started
     * @return whether the room was successfully started
     */
    @GetMapping("/startGame")
    public ResponseEntity<Object> startNewGame(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                return (singlePlayerGameService.startSinglePlayerGame(username, roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                return (multiplayerGameService.startMultiPlayerGame(username, roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the requested question by the client or gives a corresponding error message
     *
     * @param username       the username of the player that requests the question
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @param questionNumber the number of the requested question
     * @return the desired question or an error message
     */
    @GetMapping("/{questionNumber}/getQuestion")
    public ResponseEntity<Question> getQuestion(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                    @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                Question question = singlePlayerGameService.getSinglePlayerQuestion(username, roomId, questionNo);

                if (question == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(question);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                Question question = multiplayerGameService.getMultiPlayerQuestion(username, roomId, questionNo);

                if (question == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(question);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the answer to the question or gives a corresponding error message
     *
     * @param username the username of the player that requests the answer
     * @param gameType the type of the game
     * @param roomId the id of the game that the player is in
     * @param questionNumber the number of the question that the answer is needed for
     * @return the String representing the answer or an error message
     */
    @GetMapping("/{questionNumber}/getAnswer")
    public ResponseEntity<String> getCorrectAnswer(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                                   @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                String answer = singlePlayerGameService.getSinglePlayerAnswer(username, roomId, questionNo);

                if (answer == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(answer);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);

                String answer = multiplayerGameService.getMultiPlayerAnswer(username, roomId, questionNo);

                if (answer == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }

                return ResponseEntity.status(HttpStatus.OK).body(answer);
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the score of the desired player or gives a corresponding error message
     * @param username       the username of the player that requests his score
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @return the score of the desired player or an error message
     */
    @GetMapping("/getScore")
    public ResponseEntity<Integer> getScore(@PathVariable("username") String username, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                Integer score = singlePlayerGameService.getSinglePlayerScore(username, roomId);
                return (score != null) ? ResponseEntity.status(HttpStatus.OK).body(score) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                Integer score = multiplayerGameService.getMultiPlayerScore(username, roomId);
                return (score != null) ? ResponseEntity.status(HttpStatus.OK).body(score) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Updates the score of the player according to the given and correct answer or gives a corresponding error message
     *
     * @param username the username of the player that gives the answer
     * @param gameType the type of the game
     * @param roomId the id of the game that the player is in
     * @param questionNumber the number of the answered question
     * @param userAnswer the user answer to the question
     * @return whether the request was successful or not
     */
    @PostMapping("/{questionNumber}/postAnswer")
    public ResponseEntity<Object> updateAnswer(@PathVariable("username") String username, @PathVariable("gameType") String gameType,
                                               @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber, @RequestBody String userAnswer) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                return singlePlayerGameService.updateSinglePlayerScore(username, roomId, questionNo, userAnswer) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                return multiplayerGameService.updateMultiPlayerScore(username, roomId, questionNo, userAnswer) ?
                        ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
