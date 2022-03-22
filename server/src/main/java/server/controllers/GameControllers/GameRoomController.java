package server.controllers.GameControllers;

import commons.Questions.Question;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.services.GameServices.MultiplayerGameService;
import server.services.GameServices.SinglePlayerGameService;

@RestController
@RequestMapping("api/{userName}/{gameType}/{roomId}")
public class GameRoomController {

    private final SinglePlayerGameService singlePlayerGameService;
    private final MultiplayerGameService  multiplayerGameService;

    /**
     * Constructor for the game controller
     *
     * @param singlePlayerGameService the service for the singleplayer game features
     * @param multiplayerGameService  the service for the multiplayer  game features
     */
    public GameRoomController(SinglePlayerGameService singlePlayerGameService, MultiplayerGameService multiplayerGameService) {
        this.singlePlayerGameService = singlePlayerGameService;
        this.multiplayerGameService  = multiplayerGameService;
    }

    /**
     * Starts the game with the given room or gives a corresponding error message
     *
     * @param userName the userName of the player that is starting the game
     * @param gameType the type of the game
     * @param roomId   the id of the game that is wanted to be started
     * @return whether the room was successfully started
     */
    @GetMapping("/startGame")
    public ResponseEntity<Object> startNewGame(@PathVariable("userName") String userName, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                return (singlePlayerGameService.startSinglePlayerGame(userName, roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                return (multiplayerGameService.startMultiPlayerGame(roomId)) ? ResponseEntity.status(HttpStatus.OK).build() : ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Returns the requested question by the client or gives a corresponding error message
     *
     * @param userName       the userName of the player that requests the question
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @param questionNumber the number of the requested question
     * @return the desired question or an error message
     */
    @GetMapping("/{questionNumber}/getQuestion")
    public ResponseEntity<Question> getNextQuestion(@PathVariable("userName") String userName, @PathVariable("gameType") String gameType,
                                                    @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                Question question = singlePlayerGameService.getSinglePlayerQuestion(userName, questionNo);

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
                Question question = multiplayerGameService.getMultiPlayerQuestion(roomId, questionNo);

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
     * Updates the score of the player according to the given and correct answer or gives a corresponding error message
     *
     * @param userName the userName of the player that gives the answer
     * @param gameType the type of the game
     * @param roomId the id of the game that the player is in
     * @param questionNumber the number of the answered question
     * @param answer the user answer to the question
     * @return whether the request was successful or not
     */
    @PostMapping("/{questionNumber}/postAnswer")
    public ResponseEntity<Object> updateAnswer(@PathVariable("userName") String userName, @PathVariable("gameType") String gameType,
                                               @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber, @RequestBody String answer) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                singlePlayerGameService.updateSinglePlayerScore(roomId, questionNo, answer);

                return ResponseEntity.status(HttpStatus.OK).build();
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
                multiplayerGameService.updateMultiPlayerScore(userName, roomId, questionNo, answer);

                return ResponseEntity.status(HttpStatus.OK).build();
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
     * Returns the answer to the question or gives a corresponding error message
     *
     * @param userName the userName of the player that requests the answer
     * @param gameType the type of the game
     * @param roomId the id of the game that the player is in
     * @param questionNumber the number of the question that the answer is needed for
     * @return the String representing the answer or an error message
     */
    @GetMapping("/{questionNumber}/getAnswer")
    public ResponseEntity<String> getCorrectAnswer(@PathVariable("userName") String userName, @PathVariable("gameType") String gameType,
                                                   @PathVariable("roomId") String roomId, @PathVariable("questionNumber") String questionNumber) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                int questionNo = Integer.parseInt(questionNumber);
                String answer = singlePlayerGameService.getSinglePlayerAnswer(userName, roomId, questionNo);

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

                String answer = multiplayerGameService.getMultiPlayerAnswer(userName, roomId, questionNo);

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
     * @param userName       the userName of the player that requests his score
     * @param gameType       the type of the game
     * @param roomId         the id of the game that the player is in
     * @return the score of the desired player or an error message
     */
    @GetMapping("/getScore")
    public ResponseEntity<Integer> getScore(@PathVariable("userName") String userName, @PathVariable("gameType") String gameType, @PathVariable("roomId") String roomId) {
        if (gameType.equals("SINGLEPLAYER")) {
            try {
                Integer score = singlePlayerGameService.getSinglePlayerScore(roomId);
                return (score != null) ? ResponseEntity.status(HttpStatus.OK).body(score) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        if (gameType.equals("MULTIPLAYER")) {
            try {
                Integer score = multiplayerGameService.getMultiPlayerScore(userName, roomId);
                return (score != null) ? ResponseEntity.status(HttpStatus.OK).body(score) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
